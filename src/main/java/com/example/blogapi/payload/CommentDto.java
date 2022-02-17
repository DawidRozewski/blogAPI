package com.example.blogapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto {
    private long id;
    private String name;
    private String email;
    private String body;
}
