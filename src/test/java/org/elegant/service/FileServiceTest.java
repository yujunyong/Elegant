package org.elegant.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elegant.util.FileUtil.getPath;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileServiceTest {
    @Autowired
    private FileService fileService;

    @Test
    public void testGetFilesWithDir() {
        Path path = getPath("book/pdf/dir3");
        List<Path> files = fileService.getFiles(path, p -> p.toString().endsWith(".pdf")).collectList().block();

        Path expect = getPath("book/pdf/dir3/React 16 Essentials, 2nd Edition.pdf");

        assertThat(files).isNotNull();
        assertThat(files.size()).isEqualTo(1);
        assertThat(files.get(0)).isEqualTo(expect);
    }

    @Test
    public void testGetFilesWithDirNeedFile() {
        Path path = getPath("book/pdf/dir3/React 16 Essentials, 2nd Edition.pdf");
        List<Path> files = fileService.getFiles(path, p -> p.toString().endsWith(".pdf")).collectList().block();

        assertThat(files).isNotNull();
        assertThat(files.size()).isEqualTo(1);
        assertThat(files.get(0)).isEqualTo(path);
    }

    @Test
    public void testGetFilesWithDirNotNeedFile() {
        Path path = getPath("book/pdf/dir3/test.txt");
        List<Path> files = fileService.getFiles(path, p -> p.toString().endsWith(".pdf")).collectList().block();

        assertThat(files).isNotNull();
        assertThat(files).isEmpty();
    }

    @Test
    public void testGetSubDirsWithDir() {
        Path path = getPath("book/pdf/dir3");
        List<Path> dirs = fileService.getSubDirs(path).collectList().block();

        Path expect = getPath("book/pdf/dir3/dir4");

        assertThat(dirs).isNotNull();
        assertThat(dirs.size()).isEqualTo(1);
        assertThat(dirs.get(0)).isEqualTo(expect);
    }

    @Test
    public void testGetSubDirsWithFile() {
        Path path = getPath("book/pdf/dir3/test.txt");
        List<Path> dirs = fileService.getSubDirs(path).collectList().block();

        assertThat(dirs).isNotNull();
        assertThat(dirs).isEmpty();
    }
}