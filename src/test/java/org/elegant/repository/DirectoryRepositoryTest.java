package org.elegant.repository;

import org.elegant.annotation.test.RepositoryTest;
import org.elegant.model.jooq.tables.pojos.Directory;
import org.jooq.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:sql/directories-init.sql")
@RepositoryTest
public class DirectoryRepositoryTest {
    @Autowired
    private Configuration configuration;

    private DirectoryRepository directoryRepository;

    @Before
    public void setup() {
        directoryRepository = new DirectoryRepository(configuration);
    }

    @Test
    public void testFetchOneDirectoryByParentIdAndName() {
        // 可以正常查询到子文件夹
        Directory directory = directoryRepository.fetchOneDirectory(2, "dir6");
        assertThat(directory).isNotNull();

        // 不能查到自己
        directory = directoryRepository.fetchOneDirectory(2, "dir2");
        assertThat(directory).isNull();

        // 不能查到其它
        directory = directoryRepository.fetchOneDirectory(2, "dir7");
        assertThat(directory).isNull();
    }

    @Test
    public void testFetchAncestors() {
        List<Directory> ancestors = directoryRepository.fetchAncestors(7);

        assertThat(ancestors.size()).isEqualTo(3);
        assertThat(ancestors.get(0).getDirId()).isEqualTo(1);
        assertThat(ancestors.get(1).getDirId()).isEqualTo(2);
        assertThat(ancestors.get(2).getDirId()).isEqualTo(6);
    }

    @Test
    public void testFetchDescendants() {
        List<Directory> descendants = directoryRepository.fetchDescendants(2);

        assertThat(descendants.size()).isEqualTo(3);
        assertThat(descendants.get(0).getName()).isEqualTo("dir6");
        assertThat(descendants.get(1).getName()).isEqualTo("dir8");
        assertThat(descendants.get(2).getName()).isEqualTo("dir7");
    }

    @Test
    public void testFetchSubs() {
        List<Directory> descendants = directoryRepository.fetchSubs(2);

        assertThat(descendants.size()).isEqualTo(2);
        assertThat(descendants.get(0).getName()).isEqualTo("dir6");
        assertThat(descendants.get(1).getName()).isEqualTo("dir8");
    }
}