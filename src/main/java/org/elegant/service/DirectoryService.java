package org.elegant.service;

import com.google.common.base.Joiner;
import com.google.common.base.Supplier;
import org.elegant.model.jooq.tables.pojos.Directory;
import org.elegant.model.jooq.tables.pojos.DirectoryPath;
import org.elegant.repository.DirectoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkState;

@Service
public class DirectoryService {
    public static final Integer ROOT_DIR_ID = 1;
    public static final String ROOT_DIR_NAME = "root";

    private Directory root;
    private DirectoryRepository directoryRepository;

    private DirectoryPathService directoryPathService;
    private PropertyService propertyService;

    @Autowired
    public DirectoryService(DirectoryRepository directoryRepository,
                            DirectoryPathService directoryPathService,
                            PropertyService propertyService) {
        this.directoryRepository = directoryRepository;
        this.directoryPathService = directoryPathService;
        this.propertyService = propertyService;
    }

    @Transactional
    public Mono<Directory> addDirectoryIfAbsent(Integer parentId, String name) {
        return addDirectoryIfAbsent(parentId, new Directory().setName(name));
    }

    @Transactional
    public Mono<Directory> addDirectoryIfAbsent(Integer parentId, Directory dir) {
        Mono<Directory> addDirectory = Mono
                .just((Supplier<Directory>) () ->
                        addDirectoryWithNoCheck(parentId, dir))
                .map(Supplier::get);

        return getDirectory(parentId, dir.getName()).switchIfEmpty(addDirectory);
    }

    public Mono<Directory> getDirectory(Integer parentId, String name) {
        return Mono.justOrEmpty(directoryRepository.fetchOneDirectory(parentId, name));
    }

    public Mono<Directory> getDirectory(Integer dirId) {
        return Mono.justOrEmpty(directoryRepository.fetchOneByDirId(dirId));
    }

    public Flux<Directory> getParentDirecties(Integer dirId) {
        return Flux.fromIterable(directoryRepository.fetchAncestors(dirId));
    }

    public Mono<Path> getOsPath(Integer dirId) {
        return getDirectory(dirId).flatMap(this::getOsPath);
    }

    public Mono<Path> getOsPath(Directory dir) {
        Mono<List<String>> parents = getParentDirecties(dir.getDirId())
                .map(Directory::getName)
                .collectList();
        Mono<String> rootDir = propertyService.getString(PropertyService.ROOT_BOOK_DIR_KEY);

        return Mono.zip(parents, rootDir)
                .map(tuple2 -> {
                    List<String> p = tuple2.getT1();
                    String root = tuple2.getT2();

                    return Joiner.on(File.separator).join(p).replaceFirst(ROOT_DIR_NAME, root);
                })
                .map(Paths::get);
    }

    public Mono<String> getPath(Integer dirId) {
        return getDirectory(dirId).flatMap(this::getPath);
    }

    public Mono<String> getPath(Directory dir) {
        return getParentDirecties(dir.getDirId())
                .map(Directory::getName)
                .collectList()
                .map(parents ->
                        Joiner.on(File.separator).join(parents)
                                .replaceFirst(ROOT_DIR_NAME, ""));

    }

    public Mono<Directory> getRootDirectory() {
        if (Objects.isNull(root)) {
            synchronized (this) {
                if (Objects.isNull(root)) {
                    root = directoryRepository.fetchOneByDirId(ROOT_DIR_ID);
                }
            }
        }
        return Mono.justOrEmpty(root);
    }

    @Transactional
    public Directory addDirectory(Integer parentId, Directory dir) {
        checkState(getDirectory(parentId, dir.getName()).blockOptional().isPresent(), "目录已存在");

        return addDirectoryWithNoCheck(parentId, dir);
    }

    private Directory addDirectoryWithNoCheck(Integer parentId, Directory dir) {
        directoryRepository.insert(dir);
        directoryPathService.getParents(parentId)
                .map(dp -> new DirectoryPath()
                        .setAncestor(dp.getAncestor())
                        .setDirId(dir.getDirId())
                        .setPathLength(dp.getPathLength() + 1))
                .buffer(200)
                .subscribe(directoryPathService::addAll);
        return dir;
    }
}
