package com.labi.typing.controller;

import com.labi.typing.DTO.GeneratedTestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/typing-test")
public class TypingTestController {

    @GetMapping("/short")
    public ResponseEntity<GeneratedTestDTO> getShortTest() {
        return ResponseEntity.ok(new GeneratedTestDTO(List.of("Hello", "World")));
    }
}
