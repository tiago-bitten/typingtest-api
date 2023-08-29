package com.labi.typing.controller;

import com.labi.typing.DTO.GeneratedTestDTO;
import com.labi.typing.DTO.TestRegisterDTO;
import com.labi.typing.DTO.UserDTO;
import com.labi.typing.service.TestService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
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
    public ResponseEntity<?> registerTest(@RequestBody @Valid TestRegisterDTO testRegisterDTO) {
        typingTestService.saveTest(testRegisterDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/short")
    public ResponseEntity<GeneratedTestDTO> getShortTest() {
        return new ResponseEntity<>(typingTestService.getShortTest(), HttpStatus.OK);
    }

    @GetMapping("/medium")
    public ResponseEntity<GeneratedTestDTO> getMediumTest() {
        return new ResponseEntity<>(typingTestService.getMediumTest(), HttpStatus.OK);
    }

    @GetMapping("/long")
    public ResponseEntity<GeneratedTestDTO> getLongTest() {
        return new ResponseEntity<>(typingTestService.getLongTest(), HttpStatus.OK);
    }
}
