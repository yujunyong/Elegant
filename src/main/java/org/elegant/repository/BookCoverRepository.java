package org.elegant.repository;

import org.elegant.model.jooq.tables.daos.BookCoverDao;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookCoverRepository extends BookCoverDao {
    @Autowired
    public BookCoverRepository(Configuration configuration) {
        super(configuration);
    }
}
