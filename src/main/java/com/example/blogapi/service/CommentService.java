package com.example.blogapi.service;

import com.example.blogapi.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
}
