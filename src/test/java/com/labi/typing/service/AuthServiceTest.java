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
import static org.mockito.Mockito.*;

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
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("username", "password");
        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password());
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(usernamePassword)).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(authenticationDTO.username())).thenReturn("Bearer generatedToken");

        TokenDTO tokenDTO = authService.login(authenticationDTO);

        assertNotNull(tokenDTO);
        assertTrue(tokenDTO.token().startsWith("Bearer "));
    }

    @Test
    public void testLogin_Failure() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("username", "password");
        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password());
        when(authenticationManager.authenticate(usernamePassword)).thenThrow(new BadCredentialsException("Username or password is incorrect"));

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> authService.login(authenticationDTO));
        assertEquals("Username or password is incorrect", exception.getMessage());
    }

}
