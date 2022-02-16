package com.example.blogapi.service.impl;

import com.example.blogapi.dto.PostDto;
import com.example.blogapi.exception.ResourceNotFoundException;
import com.example.blogapi.model.Post;
import com.example.blogapi.repository.PostRepository;
import com.example.blogapi.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        postRepository.save(post);
        return mapToDTO(post);
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    private PostDto mapToDTO(Post post) {
        PostDto postResponse = new PostDto();
        postResponse.setId(post.getId());
        postResponse.setTitle(post.getTitle());
        postResponse.setDescription(post.getDescription());
        postResponse.setContent(post.getContent());
        return postResponse;
    }
}
