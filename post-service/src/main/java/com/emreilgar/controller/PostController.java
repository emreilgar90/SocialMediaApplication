package com.emreilgar.controller;

import com.emreilgar.dto.request.PostCreateRequestDto;
import com.emreilgar.dto.response.PostCreateResponseDto;
import com.emreilgar.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.emreilgar.constants.RestApi.*;
@RestController
@RequestMapping(POST)
public class PostController {
    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(CREATE)
    public ResponseEntity<PostCreateResponseDto> createPost(@RequestBody PostCreateRequestDto dto){
        return ResponseEntity.ok(postService.createPost(dto));
    }

}
