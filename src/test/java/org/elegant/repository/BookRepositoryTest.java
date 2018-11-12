package org.elegant.repository;

import org.elegant.annotation.test.RepositoryTest;
import org.elegant.enumeration.BookFormatEnum;
import org.elegant.model.jooq.tables.pojos.Book;
import org.jooq.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Sql(scripts = {"classpath:sql/books-init.sql"})
@RepositoryTest
public class BookRepositoryTest {
    @Autowired
    private Configuration configuration;

    private BookRepository bookRepository;

    @Before
    public void setup() {
        this.bookRepository = new BookRepository(configuration);
    }

    @Test
    public void testFetchOneBookByDirIdAndTitle() {
        Book book = new Book()
                .setTitle("book")
                .setFormat(BookFormatEnum.PDF.getFormat())
                .setDirId(1);

        bookRepository.insert(book);

        Book expect = bookRepository.fetchOneByDirIdAndTitle(1, "book");

        assertThat(expect.getTitle()).isEqualTo("book");
        assertThat(expect.getFormat()).isEqualTo("pdf");
        assertThat(expect.getDirId()).isEqualTo(1);
    }
}