package com.labi.typing.controller;

import com.labi.typing.DTO.UserScoreDTO;
import com.labi.typing.DTO.UserScoreTopDTO;
import com.labi.typing.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/{id}")
    public ResponseEntity<List<UserScoreDTO>> getUserScore(@PathVariable Long id) {
        List<UserScoreDTO> scores = scoreService.getUserScore(id);
        return new ResponseEntity<>(scores, HttpStatus.OK);
    }

    @GetMapping("/top/short")
    public ResponseEntity<List<UserScoreTopDTO>> getTopScoresShort() {
        List<UserScoreTopDTO> scores = scoreService.getTopScoresShort();
        return new ResponseEntity<>(scores, HttpStatus.OK);
    }

    @GetMapping("/top/medium")
    public ResponseEntity<List<UserScoreTopDTO>> getTopScoresMedium() {
        List<UserScoreTopDTO> scores = scoreService.getTopScoresMedium();
        return new ResponseEntity<>(scores, HttpStatus.OK);
    }

    @GetMapping("/top/long")
    public ResponseEntity<List<UserScoreTopDTO>> getTopScoresLong() {
        List<UserScoreTopDTO> scores = scoreService.getTopScoresLong();
        return new ResponseEntity<>(scores, HttpStatus.OK);
    }
}
