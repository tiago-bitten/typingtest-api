package com.labi.typing.service;

import com.labi.typing.DTO.AuthenticationDTO;
import com.labi.typing.DTO.TokenDTO;
import com.labi.typing.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public TokenDTO login(AuthenticationDTO dto) {
        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        Authentication authenticationManager = this.authenticationManager.authenticate(usernamePassword);

        return new TokenDTO(jwtTokenProvider.generateToken(dto.username()));
    }
}
