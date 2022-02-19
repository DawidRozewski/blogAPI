package com.example.blogapi.service.impl;

import com.example.blogapi.payload.PostDto;
import com.example.blogapi.exception.ResourceNotFoundException;
import com.example.blogapi.model.Post;
import com.example.blogapi.payload.PostResponse;
import com.example.blogapi.repository.PostRepository;
import com.example.blogapi.service.PostService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    private final ModelMapper mapper;

    @Override
    public void deletePostById(long id) {
        Post post = getPost(id);

        postRepository.delete(post);
    }


    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = getOrders(sortBy, sortDir);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> postsList = posts.getContent();
        List<PostDto> content = getContentFromPost(postsList);
        return getPostResponse(pageNo, pageSize, posts, content);
    }
    private List<PostDto> getContentFromPost(List<Post> postsList) {
        return postsList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private PostResponse getPostResponse(int pageNo, int pageSize, Page<Post> posts, List<PostDto> content) {
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(pageNo);
        postResponse.setPageSize(pageSize);
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    private Sort getOrders(String sortBy, String sortDir) {
        return sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = getPost(id);
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);
        return mapToDTO(newPost);
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = getPost(id);
        return mapToDTO(post);
    }

    private Post mapToEntity(PostDto postDto) {
        return mapper.map(postDto, Post.class);
    }

    private PostDto mapToDTO(Post post) {
        return mapper.map(post, PostDto.class);
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    }
}
