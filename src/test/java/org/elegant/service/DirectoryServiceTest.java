package org.elegant.service;

import com.google.common.collect.Lists;
import org.elegant.model.jooq.tables.pojos.Directory;
import org.elegant.model.jooq.tables.pojos.DirectoryPath;
import org.elegant.repository.DirectoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DirectoryServiceTest {

    @MockBean
    private DirectoryPathService directoryPathService;

    @MockBean
    private PropertyService propertyService;

    @MockBean
    private DirectoryRepository directoryRepository;

    @Autowired
    private DirectoryService directoryService;

    /**
     * 不创建, 返回已经有文件夹
     */
    @Test
    public void testAddDirectoryIfAbsentNotAdd() {
        Directory subDir = new Directory()
                .setName("sub")
                .setDirId(2);
        when(directoryRepository.fetchOneDirectory(1, "sub")).thenReturn(subDir);

        Directory expect = directoryService.addDirectoryIfAbsent(1, "sub").block();

        assertThat(expect).isNotNull();
        assertThat(expect.getDirId()).isEqualTo(subDir.getDirId());
        assertThat(expect.getName()).isEqualTo(subDir.getName());
    }

    // todo 重写相关方法
    /**
     * 创建文件夹, 创建本身路径和父子路径
     */
//    @Test
//    public void testAddDirectoryIfAbsentAddParentPath() {
//        Directory createdDir = new Directory()
//                .setName("created")
//                .setDirId(3);
//        when(directoryRepository.fetchOneDirectory(1, "created")).thenReturn(null);
//        when(directoryPathService.getAncestors(1)).thenReturn(Flux.empty());
//
//        directoryService.addDirectoryIfAbsent(1, createdDir).block();
//
//        verify(directoryRepository).insert(createdDir);
//        verify(directoryPathService).addDirectoryPath(any(DirectoryPath.class), any(DirectoryPath.class));
//        verify(directoryPathService, never()).addDirectoryPath(anyCollection());
//    }

    /**
     * 创建文件夹, 创建本身路径,父子路径和祖先路径
     */
//    @Test
//    public void testAddDirectoryIfAbsentAddAncestorPath() {
//        Directory createdDir = new Directory()
//                .setName("created")
//                .setDirId(3);
//        DirectoryPath path = new DirectoryPath()
//                .setAncestor(1)
//                .setDirId(2)
//                .setPathLength(1);
//        when(directoryRepository.fetchOneDirectory(1, "created")).thenReturn(null);
//        when(directoryPathService.getAncestors(2)).thenReturn(Flux.just(path));
//
//        directoryService.addDirectoryIfAbsent(2, createdDir).block();
//
//        verify(directoryRepository).insert(createdDir);
//        verify(directoryPathService).addDirectoryPath(any(DirectoryPath.class), any(DirectoryPath.class));
//        verify(directoryPathService).addDirectoryPath(anyCollection());
//    }

    @Test
    public void testGetOsPath() {
        Directory rootDir = new Directory().setDirId(1).setName("root");
        Directory subDir = new Directory().setDirId(2).setName("sub");
        Directory currentDir = new Directory().setDirId(3).setName("current");
        when(directoryRepository.fetchAncestors(3)).thenReturn(Lists.newArrayList(rootDir, subDir));
        when(propertyService.getString(PropertyService.ROOT_BOOK_DIR_KEY)).thenReturn(Mono.just("/elegant/dir"));

        Path expect = Paths.get("/elegant/dir/sub/current");

        Path path = directoryService.getOsPath(currentDir).block();

        assertThat(expect).isEqualTo(path);
    }

    @Test
    public void testGetPath() {
        Directory rootDir = new Directory().setDirId(1).setName("root");
        Directory subDir = new Directory().setDirId(2).setName("sub");
        Directory currentDir = new Directory().setDirId(3).setName("current");
        when(directoryRepository.fetchAncestors(3)).thenReturn(Lists.newArrayList(rootDir, subDir));

        String expect = "/sub/current";

        String path = directoryService.getPath(currentDir).block();

        assertThat(expect).isEqualTo(path);
    }
}