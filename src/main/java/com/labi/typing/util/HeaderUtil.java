package com.labi.typing.util;

import com.labi.typing.exception.custom.ValidationException;
import com.labi.typing.model.User;
import com.labi.typing.security.jwt.JwtTokenProvider;
import com.labi.typing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class HeaderUtil {


    public static User validateUserByHeader(String authHeader, UserService userService, JwtTokenProvider jwtTokenProvider) {
        String token = jwtTokenProvider.resolveToken(authHeader);
        User user = userService.findByUsername(jwtTokenProvider.validateToken(token));
        if (user == null) {
            throw new ValidationException("User not found", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return user;
    }
}
