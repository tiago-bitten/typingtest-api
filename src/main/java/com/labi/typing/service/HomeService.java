package com.labi.typing.service;

import com.labi.typing.DTO.test.TestGeneratedDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    private static final String WELCOME_MESSAGE =
            "Hello and welcome to our typing test platform! Dive in and create an account to start having fun!";

    public TestGeneratedDTO getHomeTest() {
        return new TestGeneratedDTO(splitWelcomeMessage());
    }

    private List<String> splitWelcomeMessage() {
        return List.of(WELCOME_MESSAGE.split(" "));
    }
}
