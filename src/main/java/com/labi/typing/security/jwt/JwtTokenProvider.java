package com.labi.typing.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.labi.typing.exception.custom.ValidationException;
import com.labi.typing.model.User;
import com.labi.typing.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.labi.typing.util.LoggerUtil.log;

@Service
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("labi")
                    .withSubject(username)
                    .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                    .sign(algorithm);
        }
        catch (JWTCreationException e) {
            throw new ValidationException("Couldn't create JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWT.require(algorithm)
                    .withIssuer("labi")
                    .build()
                    .verify(token)
                    .getSubject();
            return true;
        }
        catch (JWTVerificationException e) {
            log(e.getClass().getSimpleName() + " was thrown - Message: " + e.getMessage());
            return false;
        }
    }

    public String resolveToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        return null;
    }

    public String getUsernameFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("labi")
                .build()
                .verify(token)
                .getSubject();
    }

    public User getUserFromToken(String authHeader, UserService userService) {
        String token = resolveToken(authHeader);
        User user = userService.findByUsername(getUsernameFromToken(token));
        if (user == null) {
            throw new ValidationException("User not found", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return user;
    }
}
