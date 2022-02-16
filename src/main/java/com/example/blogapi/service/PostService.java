package com.example.blogapi.service;

import com.example.blogapi.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPost();
    PostDto getPostById(long id);
    PostDto updatePost(PostDto postDto, long id);

}
