package com.labi.typing.controller;

import com.labi.typing.DTO.score.ScoreUserDTO;
import com.labi.typing.DTO.score.ScoreTopDTO;
import com.labi.typing.DTO.score.ScoreUserTopDTO;
import com.labi.typing.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/user")
    public ResponseEntity<List<ScoreUserDTO>> getUserAllScore(@RequestHeader("Authorization") String authHeader) {
        return new ResponseEntity<>(scoreService.getUserAllScore(authHeader), HttpStatus.OK);
    }

    @GetMapping("/user/top/short")
    public ResponseEntity<List<ScoreUserTopDTO>> getUserTopShortScore(@RequestHeader("Authorization") String authHeader) {
        return new ResponseEntity<>(scoreService.getUserTopShortScore(authHeader), HttpStatus.OK);
    }

    @GetMapping("/user/top/medium")
    public ResponseEntity<List<ScoreUserTopDTO>> getUserTopMediumScore(@RequestParam("Authorization") String authHeader) {
        return new ResponseEntity<>(scoreService.getUserTopMediumScore(authHeader), HttpStatus.OK);
    }

    @GetMapping("/user/top/long")
    public ResponseEntity<List<ScoreUserTopDTO>> getUserTopLongScore(@RequestParam("Authorization") String authHeader) {
        return new ResponseEntity<>(scoreService.getUserTopLongScore(authHeader), HttpStatus.OK);
    }

    @GetMapping("/top/short")
    public ResponseEntity<List<ScoreTopDTO>> getTopShortScore() {
        return new ResponseEntity<>(scoreService.getTopShortScore(), HttpStatus.OK);
    }

    @GetMapping("/top/medium")
    public ResponseEntity<List<ScoreTopDTO>> getTopMediumScore() {
        return new ResponseEntity<>(scoreService.getTopMediumScore(), HttpStatus.OK);
    }

    @GetMapping("/top/long")
    public ResponseEntity<List<ScoreTopDTO>> getTopLongScore() {
        return new ResponseEntity<>(scoreService.getTopLongScore(), HttpStatus.OK);
    }
}
