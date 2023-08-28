package com.labi.typing.controller;

import com.labi.typing.DTO.GeneratedTestDTO;
import com.labi.typing.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController {

    @Autowired
    private TestService typingTestService;

    @GetMapping("/short")
    public ResponseEntity<GeneratedTestDTO> getShortTest() {
        return ResponseEntity.ok(typingTestService.getShortTest());
    }

    @GetMapping("/medium")
    public ResponseEntity<GeneratedTestDTO> getMediumTest() {
        return ResponseEntity.ok(typingTestService.getMediumTest());
    }

    @GetMapping("/long")
    public ResponseEntity<GeneratedTestDTO> getLongTest() {
        return ResponseEntity.ok(typingTestService.getLongTest());
    }
}
