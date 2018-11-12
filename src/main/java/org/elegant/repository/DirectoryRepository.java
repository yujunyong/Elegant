package org.elegant.repository;

import org.elegant.model.jooq.Tables;
import org.elegant.model.jooq.tables.DirectoryPath;
import org.elegant.model.jooq.tables.daos.DirectoryDao;
import org.elegant.model.jooq.tables.pojos.Directory;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.jooq.impl.DSL.using;

@Component
public class DirectoryRepository extends DirectoryDao {
    private org.elegant.model.jooq.tables.Directory dir = Tables.DIRECTORY;
    private DirectoryPath path = Tables.DIRECTORY_PATH;

    @Autowired
    public DirectoryRepository(Configuration configuration) {
        super(configuration);
    }

    public Directory fetchOneDirectory(Integer parentId, String name) {
        return using(configuration())
                .select(dir.fields())
                .from(dir)
                .innerJoin(path)
                .on(dir.DIR_ID.eq(path.DIR_ID))
                .where(path.ANCESTOR.eq(parentId))
                .and(dir.NAME.eq(name))
                .and(path.PATH_LENGTH.eq(1))
                .fetchOneInto(Directory.class);
    }

    public List<Directory> fetchAncestors(Integer dirId) {
        return using(configuration())
                .select(dir.fields())
                .from(dir)
                .innerJoin(path)
                .on(dir.DIR_ID.eq(path.ANCESTOR))
                .where(path.DIR_ID.eq(dirId))
                .and(path.PATH_LENGTH.notEqual(0))
                .orderBy(path.PATH_LENGTH.desc())
                .fetchInto(Directory.class);
    }
}
