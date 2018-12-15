package org.elegant.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.elegant.event.property.AddPropertyEvent;
import org.elegant.event.property.UpdatePropertyEvent;
import org.elegant.model.jooq.tables.pojos.Book;
import org.elegant.model.jooq.tables.pojos.BookCover;
import org.elegant.model.jooq.tables.pojos.Directory;
import org.elegant.model.jooq.tables.pojos.Property;
import org.elegant.repository.BookCoverRepository;
import org.elegant.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.elegant.enumeration.BookFormatEnum.PDF;

@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    public static final String BOOK_COVER_THUMBNAILATOR_FORMAT = "jpg";

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
        return Mono.just(bookId)
                .flatMap(id -> Mono.justOrEmpty(bookRepository.fetchOneByBookId(id)));
    }

    public Mono<Book> getBook(Integer dirId, String title) {
        checkNotNull(dirId);
        checkNotNull(title);

        return Mono.justOrEmpty(bookRepository.fetchOneByDirIdAndTitle(dirId, title));
    }

    public Mono<BookCover> getBookCover(Integer bookId) {
        return Mono.just(bookId)
                .flatMap(id -> Mono.justOrEmpty(bookCoverRepository.fetchOneByBookId(id)));
    }

    public Flux<Book> getBooksByDirId(Integer dirId) {
        return Mono.just(dirId)
                .flatMapIterable(id -> bookRepository.fetchByDirId(id))
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
        return processBookCover(book, bookCoverRepository::update);
    }

    public Mono<Void> addBookCover(Collection<Book> books) {
        return Flux.fromIterable(books).flatMap(this::addBookCover).then();
    }

    public Mono<Void> addBookCover(Book book) {
        return processBookCover(book, bookCoverRepository::insert);
    }

    private Mono<Void> processBookCover(Book book, Consumer<BookCover> consumer) {
        return getOsPath(book).flatMap(path -> {
            PDDocument document = null;
            try {
                document = PDDocument.load(path.toFile());
                var renderer = new PDFRenderer(document);
                var image = renderer.renderImage(0);
                var out = new ByteArrayOutputStream();
                ImageIO.write(image, BOOK_COVER_THUMBNAILATOR_FORMAT, out);

                BookCover bookCover = new BookCover();
                bookCover.setBookId(book.getBookId());
                bookCover.setCover(out.toByteArray());
                bookCover.setImageFileExtension(BOOK_COVER_THUMBNAILATOR_FORMAT);
                return Mono.just(bookCover);

            } catch (Throwable t) {
                logger.error(String.format("extract book(%s) cover failed", book.getBookId()), t);
                return Mono.empty();
            } finally {
                if (Objects.nonNull(document)) {
                    try {
                        document.close();
                    } catch (IOException e) {
                        logger.error("close document failed", e);
                    }
                }
            }
        }).doOnNext(consumer).then();
    }

    public Flux<Integer> syncOsFile2Db() {
        return directoryService.getRootDirectory()
                .map(Directory::getDirId)
                .doOnSubscribe(s -> logger.info("start sync all books"))
                .flatMapMany(this::syncOsFile2Db)
                .doOnComplete(() -> logger.info("sync all books finished"));
    }

    public Flux<Integer> syncOsFile2Db(Integer dirId) {
        // 同步book, 新增, 更新book
        var syncBook = new CompletableFuture<Integer>();
        syncBooks(dirId)
                .subscribeOn(Schedulers.parallel())
                .subscribe(book -> this.addBookCover(book).subscribe(), syncBook::completeExceptionally, () -> syncBook.complete(dirId));

        // 同步子文件夹下数据
        var syncSubDirs = new CompletableFuture<List<Integer>>();
        directoryService.getOsPath(dirId)
                .flatMapMany(path -> fileService.getSubDirs(path))
                .flatMap(dir -> directoryService.addDirectoryIfAbsent(dirId, dir.getFileName().toString()))
                .map(Directory::getDirId)
                .flatMap(this::syncOsFile2Db)
                .collectList()
                .doOnSuccessOrError((dirIds, t) -> {
                    if (Objects.nonNull(t)) {
                        syncSubDirs.completeExceptionally(t);
                    } else {
                        syncSubDirs.complete(dirIds);
                    }
                })
                .subscribe();

        return Mono.fromFuture(syncBook.thenCombine(syncSubDirs,
                (parentDirId, dirIds) -> {dirIds.add(parentDirId); return dirIds;}))
                .flatMapMany(Flux::fromIterable);
    }

    public Flux<Book> syncBooks(Integer dirId) {
        return getBooksByDirId(dirId)
                .collectMap(Book::getTitle)
                .flatMapMany(books ->
                    directoryService.getOsPath(dirId)
                            .flatMapMany(path -> fileService.getFiles(path, PDF::isMatch))
                            .doOnSubscribe(s -> logger.info("start sync dir({}) books", dirId))
                            .filter(path -> !books.containsKey(getTitle(path.getFileName().toString())))
                            .map(path -> {
                                Book book = new Book();
                                String fileName = path.getFileName().toString();
                                book.setTitle(getTitle(fileName));
                                book.setDirId(dirId);
                                book.setFormat(PDF.getFormat());
                                return book;
                            })
                            .buffer(200)
                            .doOnNext(bs -> this.addBook(bs).subscribe())
                            .doOnComplete(() -> logger.info("sync dir({}) finished", dirId))
                            .flatMap(Flux::fromIterable));
    }

    private String getTitle(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    public Mono<Void> addBook(Collection<Book> books) {
        return Flux.fromIterable(books)
                .doOnNext(bookRepository::insert)
                .then();
    }

    public Mono<Void> addBook(Book book) {
        return Mono.just(book)
                .doOnNext(bookRepository::insert)
                .then();
    }

    public Mono<Void> updateBook(Book book) {
        return Mono.just(book)
                .doOnNext(b -> b.setUpdateTime(LocalDateTime.now()))
                .doOnNext(bookRepository::update)
                .then();
    }

    public Mono<Void> deleteBook(Integer bookId) {
        return Mono.just(bookId)
                .doOnNext(bookRepository::deleteById)
                .then();
    }

    @EventListener
    public void processAddRootDirectoryPropertyEvent(AddPropertyEvent event) {
        if (isRootDirectoryProperty(event.getProperty())) {
            syncOsFile2Db().subscribe();
        }
    }

    @EventListener
    public void processUpdateRootDirectoryPropertyEvent(UpdatePropertyEvent event) {
        if (isRootDirectoryProperty(event.getProperty())) {
            syncOsFile2Db().subscribe();
        }
    }

    private boolean isRootDirectoryProperty(Property property) {
        return Objects.equals(property.getPropName(), PropertyService.ROOT_BOOK_DIR_KEY);
    }
}
