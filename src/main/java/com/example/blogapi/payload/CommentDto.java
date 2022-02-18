package com.example.blogapi.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private long id;
    private String name;
    private String email;
    private String body;
}
