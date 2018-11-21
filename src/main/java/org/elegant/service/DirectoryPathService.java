package org.elegant.service;

import org.elegant.model.jooq.tables.pojos.DirectoryPath;
import org.elegant.repository.DirectoryPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collection;

import static reactor.core.scheduler.Schedulers.elastic;
import static reactor.core.scheduler.Schedulers.resetFactory;

@Service
public class DirectoryPathService {
    private DirectoryPathRepository directoryPathRepository;

    @Autowired
    public DirectoryPathService(DirectoryPathRepository directoryPathRepository) {
        this.directoryPathRepository = directoryPathRepository;
    }

    public Flux<DirectoryPath> getAncestors(Integer dirId) {
        return Mono.just(dirId)
                .flatMapIterable(id -> directoryPathRepository.fetchAncestors(id))
                .subscribeOn(elastic());
    }

    public Mono<Void> addDirectoryPath(DirectoryPath path) {
        return Mono.just(path)
                .doOnNext(directoryPathRepository::insert)
                .subscribeOn(elastic())
                .then();
    }

    public Mono<Void> addDirectoryPath(DirectoryPath... paths) {
        return Flux.fromArray(paths)
                .buffer(200)
                .doOnNext(directoryPathRepository::insert)
                .subscribeOn(elastic())
                .then();
    }

    public Mono<Void> addDirectoryPath(Collection<DirectoryPath> paths) {
        return Flux.fromIterable(paths)
                .buffer(200)
                .doOnNext(directoryPathRepository::insert)
                .subscribeOn(elastic())
                .then();
    }
}
