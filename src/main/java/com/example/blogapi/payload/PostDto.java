package com.example.blogapi.payload;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private long id;
    private String title;
    private String description;
    private String content;
    private Set<CommentDto> comments;
}
