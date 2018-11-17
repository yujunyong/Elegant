package org.elegant.repository;

import org.elegant.model.jooq.Tables;
import org.elegant.model.jooq.tables.daos.DirectoryPathDao;
import org.elegant.model.jooq.tables.pojos.DirectoryPath;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.jooq.impl.DSL.using;

@Component
public class DirectoryPathRepository extends DirectoryPathDao {
    org.elegant.model.jooq.tables.DirectoryPath path = Tables.DIRECTORY_PATH;

    @Autowired
    public DirectoryPathRepository(Configuration configuration) {
        super(configuration);
    }

    public List<DirectoryPath> fetchAncestors(Integer dirId) {
        return using(configuration()).selectFrom(path)
                .where(path.DIR_ID.eq(dirId))
                .and(path.PATH_LENGTH.notEqual(0))
                .orderBy(path.PATH_LENGTH.desc())
                .fetchInto(DirectoryPath.class);
    }
}
