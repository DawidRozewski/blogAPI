package com.example.blogapi.service;

import com.example.blogapi.payload.LoginDto;
import org.springframework.security.core.Authentication;

public interface AuthenticationManagerService {
    Authentication authenticate(LoginDto loginDto);
    String getGeneratedToken(Authentication authentication);
    void setSpringAuthentication(Authentication authentication);
}
