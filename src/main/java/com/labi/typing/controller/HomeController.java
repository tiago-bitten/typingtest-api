package com.labi.typing.controller;

import com.labi.typing.DTO.test.TestGeneratedDTO;
import com.labi.typing.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping
    public ResponseEntity<TestGeneratedDTO> getHomeTest() {
        return new ResponseEntity<>(homeService.getHomeTest(), HttpStatus.OK);
    }
}
