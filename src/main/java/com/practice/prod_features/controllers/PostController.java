package com.practice.prod_features.controllers;

import com.practice.prod_features.dto.PostDTO;
import com.practice.prod_features.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts(){
        List<PostDTO> postDTOS = postService.getAllPosts();
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id){
        PostDTO postDTO = postService.getPostById(id);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostDTO> createNewPost(@RequestBody PostDTO inputPost){
        PostDTO postDTO = postService.createNewPost(inputPost);
        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long postId, @RequestBody PostDTO inputPost){
        PostDTO postDTO = postService.updatePost(postId, inputPost);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }
}
