package com.example.blogapi.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@ApiModel(description = "Post Model Information")
@Data
public class PostDto {

    @ApiModelProperty(value = "Blog Post ID")
    private long id;

    @ApiModelProperty(value = "Blog Post Title")
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @ApiModelProperty(value = "Blog Post Description")
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    @ApiModelProperty(value = "Blog Post Content")
    @NotEmpty
    private String content;

    @ApiModelProperty(value = "Blog Post Comments")
    private Set<CommentDto> comments;
}
