package com.labi.typing.service;

import com.labi.typing.DTO.AuthenticationDTO;
import com.labi.typing.DTO.TokenDTO;
import com.labi.typing.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLogin_Success() {
        AuthenticationDTO dto = new AuthenticationDTO("username", "password");
        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(usernamePassword)).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(dto.username())).thenReturn("generatedToken");

        TokenDTO tokenDTO = authService.login(dto);

        assertNotNull(tokenDTO);
        assertEquals("generatedToken", tokenDTO.token());
    }

    @Test
    public void testLogin_Failure() {
        AuthenticationDTO dto = new AuthenticationDTO("username", "password");
        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        when(authenticationManager.authenticate(usernamePassword)).thenThrow(new BadCredentialsException("Username or password is incorrect"));

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> authService.login(dto));
        assertEquals("Username or password is incorrect", exception.getMessage());
    }
}
