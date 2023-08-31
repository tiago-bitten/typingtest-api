package com.labi.typing.controller;

import com.labi.typing.DTO.UserRegisterDTO;
import com.labi.typing.security.jwt.JwtTokenProvider;
import com.labi.typing.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        userService.saveUser(userRegisterDTO);
        String token = jwtTokenProvider.generateToken(userRegisterDTO.username());
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }
}
