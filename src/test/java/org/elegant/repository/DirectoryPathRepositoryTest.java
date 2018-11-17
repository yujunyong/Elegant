package org.elegant.repository;

import org.elegant.annotation.test.RepositoryTest;
import org.elegant.model.jooq.tables.pojos.Directory;
import org.elegant.model.jooq.tables.pojos.DirectoryPath;
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
public class DirectoryPathRepositoryTest {
    @Autowired
    private Configuration configuration;

    private DirectoryPathRepository pathRepository;

    @Before
    public void setup() {
        pathRepository = new DirectoryPathRepository(configuration);
    }

    @Test
    public void testFetchAncestors() {
        List<DirectoryPath> ancestors = pathRepository.fetchAncestors(7);

        assertThat(ancestors.size()).isEqualTo(3);
        assertThat(ancestors.get(0).getAncestor()).isEqualTo(1);
        assertThat(ancestors.get(1).getAncestor()).isEqualTo(2);
        assertThat(ancestors.get(2).getAncestor()).isEqualTo(6);
    }
}