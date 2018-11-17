package org.elegant.repository;

import org.elegant.model.jooq.Tables;
import org.elegant.model.jooq.tables.daos.BookDao;
import org.elegant.model.jooq.tables.pojos.Book;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
}
