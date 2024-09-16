package com.example.yatzysimulator.controller;

import com.example.yatzysimulator.dto.DiceResponse;
import com.example.yatzysimulator.dto.ScoreOfCategories;
import com.example.yatzysimulator.dto.ScoreRequest;
import com.example.yatzysimulator.dto.SumOfScoreAndListedCategoryScore;
import com.example.yatzysimulator.service.YatzyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class YatzyController {
    @Autowired
    private YatzyService service;

    @GetMapping("roll/{token}")
    public @ResponseBody DiceResponse roll(@PathVariable String token){
        return service.rollDice(token);
    }
    @PostMapping("roll")
    public int scoreCalculation(@RequestBody ScoreRequest request){
       return service.scoreCalculation(request);

    }

    @GetMapping("scores/{token}")
    public @ResponseBody SumOfScoreAndListedCategoryScore getScores(@PathVariable String token){
        return service.getScore(token);
    }


}
