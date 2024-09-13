package com.example.yatzysimulator.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@Service
public class YatzyService {

    private final int numOfDices= 5;
    private Random random= new Random();

    public List<Integer> rollDice(){

        List<Integer> diceRolls = new ArrayList<>();
        for (int i=0; i<numOfDices; i++){
            int diceNum= random.nextInt(6) + 1;
            diceRolls.add(diceNum);
        }

        System.out.println(diceRolls);
        return diceRolls;
    }
}
