package com.labi.typing.util;

import com.labi.typing.exception.custom.ValidationException;
import com.labi.typing.model.User;
import com.labi.typing.security.jwt.JwtTokenProvider;
import com.labi.typing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class HeaderUtil {

    @Autowired
    private static JwtTokenProvider jwtTokenProvider;

    @Autowired
    private static UserService userService;

    public static User validateUserByHeader(String authHeader) {
        String token = jwtTokenProvider.resolveToken(authHeader);
        User user = userService.findByUsername(jwtTokenProvider.validateToken(token));
        if (user == null) {
            throw new ValidationException("User not found", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return user;
    }
}
