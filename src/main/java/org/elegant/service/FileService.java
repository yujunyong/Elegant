package org.elegant.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toList;

@Service
public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    /**
     * path是文件夹时, 返回里面的文件
     * path是文件时, 返回这个文件
     * 出错时, 返回空
     * @param path
     * @return
     */
    public Flux<Path> getFiles(Path path) {
        return getFiles(path, p -> true);
    }

    /**
     * path是文件夹时, 返回里面的文件
     * path是文件时, 返回这个文件
     * 出错时, 返回空
     * @param path
     * @param predicate 返回文件后的过滤函数
     * @return
     */
    public Flux<Path> getFiles(Path path, Predicate<Path> predicate) {
        checkNotNull(path);
        checkNotNull(predicate);

        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
                return Flux.fromIterable(StreamSupport.stream(ds.spliterator(), false)
                        .filter(p -> !Files.isDirectory(p))
                        .filter(predicate)
                        .collect(toList()));
            } catch (Throwable t) {
                logger.error(String.format("get %s files failed", path.toString()), t);
                return Flux.empty();
            }
        } else {
            return Flux.just(path).filter(predicate);
        }
    }

    /**
     * path是文件夹时, 返回子文件夹
     * path是文件时, 返回空,
     * 报错时, 返回空
     * @param path
     * @return
     */
    public Flux<Path> getSubDirs(Path path) {
        return getSubDirs(path, p -> true);
    }

    /**
     * path是文件夹时, 返回子文件夹
     * path是文件时, 返回空,
     * 报错时, 返回空
     * @param path
     * @param predicate 返回文件夹时的过滤函数
     * @return 返回非空子文件夹
     */
    public Flux<Path> getSubDirs(Path path, Predicate<Path> predicate) {
        checkNotNull(path);
        checkNotNull(predicate);

        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
                return Flux.fromIterable(StreamSupport.stream(ds.spliterator(), false)
                        .filter(Files::isDirectory)
                        .filter(predicate)
                        .collect(toList()));
            } catch (Throwable t) {
                logger.error(String.format("get %s sub dirs failed", path.toString()), t);
                return Flux.empty();
            }
        } else {
            return Flux.empty();
        }
    }
}
