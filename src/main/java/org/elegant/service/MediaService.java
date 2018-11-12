package org.elegant.service;

import org.elegant.model.jooq.tables.pojos.BookCover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MediaService {
    private BookService bookService;

    @Autowired
    public MediaService(BookService bookService) {
        this.bookService = bookService;
    }

    public Mono<BookCover> getBookCover(Integer bookId) {
        return bookService.getBookCover(bookId);
    }
}
