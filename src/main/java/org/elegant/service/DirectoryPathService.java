package org.elegant.service;

import org.elegant.model.jooq.tables.pojos.DirectoryPath;
import org.elegant.repository.DirectoryPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Collection;

@Service
public class DirectoryPathService {
    private DirectoryPathRepository directoryPathRepository;

    @Autowired
    public DirectoryPathService(DirectoryPathRepository directoryPathRepository) {
        this.directoryPathRepository = directoryPathRepository;
    }

    public Flux<DirectoryPath> getParents(Integer dirId) {
        return Flux.fromIterable(directoryPathRepository.fetchByDirId(dirId));
    }

    public void addAll(Collection<DirectoryPath> directoryPaths) {
        directoryPathRepository.insert(directoryPaths);
    }
}
