package com.labi.typing.controller;

import com.labi.typing.DTO.TestGeneratedDTO;
import com.labi.typing.DTO.TestRegisterDTO;
import com.labi.typing.service.TestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/test")
public class TestController {

    @Autowired
    private TestService typingTestService;

    @PostMapping("/register")
    public ResponseEntity<?> registerTest(@RequestBody @Valid TestRegisterDTO testRegisterDTO, @RequestHeader("Authorization") String authHeader) {
        typingTestService.saveTest(testRegisterDTO, authHeader);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/short")
    public ResponseEntity<TestGeneratedDTO> getShortTest() {
        return new ResponseEntity<>(typingTestService.getShortTest(), HttpStatus.OK);
    }

    @GetMapping("/medium")
    public ResponseEntity<TestGeneratedDTO> getMediumTest() {
        return new ResponseEntity<>(typingTestService.getMediumTest(), HttpStatus.OK);
    }

    @GetMapping("/long")
    public ResponseEntity<TestGeneratedDTO> getLongTest() {
        return new ResponseEntity<>(typingTestService.getLongTest(), HttpStatus.OK);
    }
}
