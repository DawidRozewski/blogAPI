package com.example.blogapi.service.impl;

import com.example.blogapi.exception.BlogAPIExcpetion;
import com.example.blogapi.exception.ResourceNotFoundException;
import com.example.blogapi.model.Comment;
import com.example.blogapi.model.Post;
import com.example.blogapi.payload.CommentDto;
import com.example.blogapi.repository.CommentRepository;
import com.example.blogapi.repository.PostRepository;
import com.example.blogapi.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper mapper;

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = getPost(postId);
        Comment comment = getComment(commentId);
        ifCommentDoesntBelongToPostThrowException(post, comment);

        commentRepository.delete(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest) {
        Post post = getPost(postId);
        Comment comment = getComment(commentId);
        ifCommentDoesntBelongToPostThrowException(post, comment);
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDTO(updatedComment);
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post  = getPost(postId);
        Comment comment = getComment(commentId);
        ifCommentDoesntBelongToPostThrowException(post, comment);

        return mapToDTO(comment);
    }

    private void ifCommentDoesntBelongToPostThrowException(Post post, Comment comment) {
        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIExcpetion(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = getPost(postId);
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return mapToDTO(newComment);
    }

    private CommentDto mapToDTO(Comment comment) {
        return mapper.map(comment, CommentDto.class);
    }

    private Comment mapToEntity(CommentDto commentDto) {
        return mapper.map(commentDto, Comment.class);
    }
}
