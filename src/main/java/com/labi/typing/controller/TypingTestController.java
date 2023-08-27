package com.labi.typing.controller;

import com.labi.typing.DTO.GeneratedTestDTO;
import com.labi.typing.service.TypingTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/typing-test")
public class TypingTestController {

    @Autowired
    private TypingTestService typingTestService;

    @GetMapping("/short")
    public ResponseEntity<GeneratedTestDTO> getShortTest() {
        return ResponseEntity.ok(typingTestService.getShortTest());
    }
}
