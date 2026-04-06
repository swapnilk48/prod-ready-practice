package com.practice.prod_features.services;

import com.practice.prod_features.dto.PostDTO;
import com.practice.prod_features.entities.PostEntity;
import com.practice.prod_features.exceptions.ResourceNotFoundException;
import com.practice.prod_features.repositories.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper){
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDTO.class))
                .collect(Collectors.toList());
    }



    @Override
    public PostDTO createNewPost(PostDTO inputPost) {
        PostEntity postEntity = modelMapper.map(inputPost, PostEntity.class);
        return modelMapper.map(postRepository.save(postEntity), PostDTO.class);
    }

    @Override
    public PostDTO getPostById(Long id) {
        PostEntity postEntity = postRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID:"+id));
        return modelMapper.map(postEntity, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(Long postId, PostDTO inputPost) {
        PostEntity olderPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID:"+postId));
        inputPost.setId(postId);
        modelMapper.map(inputPost, olderPost);
        PostEntity savedPost = postRepository.save(olderPost);
        return modelMapper.map(savedPost, PostDTO.class);
    }
}
