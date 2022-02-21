package com.example.blogapi.service.impl;

import com.example.blogapi.model.Role;
import com.example.blogapi.model.User;
import com.example.blogapi.payload.SignUpDto;
import com.example.blogapi.repository.RoleRepository;
import com.example.blogapi.repository.UserRepository;
import com.example.blogapi.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper mapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User mapToEntity(SignUpDto signUpDto) {
        User user = mapper.map(signUpDto, User.class);
        setRoleAdmin(user);
        return user;
    }

    @Override
    public void setRoleAdmin(User user) {
        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
    }

    @Override
    public boolean userEmailAlreadyExist(SignUpDto signUpDto) {
        return userRepository.existsByEmail(signUpDto.getEmail());
    }

    @Override
    public boolean userAlreadyExist(SignUpDto signUpDto) {
        return userRepository.existsByUsername(signUpDto.getUsername());
    }
}
