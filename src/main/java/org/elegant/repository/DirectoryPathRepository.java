package org.elegant.repository;

import org.elegant.model.jooq.tables.daos.DirectoryPathDao;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DirectoryPathRepository extends DirectoryPathDao {

    @Autowired
    public DirectoryPathRepository(Configuration configuration) {
        super(configuration);
    }
}
