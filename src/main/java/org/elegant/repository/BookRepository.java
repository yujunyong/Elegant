package org.elegant.repository;

import org.elegant.model.jooq.Tables;
import org.elegant.model.jooq.tables.daos.BookDao;
import org.elegant.model.jooq.tables.pojos.Book;
import org.elegant.web.controller.dto.request.BookQuerier;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static org.jooq.impl.DSL.using;

@Component
public class BookRepository extends BookDao {
    private org.elegant.model.jooq.tables.Book book = Tables.BOOK;

    @Autowired
    public BookRepository(Configuration configuration) {
        super(configuration);
    }

    public Book fetchOneByDirIdAndTitle(Integer dirId, String title) {
        return using(configuration())
                .selectFrom(book)
                .where(book.DIR_ID.eq(dirId))
                .and(book.TITLE.eq(title))
                .fetchOneInto(Book.class);
    }

    public List<Book> queryBook(BookQuerier querier) {
        var seek = querier.getSeek();
        var q = using(configuration())
                .selectFrom(book)
                .orderBy(book.UPDATE_TIME.desc(), book.BOOK_ID.desc());

        if (Objects.nonNull(seek)) {
            return q.seek(seek.getUpdateTime(), seek.getBookId())
                    .limit(querier.getSize())
                    .fetchInto(Book.class);
        } else {
            return q.limit(querier.getSize())
                    .fetchInto(Book.class);
        }
    }
}
