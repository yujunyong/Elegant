package org.elegant.service;

import org.elegant.model.jooq.tables.pojos.Book;
import org.elegant.repository.BookCoverRepository;
import org.elegant.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private BookCoverRepository bookCoverRepository;

    @MockBean
    private DirectoryService directoryService;

    @Autowired
    private BookService bookService;

    @Before
    public void setup() {
    }

    @Test
    public void testGetOsPath() throws URISyntaxException {
        Path dirPath = Paths.get(ClassLoader.getSystemResource("book/pdf").toURI());
        when(directoryService.getOsPath(1))
                .thenReturn(Mono.just(dirPath));

        Book book = new Book()
                .setBookId(1)
                .setDirId(1)
                .setTitle("The Art of Code")
                .setFormat("pdf");
        Path path = bookService.getOsPath(book).block();

        assertThat(path.getParent()).isEqualTo(dirPath);
        assertThat(path.endsWith("The Art of Code.pdf")).isTrue();
    }
}