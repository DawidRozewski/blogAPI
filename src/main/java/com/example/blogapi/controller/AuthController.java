package com.example.blogapi.controller;

import com.example.blogapi.model.Role;
import com.example.blogapi.model.User;
import com.example.blogapi.payload.JWTAuthResponse;
import com.example.blogapi.payload.LoginDto;
import com.example.blogapi.payload.SignUpDto;
import com.example.blogapi.service.AuthenticationManagerService;
import com.example.blogapi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Auth controller exposes signin and signup REST API's")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManagerService authenticationManagerService;

    @ApiOperation(value = "REST API to Register or SignUp user to Blog app")
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManagerService.authenticate(loginDto);
        authenticationManagerService.setSpringAuthentication(authentication);
        String token = authenticationManagerService.getGeneratedToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    @ApiOperation(value = "REST API to SignIn or Login user to Blog app")
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
