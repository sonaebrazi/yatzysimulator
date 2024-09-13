package com.example.yatzysimulator.service;

import com.example.yatzysimulator.dto.ScoreRequest;
import com.sun.source.tree.BreakTree;
import org.apache.tomcat.util.collections.CaseInsensitiveKeyMap;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Service
public class YatzyService {

    private final int numOfDices = 5;
    private Random random = new Random();

    public List<Integer> rollDice() {

        List<Integer> diceRolls = new ArrayList<>();
        for (int i = 0; i < numOfDices; i++) {
            int diceNum = random.nextInt(6) + 1;
            diceRolls.add(diceNum);
        }

        return diceRolls;
    }

    public int scoreCalculation(ScoreRequest request) {

        List<Integer> rolls = request.getRollDices();
        int category = request.getCategory();
        switch (category) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                int count = 0;
                for (int i = 0; i < numOfDices; i++) {
                    if (rolls.get(i) == category) count++;
                }
                return count * category;
            case 7:
                return doubleSix(rolls);
            case 8:
                return doubleSixDoubleFive(rolls);
            case 9:
                return tripleSix(rolls);
            case 10:
                return quadFives(rolls);
            case 11:
                return smallStraight(rolls);
            case 12:
                return largeStraight(rolls);
            case 13:
                return tripleFiveDoubleSix(rolls);
            case 14:
                return yatzy(rolls);
        }

        return 0;
    }

    private int yatzy(List<Integer> rolls) {
        int checker = rolls.get(0);
        for (int i=1; i<numOfDices;i++){
            if(rolls.get(i)!= checker){
                return 0;
            }
        }
        return  50;
    }

    private int largeStraight(List<Integer> rolls) {
        rolls.sort(Comparator.naturalOrder());
        for(int i=0;i<numOfDices; i++){
            if(rolls.get(i)!= i+2){
                return 0;}
        }
        return 50;
    }

    private int smallStraight(List<Integer> rolls) {
        rolls.sort(Comparator.naturalOrder());
        for(int i=0;i<numOfDices; i++){
            if(rolls.get(i)!= i+1){
                return 0;}
        }
        return 45;
    }

    private int tripleFiveDoubleSix(List<Integer> rolls) {
        int counter5 = 0;
        int counter6 = 0;
        for (int i = 0; i < numOfDices; i++) {
            int dice = rolls.get(i);
            if (dice == 6 && counter6 < 2) {
                counter6++;
            } else if (dice == 5 && (counter5 < 3)) {
                counter5++;

            }
        }

        if (counter6 == 2 && counter5 == 3) {
            return 27;
        } else return 0;
    }

    private int quadFives(List<Integer> rolls) {
        int counter = 0;
        for (int i = 0; i < numOfDices; i++) {
            if (rolls.get(i) == 5) {
                counter++;
                if (counter == 4) {
                    return 20;
                }
            }
        }
        return 0;
    }

    private int tripleSix(List<Integer> rolls) {
            int counter = 0;
            for (int i = 0; i < numOfDices; i++) {
                if (rolls.get(i) == 6) {
                    counter++;
                    if (counter == 3) {
                        return 18;
                    }
                }
            }
            return 0;
        }



    private int doubleSixDoubleFive(List<Integer> rolls) {
        int counter5 = 0;
        int counter6 = 0;
        int sum = 0;
        for (int i = 0; i < numOfDices; i++) {

            int dice = rolls.get(i);
            if (dice == 6 && counter6 < 2) {
                counter6++;
            } else if (dice == 5 && (counter5 < 2)) {
                counter5++;

            }
        }

        System.out.println(counter6);
        System.out.println(counter5);
        if (counter6 == 2 && counter5 == 2) {
            return 22;
        } else return 0;

    }

    private int doubleSix(List<Integer> rolls) {
        int counter = 0;
        for (int i = 0; i < numOfDices; i++) {
            if (rolls.get(i) == 6) {
                counter++;
                if (counter == 2) {
                    return 12;
                }
            }
        }
        return 0;
    }
}

