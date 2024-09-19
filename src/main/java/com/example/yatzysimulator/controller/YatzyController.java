package com.example.yatzysimulator.controller;

import com.example.yatzysimulator.dto.DiceResponseDto;
import com.example.yatzysimulator.dto.ScoreRequestDto;
import com.example.yatzysimulator.dto.ScoreResponseDto;
import com.example.yatzysimulator.service.YatzyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class YatzyController {
    @Autowired
    private YatzyService service;
    @GetMapping("roll/{token}")
    public @ResponseBody DiceResponseDto roll(@PathVariable String token){
        return service.rollDice(token);
    }

    @PostMapping("calculation")
    public int scoreCalculation(@RequestBody ScoreRequestDto request){
       return service.scoreCalculation(request);
    }

    @GetMapping("scores/{token}")
    public @ResponseBody ScoreResponseDto getScores(@PathVariable String token){
        return service.getScore(token);
    }
}
