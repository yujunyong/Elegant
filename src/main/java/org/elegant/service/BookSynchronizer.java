package org.elegant.service;

import org.elegant.event.property.AddPropertyEvent;
import org.elegant.event.property.UpdatePropertyEvent;
import org.elegant.model.jooq.tables.pojos.Book;
import org.elegant.model.jooq.tables.pojos.Directory;
import org.elegant.model.jooq.tables.pojos.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.elegant.enumeration.BookFormatEnum.PDF;

@Service
public class BookSynchronizer implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(BookSynchronizer.class);

    private PropertyService propertyService;

    private DirectoryService directoryService;

    private BookService bookService;

    @Autowired
    public BookSynchronizer(PropertyService propertyService, DirectoryService directoryService, BookService bookService) {
        this.propertyService = propertyService;
        this.directoryService = directoryService;
        this.bookService = bookService;
    }

    @EventListener
    @Transactional
    public void processAddRootDirectoryPropertyEvent(AddPropertyEvent event) {
        if (isRootDirectoryProperty(event.getProperty())) {
            osSync2Db();
        }
    }

    @EventListener
    @Transactional
    public void processUpdateRootDirectoryPropertyEvent(UpdatePropertyEvent event) {
        if (isRootDirectoryProperty(event.getProperty())) {
            osSync2Db();
        }
    }

    private boolean isRootDirectoryProperty(Property property) {
        return Objects.equals(property.getPropName(), PropertyService.ROOT_BOOK_DIR_KEY);
    }

    // todo 优化同步数据慢的问题
    public void osSync2Db() {
        logger.info("start sync book in os to database");
        logger.info("=================================");
        Mono.zip(propertyService.getString(PropertyService.ROOT_BOOK_DIR_KEY), directoryService.getRootDirectory())
                .subscribe(tuple2 -> {
                    Path rootPath = Paths.get(tuple2.getT1());
                    Directory rootDir = tuple2.getT2();
                    osSync2Db(rootPath, rootDir.getDirId(), rootDir.getName());
                });
    }

    public void osSync2Db(Path source, Integer parentDirId, String dirName) {
        logger.info("start sync {} directory", source.toString());
        directoryService.addDirectoryIfAbsent(parentDirId, dirName)
                .subscribeOn(Schedulers.elastic())
                .subscribe(dir -> {
                    try (DirectoryStream<Path> ds = Files.newDirectoryStream(source)) {
                        List<Path> files = StreamSupport.stream(ds.spliterator(), false)
                                .filter(p -> {
                                    if (Files.isDirectory(p)) {
                                        osSync2Db(p, dir.getDirId(), p.getFileName().toString());
                                        return false;
                                    } else {
                                        return true;
                                    }
                                })
                                .collect(toList());

                        // 添加书籍
                        addBooks(dir.getDirId(), files);
                    } catch (Throwable t) {
                        logger.error(String.format("sync book in %s  to db failed", source.toString()), t);
                    }
                });

    }

    private void addBooks(Integer dirId, List<Path> files) {
        Flux.fromIterable(files)
                .filter(f ->
                    PDF.getFileExtension().stream()
                            .anyMatch(extention -> f.toString().endsWith(extention)))
                .flatMap(f ->
                    bookService.getBook(dirId, f.getFileName().toString())
                            .map(b -> (Path)null)
                            .switchIfEmpty(Mono.just(f))
                            .flux())
                .filter(Objects::nonNull)
                .map(f -> {
                    Book book = new Book();
                    String fileName = f.getFileName().toString();
                    book.setTitle(fileName.substring(0, fileName.lastIndexOf(".")));
                    book.setDirId(dirId);
                    book.setFormat(PDF.getFormat());
                    return book;
                })
                .buffer(200)
                .subscribe(bookService::addBooks);

        bookService.updateCovers(dirId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        osSync2Db();
    }
}
