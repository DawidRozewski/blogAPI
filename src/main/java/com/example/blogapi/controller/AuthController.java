package com.example.blogapi.controller;

import com.example.blogapi.model.Role;
import com.example.blogapi.model.User;
import com.example.blogapi.payload.JWTAuthResponse;
import com.example.blogapi.payload.LoginDto;
import com.example.blogapi.payload.SignUpDto;
import com.example.blogapi.repository.RoleRepository;
import com.example.blogapi.repository.UserRepository;
import com.example.blogapi.security.JwtTokenProvider;
import com.example.blogapi.service.AuthenticationManagerService;
import com.example.blogapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManagerService authenticationManagerService;

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManagerService.authenticate(loginDto);
        authenticationManagerService.setSpringAuthentication(authentication);
        String token = authenticationManagerService.getGeneratedToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        if (userService.userAlreadyExist(signUpDto)) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }
        if (userService.userEmailAlreadyExist(signUpDto)) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }
        User user = userService.mapToEntity(signUpDto);
        userService.save(user);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);

    }

}
