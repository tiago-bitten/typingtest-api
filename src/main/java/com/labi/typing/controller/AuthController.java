package com.labi.typing.controller;

import com.labi.typing.DTO.AuthenticationDTO;
import com.labi.typing.DTO.TokenDTO;
import com.labi.typing.security.jwt.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

//    @PostMapping("/login")
//    public ResponseEntity<TokenDTO> login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
//
//    }
}
