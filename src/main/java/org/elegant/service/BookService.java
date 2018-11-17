package org.elegant.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfDocumentContentParser;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import org.elegant.event.property.AddPropertyEvent;
import org.elegant.event.property.UpdatePropertyEvent;
import org.elegant.model.jooq.tables.pojos.Book;
import org.elegant.model.jooq.tables.pojos.BookCover;
import org.elegant.model.jooq.tables.pojos.Directory;
import org.elegant.model.jooq.tables.pojos.Property;
import org.elegant.processor.pdf.listener.ImageListener;
import org.elegant.repository.BookCoverRepository;
import org.elegant.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.elegant.enumeration.BookFormatEnum.PDF;

@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private BookRepository bookRepository;
    private BookCoverRepository bookCoverRepository;
    private DirectoryService directoryService;
    private FileService fileService;

    @Autowired
    public BookService(BookRepository bookRepository,
                       BookCoverRepository bookCoverRepository,
                       DirectoryService directoryService,
                       FileService fileService) {
        this.bookRepository = bookRepository;
        this.bookCoverRepository = bookCoverRepository;
        this.directoryService = directoryService;
        this.fileService = fileService;
    }

    public Mono<Book> getBook(Integer bookId) {
        return Mono.justOrEmpty(bookRepository.fetchOneByBookId(bookId));
    }

    public Mono<Book> getBook(Integer dirId, String title) {
        return Mono.justOrEmpty(bookRepository.fetchOneByDirIdAndTitle(dirId, title));
    }

    public Mono<BookCover> getBookCover(Integer bookId) {
        return Mono.justOrEmpty(bookCoverRepository.fetchOneByBookId(bookId));
    }

    public Flux<Book> getBooksByDirId(Integer dirId) {
        return Flux.fromIterable(bookRepository.fetchByDirId(dirId))
                .sort(Comparator.comparing(Book::getTitle));
    }

    public Mono<Path> getOsPath(Integer bookId) {
        return getBook(bookId).flatMap(this::getOsPath);
    }

    public Mono<Path> getOsPath(Book book) {
        return directoryService.getOsPath(book.getDirId())
                .flatMap(dirPath ->
                        getFileName(book).map(fileName -> Paths.get(dirPath.toString(), fileName)));
    }

    public Mono<String> getPath(Integer bookId) {
        return getBook(bookId).flatMap(this::getPath);
    }

    public Mono<String> getPath(Book book) {
        return directoryService.getPath(book.getDirId())
                .flatMap(dirPath -> getFileName(book)
                        .map(fileName -> dirPath + File.separator +  fileName));
    }

    private Mono<String> getFileName(Integer bookId) {
        return getBook(bookId).flatMap(this::getFileName);
    }

    private Mono<String> getFileName(Book book) {
        return Mono.just(book.getTitle() + "." + book.getFormat());
    }

    public Mono<Void> updateCovers(Integer dirId) {
        return getBooksByDirId(dirId).flatMap(this::updateCover).then();
    }

    public Mono<Void> updateCover(Book book) {
        return getOsPath(book).flatMap(path -> {
            try {
                PdfDocument document = new PdfDocument(new PdfReader(path.toFile()));

                new PdfDocumentContentParser(document)
                        .processContent(1, new ImageListener(image -> {
                            BookCover bookCover = convert2BookCover(image, book.getBookId());
                            bookCoverRepository.update(bookCover);
                        }));
            } catch (Throwable t) {
                logger.error(String.format("update book(%s) cover failed", book.getBookId()), t);
            }
            return Mono.empty();
        });
    }

    public Mono<Void> addBookCover(Collection<Book> books) {
        return Flux.fromIterable(books).doOnNext(this::addBookCover).then();
    }

    public Mono<Void> addBookCover(Book book) {
        return getOsPath(book).flatMap(path -> {
            try {
                PdfDocument document = new PdfDocument(new PdfReader(path.toFile()));

                new PdfDocumentContentParser(document)
                        .processContent(1, new ImageListener(image -> {
                            BookCover bookCover = convert2BookCover(image, book.getBookId());
                            bookCoverRepository.insert(bookCover);
                        }));
            } catch (Throwable t) {
                logger.error(String.format("add book(%s) cover failed", book.getBookId()), t);
            }

            return Mono.empty();
        });
    }

    private BookCover convert2BookCover(PdfImageXObject image, Integer bookId) {
        BookCover bookCover = new BookCover();
        bookCover.setBookId(bookId);
        bookCover.setCover(image.getImageBytes());
        bookCover.setImageFileExtension(image.identifyImageFileExtension());

        return bookCover;
    }

    public Mono<Void> syncOsFile2Db() {
        return directoryService.getRootDirectory()
                .map(Directory::getDirId)
                .flatMap(this::syncOsFile2Db);
    }

    public Mono<Void> syncOsFile2Db(Integer dirId) {
        return directoryService.getOsPath(dirId).flatMap(path -> {
            // 同步book, 新增, 更新book
            Mono<Void> bookComplete = getBooksByDirId(dirId)
                    .collectMap(Book::getTitle)
                    .flatMap(books -> fileService.getFiles(path, f -> PDF.getFileExtension().stream().anyMatch(extention -> f.toString().endsWith(extention)))
                        .filter(file -> !books.containsKey(getTitle(file.getFileName().toString())))
                        .map(file -> {
                            Book book = new Book();
                            String fileName = file.getFileName().toString();
                            book.setTitle(getTitle(fileName));
                            book.setDirId(dirId);
                            book.setFormat(PDF.getFormat());
                            return book;
                        })
                        .buffer(200)
                        .doOnNext(bs -> {
                            addBook(bs);
                            addBookCover(bs);
                        })
                        .then());

            // 同步子文件夹下数据
            Mono<Void> subDirsComplete = fileService.getSubDirs(path)
                    .flatMap(dir -> directoryService.addDirectoryIfAbsent(dirId, dir.getFileName().toString()))
                    .map(Directory::getDirId)
                    .flatMap(this::syncOsFile2Db)
                    .then();

            return bookComplete.concatWith(subDirsComplete).then();
        });
    }

    private String getTitle(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    public Mono<Void> addBook(Collection<Book> books) {
        checkNotNull(books);
        checkArgument(!books.isEmpty());

        bookRepository.insert(books);
        return Mono.empty();
    }

    public Mono<Void> addBook(Book book) {
        checkNotNull(book);

        bookRepository.insert(book);
        return Mono.empty();
    }

    public Mono<Void> updateBook(Book book) {
        checkNotNull(book);
        checkNotNull(book.getBookId());

        book.setUpdateTime(LocalDateTime.now());
        bookRepository.update(book);
        return Mono.empty();
    }

    public Mono<Void> deleteBook(Integer bookId) {
        bookRepository.deleteById(bookId);
        return Mono.empty();
    }

    @EventListener
    @Transactional
    public void processAddRootDirectoryPropertyEvent(AddPropertyEvent event) {
        if (isRootDirectoryProperty(event.getProperty())) {
            syncOsFile2Db();
        }
    }

    @EventListener
    @Transactional
    public void processUpdateRootDirectoryPropertyEvent(UpdatePropertyEvent event) {
        if (isRootDirectoryProperty(event.getProperty())) {
            syncOsFile2Db();
        }
    }

    private boolean isRootDirectoryProperty(Property property) {
        return Objects.equals(property.getPropName(), PropertyService.ROOT_BOOK_DIR_KEY);
    }
}
