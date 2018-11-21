package org.elegant.service;

import org.elegant.model.jooq.tables.pojos.Book;
import org.elegant.model.jooq.tables.pojos.Directory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Hooks;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elegant.util.FileUtil.getPath;

@RunWith(SpringRunner.class)
@Sql("classpath:sql/root-directories-init.sql")
@SpringBootTest
public class BookSyncTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BookService bookService;

    @Autowired
    private DirectoryService directoryService;

    @Before
    public void setup() {
        String root = getPath("book/pdf").toString();
        jdbcTemplate.update("insert into property(prop_name, prop_value) values ('app.directory.root', ?)", root);
        Hooks.onOperatorDebug();
    }

    @Test
    public void testSyncOsFile2Db() {
        bookService.syncOsFile2Db().block();

        // 检查根目录
        List<Book> books = bookService.getBooksByDirId(1).collectList().block();
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(2);
        assertThat(books.get(0).getTitle()).isEqualTo("The Art of Code");
        assertThat(books.get(1).getTitle()).isEqualTo("Vuex Concepts The Flux Application Architecture for Vue.js");

        List<Directory> subDirs = directoryService.getSubDirecties(1).collectList().block();
        assertThat(subDirs).isNotNull();
        assertThat(subDirs.size()).isEqualTo(2);
        assertThat(subDirs.get(0).getName()).isEqualTo("dir2");
        assertThat(subDirs.get(1).getName()).isEqualTo("dir3");

        assertThat(jdbcTemplate.queryForList("select * from book").size()).isEqualTo(6);
        assertThat(jdbcTemplate.queryForList("select * from book_cover").size()).isEqualTo(5);
        assertThat(jdbcTemplate.queryForList("select * from directory").size()).isEqualTo(7);
        assertThat(jdbcTemplate.queryForList("select * from directory_path").size()).isEqualTo(19);
    }
}