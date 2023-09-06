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
    public ResponseEntity<List<ScoreUserDTO>> getUserScore(@RequestHeader("Authorization") String authHeader) {
        List<ScoreUserDTO> scores = scoreService.getUserScores(authHeader);
        return new ResponseEntity<>(scores, HttpStatus.OK);
    }

    @GetMapping("/user/top/short")
    public ResponseEntity<List<ScoreUserTopDTO>> getUserTopShort(@RequestHeader("Authorization") String authHeader) {
        List<ScoreUserTopDTO> scores = scoreService.getUserTopShort(authHeader);
        return new ResponseEntity<>(scores, HttpStatus.OK);
    }

    @GetMapping("/user/top/medium")
    public ResponseEntity<List<ScoreUserTopDTO>> getUserTopMedium(@RequestParam("Authorization") String authHeader) {
        List<ScoreUserTopDTO> scores = scoreService.getUserTopMedium(authHeader);
        return new ResponseEntity<>(scores, HttpStatus.OK);
    }

    @GetMapping("/user/top/long")
    public ResponseEntity<List<ScoreUserTopDTO>> getUserTopLong(@RequestParam("Authorization") String authHeader) {
        List<ScoreUserTopDTO> scores = scoreService.getUserTopLong(authHeader);
        return new ResponseEntity<>(scores, HttpStatus.OK);
    }

    @GetMapping("/top/short")
    public ResponseEntity<List<ScoreTopDTO>> getTopScoresShort() {
        List<ScoreTopDTO> scores = scoreService.getTopScoresShort();
        return new ResponseEntity<>(scores, HttpStatus.OK);
    }

    @GetMapping("/top/medium")
    public ResponseEntity<List<ScoreTopDTO>> getTopScoresMedium() {
        List<ScoreTopDTO> scores = scoreService.getTopScoresMedium();
        return new ResponseEntity<>(scores, HttpStatus.OK);
    }

    @GetMapping("/top/long")
    public ResponseEntity<List<ScoreTopDTO>> getTopScoresLong() {
        List<ScoreTopDTO> scores = scoreService.getTopScoresLong();
        return new ResponseEntity<>(scores, HttpStatus.OK);
    }
}
