package org.elegant.web.controller;

import org.elegant.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import static org.elegant.enumeration.ImageEnum.JPG;

@RequestMapping("/medias")
@Controller
public class MediaController {
    private MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping(value = "/book-cover/{bookId}")
    public Mono<ResponseEntity> cover(@PathVariable Integer bookId) {
        //todo 添加默认封面图片
        //todo 优化大图片sql查询慢的问题
        //todo 优化浏览器不缓存图片的问题
        return mediaService.getBookCover(bookId)
                .map(cover -> {
                    MediaType mediaType = JPG.getFileExtension().contains(cover.getImageFileExtension()) ?
                            MediaType.IMAGE_JPEG :
                            MediaType.IMAGE_PNG;

                    return ResponseEntity.ok()
                            .contentType(mediaType)
                            .body(cover.getCover());
                });
    }
}
