package com.labi.typing.controller;

import com.labi.typing.DTO.score.ScoreTopDTO;
import com.labi.typing.DTO.score.ScoreUserDTO;
import com.labi.typing.DTO.score.ScoreUserTopDTO;
import com.labi.typing.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/user")
    public ResponseEntity<Page<ScoreUserDTO>> getUserAllScore(@RequestHeader("Authorization") String authHeader,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(scoreService.getUserAllScore(authHeader, pageable), HttpStatus.OK);
    }

    @GetMapping("/user/top/short")
    public ResponseEntity<Page<ScoreUserTopDTO>> getUserTopShortScore(@RequestHeader("Authorization") String authHeader,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(scoreService.getUserTopShortScore(authHeader, pageable), HttpStatus.OK);
    }

    @GetMapping("/user/top/medium")
    public ResponseEntity<Page<ScoreUserTopDTO>> getUserTopMediumScore(@RequestParam("Authorization") String authHeader,
                                                                       @RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(scoreService.getUserTopMediumScore(authHeader, pageable), HttpStatus.OK);
    }

    @GetMapping("/user/top/long")
    public ResponseEntity<Page<ScoreUserTopDTO>> getUserTopLongScore(@RequestParam("Authorization") String authHeader,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(scoreService.getUserTopLongScore(authHeader, pageable), HttpStatus.OK);
    }

    @GetMapping("/top/short")
    public ResponseEntity<Page<ScoreTopDTO>> getTopShortScore(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(scoreService.getTopShortScore(pageable), HttpStatus.OK);
    }

    @GetMapping("/top/medium")
    public ResponseEntity<Page<ScoreTopDTO>> getTopMediumScore(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(scoreService.getTopMediumScore(pageable), HttpStatus.OK);
    }

    @GetMapping("/top/long")
    public ResponseEntity<Page<ScoreTopDTO>> getTopLongScore(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(scoreService.getTopLongScore(pageable), HttpStatus.OK);
    }
}
