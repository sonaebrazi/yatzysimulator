package com.example.yatzysimulator.controller;

import com.example.yatzysimulator.dto.*;
import com.example.yatzysimulator.service.YatzyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class YatzyController {
    @Autowired
    private YatzyService service;

    @GetMapping("start-game/{name}")
    public @ResponseBody StartGameDto startGame(@PathVariable String name){
        return service.createPlayer(name);
    }

    @GetMapping("roll-dice/{token}")
    public @ResponseBody DiceResponseDto rollDice(@PathVariable String token){
        return service.rollDice(token);
    }

    @PostMapping("calculation")
    public ScoreValueDto scoreCalculation(@RequestBody ScoreRequestDto request){
       return service.scoreCalculation(request);
    }

    @GetMapping("scores/{token}")
    public @ResponseBody ScoreResponseDto getScores(@PathVariable String token){
        return service.getScore(token);
    }

    @GetMapping("completed-games")
    public @ResponseBody List<PlayerTotalScoreDto> getCompletedGames(){
        return service.findCompletedGames();
    }
}
