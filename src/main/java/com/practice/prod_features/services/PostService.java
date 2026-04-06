package com.practice.prod_features.services;

import com.practice.prod_features.dto.PostDTO;

import java.util.List;

public interface PostService {

    public List<PostDTO> getAllPosts();

    public PostDTO createNewPost(PostDTO inputPost);

    public PostDTO getPostById(Long id);

    PostDTO updatePost(Long postId, PostDTO inputPost);
}
