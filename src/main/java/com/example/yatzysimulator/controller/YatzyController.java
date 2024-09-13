package com.example.yatzysimulator.controller;

import com.example.yatzysimulator.dto.ScoreRequest;
import com.example.yatzysimulator.service.YatzyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class YatzyController {
    @Autowired
    private YatzyService service;

    @GetMapping("roll")
    public List<Integer> roll(){
        return service.rollDice();
    }
    @PostMapping("roll")
    public int scoreCalculation(@RequestBody ScoreRequest request){
       return service.scoreCalculation(request);

    }


}
