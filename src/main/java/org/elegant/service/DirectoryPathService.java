package org.elegant.service;

import org.elegant.model.jooq.tables.pojos.DirectoryPath;
import org.elegant.repository.DirectoryPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Service
public class DirectoryPathService {
    private DirectoryPathRepository directoryPathRepository;

    @Autowired
    public DirectoryPathService(DirectoryPathRepository directoryPathRepository) {
        this.directoryPathRepository = directoryPathRepository;
    }

    public Flux<DirectoryPath> getAncestors(Integer dirId) {
        return Flux.fromIterable(directoryPathRepository.fetchAncestors(dirId));
    }

    public Mono<Void> addDirectoryPath(DirectoryPath path) {
        directoryPathRepository.insert(path);
        return Mono.empty();
    }

    public Mono<Void> addDirectoryPath(DirectoryPath... paths) {
        directoryPathRepository.insert(paths);
        return Mono.empty();
    }

    public Mono<Void> addDirectoryPath(Collection<DirectoryPath> paths) {
        directoryPathRepository.insert(paths);
        return Mono.empty();
    }
}
