package com.practice.prod_features.services;

import com.practice.prod_features.dto.PostDTO;
import com.practice.prod_features.entities.PostEntity;
import com.practice.prod_features.entities.UserEntity;
import com.practice.prod_features.exceptions.ResourceNotFoundException;
import com.practice.prod_features.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PostDTO> getAllPosts() {
        log.info("Fetching all posts");

        List<PostEntity> posts = postRepository.findAll();

        log.debug("Total posts fetched from DB: {}", posts.size());

        List<PostDTO> result = posts.stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());

        log.info("Successfully mapped {} posts to DTO", result.size());

        return result;
    }

    @Override
    public PostDTO createNewPost(PostDTO inputPost) {
        log.info("Creating new post");

        log.debug("Input PostDTO: {}", inputPost);

        PostEntity postEntity = modelMapper.map(inputPost, PostEntity.class);

        PostEntity savedPost = postRepository.save(postEntity);

        log.info("Post created successfully with id={}", savedPost.getId());

        return modelMapper.map(savedPost, PostDTO.class);
    }

    @Override
    public PostDTO getPostById(Long id) {
        log.info("Fetching post with id={}", id);

        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("User:{}", userEntity);

        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Post not found with id={}", id);
                    return new ResourceNotFoundException("Post not found with ID:" + id);
                });

        log.debug("Post found: {}", postEntity);

        return modelMapper.map(postEntity, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(Long postId, PostDTO inputPost) {
        log.info("Updating post with id={}", postId);

        PostEntity olderPost = postRepository.findById(postId)
                .orElseThrow(() -> {
                    log.error("Post not found for update with id={}", postId);
                    return new ResourceNotFoundException("Post not found with ID:" + postId);
                });

        log.debug("Existing post before update: {}", olderPost);
        log.debug("Incoming PostDTO: {}", inputPost);

        inputPost.setId(postId);
        modelMapper.map(inputPost, olderPost);

        PostEntity savedPost = postRepository.save(olderPost);

        log.info("Post updated successfully with id={}", savedPost.getId());

        return modelMapper.map(savedPost, PostDTO.class);
    }
}