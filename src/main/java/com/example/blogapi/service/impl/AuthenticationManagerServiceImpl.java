package com.example.blogapi.service.impl;

import com.example.blogapi.payload.LoginDto;
import com.example.blogapi.security.JwtTokenProvider;
import com.example.blogapi.service.AuthenticationManagerService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthenticationManagerServiceImpl implements AuthenticationManagerService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Override
    public Authentication authenticate(LoginDto loginDto) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()));
    }

    @Override
    public String getGeneratedToken(Authentication authentication) {
        return tokenProvider.generateToken(authentication);
    }

    @Override
    public void setSpringAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
