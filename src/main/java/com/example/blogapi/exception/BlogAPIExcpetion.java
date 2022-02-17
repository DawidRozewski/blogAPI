package com.example.blogapi.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;


public class BlogAPIExcpetion extends RuntimeException{

    private HttpStatus status;
    private String message;


    public BlogAPIExcpetion(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public BlogAPIExcpetion(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }
}
