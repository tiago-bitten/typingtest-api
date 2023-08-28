package com.labi.typing.controller;

import com.labi.typing.DTO.GeneratedTestDTO;
import com.labi.typing.DTO.TestRegisterDTO;
import com.labi.typing.DTO.UserDTO;
import com.labi.typing.service.TestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/test")
public class TestController {

    @Autowired
    private TestService typingTestService;

    @PostMapping("/register")
    public ResponseEntity<?> registerTest(@RequestBody @Valid TestRegisterDTO testRegisterDTO) {
        typingTestService.saveTest(testRegisterDTO);
        return ResponseEntity.noContent().build();
    }

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
