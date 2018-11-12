package org.elegant.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfDocumentContentParser;
import net.bytebuddy.asm.Advice;
import org.elegant.model.jooq.tables.pojos.Book;
import org.elegant.model.jooq.tables.pojos.BookCover;
import org.elegant.processor.pdf.listener.ImageListener;
import org.elegant.repository.BookCoverRepository;
import org.elegant.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private BookRepository bookRepository;
    private BookCoverRepository bookCoverRepository;
    private DirectoryService directoryService;

    @Autowired
    public BookService(BookRepository bookRepository,
                       BookCoverRepository bookCoverRepository,
                       DirectoryService directoryService) {
        this.bookRepository = bookRepository;
        this.bookCoverRepository = bookCoverRepository;
        this.directoryService = directoryService;
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

    public Flux<Book> getBooks(Integer dirId) {
        return Flux.fromIterable(bookRepository.fetchByDirId(dirId));
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

    public void updateCovers(Integer dirId) {
        getBooks(dirId).subscribe(this::updateCover);
    }

    public void updateCover(Book book) {
        getOsPath(book).subscribe(path -> {
            try {
                PdfDocument document = new PdfDocument(new PdfReader(path.toFile()));

                new PdfDocumentContentParser(document)
                        .processContent(1, new ImageListener(image -> {
                            BookCover bookCover = new BookCover();
                            bookCover.setBookId(book.getBookId());
                            bookCover.setCover(image.getImageBytes());
                            bookCover.setImageFileExtension(image.identifyImageFileExtension());

                            bookCoverRepository.insert(bookCover);
                        }));
            } catch (Throwable t) {
                logger.error(String.format("add book(%s) cover failed", book.getBookId()), t);
            }
        });
    }

    public void addBooks(Collection<Book> books) {
        checkNotNull(books);
        checkArgument(!books.isEmpty());

        LocalDateTime now = LocalDateTime.now();
        books.forEach(book -> book.setUpdateTime(now));

        bookRepository.insert(books);
    }

    public void addBook(Book book) {
        checkNotNull(book);

        book.setUpdateTime(LocalDateTime.now());
        bookRepository.insert(book);
    }

    public void updateBook(Book book) {
        checkNotNull(book);
        checkNotNull(book.getBookId());

        book.setUpdateTime(LocalDateTime.now());
        bookRepository.update(book);
    }

    public void deleteBook(Integer bookId) {
        bookRepository.deleteById(bookId);
    }
}
