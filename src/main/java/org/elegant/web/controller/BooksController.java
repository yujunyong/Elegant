package org.elegant.web.controller;

import org.elegant.model.jooq.tables.pojos.Book;
import org.elegant.service.BookService;
import org.elegant.web.controller.dto.request.AddBookRequest;
import org.elegant.web.controller.dto.request.BookQuerier;
import org.elegant.web.controller.dto.request.UpdateBookRequest;
import org.elegant.web.controller.dto.response.BookResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/books")
public class BooksController {
    private BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public Flux<BookResponse> getBooks(BookQuerier querier) {
        return bookService.queryBook(querier).map(this::convert2BookResponse);
    }

    @PostMapping()
    public Mono<Void> addBook(@RequestBody AddBookRequest request) {
        // todo 添加封面数据
        Book book = new Book();
        BeanUtils.copyProperties(request, book);

        return bookService.addBook(book);
    }

    private BookResponse convert2BookResponse(Book book) {
        BookResponse r = new BookResponse();
        BeanUtils.copyProperties(book, r);
        bookService.getPath(book)
                .subscribe(r::setPath);
        r.setCoverUrl(String.format("/medias/book-cover/%s", book.getBookId()));
        return r;
    }

    @RestController
    @RequestMapping("/books/{bookId}")
    public class BookController {
        @GetMapping()
        public Mono<BookResponse> getBook(@PathVariable("bookId") Integer bookId) {
            return bookService.getBook(bookId)
                    .map(BooksController.this::convert2BookResponse);
        }

        @PatchMapping()
        public Mono<Void> updateBook(@PathVariable("bookId") Integer bookId, @RequestBody UpdateBookRequest request) {
            // todo 更新封面
            Book book = new Book();
            BeanUtils.copyProperties(request, book);
            book.setBookId(bookId);
            return bookService.updateBook(book);
        }

        @DeleteMapping
        public Mono<Void> deleteBook(@PathVariable("bookId") Integer bookId) {
            return bookService.deleteBook(bookId);
        }
    }
}
