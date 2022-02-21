package com.example.blogapi.service;

import com.example.blogapi.model.User;
import com.example.blogapi.payload.SignUpDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {
    User mapToEntity(SignUpDto signUpDto);
    void setRoleAdmin(User user);
    boolean userEmailAlreadyExist(@RequestBody SignUpDto signUpDto);
    boolean userAlreadyExist(@RequestBody SignUpDto signUpDto);
    void save(User user);
}
