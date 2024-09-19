package com.example.yatzysimulator.service;

import com.example.yatzysimulator.dto.DiceResponseDto;
import com.example.yatzysimulator.dto.CategoryScoreDto;
import com.example.yatzysimulator.dto.ScoreRequestDto;
import com.example.yatzysimulator.dto.ScoreResponseDto;
import com.example.yatzysimulator.entity.CategoryScores;
import com.example.yatzysimulator.entity.DiceValue;
import com.example.yatzysimulator.repository.CategoryScoreRepository;
import com.example.yatzysimulator.repository.DiceRollRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class YatzyService {
    @Autowired
    private DiceRollRepository diceRepo;

    @Autowired
    private CategoryScoreRepository scoreRepo;

    private final int numOfDices = 5;
    private Random random = new Random();

    public DiceResponseDto rollDice(String token) {

        List<Integer> diceRolls = new ArrayList<>();
        for (int i = 0; i < numOfDices; i++) {
            int diceNum = random.nextInt(6) + 1;
            diceRolls.add(diceNum);
        }

        DiceValue diceValues = new DiceValue(
                null,
                diceRolls.get(0),
                diceRolls.get(1),
                diceRolls.get(2),
                diceRolls.get(3),
                diceRolls.get(4),
                token
        );

        diceRepo.save(diceValues);
        DiceResponseDto diceResponse = new DiceResponseDto(diceRolls, token);
        return diceResponse;
    }

    public int scoreCalculation(ScoreRequestDto request) {

        int category = request.getCategory();
        String token = request.getToken();
        DiceValue rolls = diceRepo.findByToken(token);
        if (rolls == null) {
            throw new IllegalArgumentException("No dice rolls found for token: " + token);
        }
        List<Integer> diceValueList = new ArrayList<>();
        diceValueList.add(rolls.getDice1());
        diceValueList.add(rolls.getDice2());
        diceValueList.add(rolls.getDice3());
        diceValueList.add(rolls.getDice4());
        diceValueList.add(rolls.getDice5());
        int score = 0;
        switch (category) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                int count = 0;
                for (int i = 0; i < numOfDices; i++) {
                    if (diceValueList.get(i) == category) count++;
                }
                score = count * category;
                break;
            case 7:
                score = doubleSix(diceValueList);
                break;
            case 8:
                score = doubleSixDoubleFive(diceValueList);
                break;
            case 9:
                score = tripleSix(diceValueList);
                break;
            case 10:
                score = quadFives(diceValueList);
                break;
            case 11:
                score = smallStraight(diceValueList);
                break;
            case 12:
                score = largeStraight(diceValueList);
                break;
            case 13:
                score = tripleFiveDoubleSix(diceValueList);
                break;
            case 14:
                score = yatzy(diceValueList);
                break;
        }

        List<CategoryScores> existingscores = scoreRepo.findByTokenAndCategory(token, category);

        if (!(existingscores.isEmpty())) {
            throw new IllegalArgumentException("Category " + category + " is already filled! Choose another category.");
        } else {

            CategoryScores scores = new CategoryScores(null, category, token, score);
            scoreRepo.save(scores);
        }

        return score;
    }

    private int yatzy(List<Integer> values) {
        int checker = values.get(0);
        boolean allSame = values.stream()
                .allMatch(value -> value==checker);
        return allSame ? 50 : 0;
    }

    private int largeStraight(List<Integer> values) {
        List<Integer> largStraight = Arrays.asList(2,3,4,5,6);
        return values.stream()
                .sorted()
                .equals(largStraight) ? 50 : 0;
    }

    private int smallStraight(List<Integer> values) {
        List<Integer> smallStraight = Arrays.asList(1,2,3,4,5);
        return values.stream()
                .sorted()
                .equals(smallStraight) ? 45 : 0;
    }

    private int tripleFiveDoubleSix(List<Integer> values) {
        long count5 = values.stream()
                .filter(value -> value==5)
                .count();
        long count6 = values.stream()
                .filter(value -> value==6)
                .count();
        return (count5==3 && count6==2) ? 27 : 0;
    }

    private int quadFives(List<Integer> values) {
        long count = values.stream()
                .filter(value -> value==5)
                .count();
        return count>=4 ? 20 : 0;
    }

    private int tripleSix(List<Integer> values) {
        long count = values.stream()
                .filter(value -> value==6)
                .count();
        return count>=3 ? 18 : 0;
    }

    private int doubleSixDoubleFive(List<Integer> values) {
        long count6=values.stream()
                .filter(value -> value ==6)
                .count();
        long count5=values.stream()
                .filter(value -> value ==5)
                .count();
        return (count5>= 2 && count6>=2) ? 22 : 0;
    }

    private int doubleSix(List<Integer> values) {
        long count = values.stream()
                .filter(value -> value==6)
                .count();
        return count>=2 ? 12 : 0;
    }

    public ScoreResponseDto getScore(String token) {
        int totalScore=0;
        List<CategoryScoreDto> result = new ArrayList<>();
        List<CategoryScores> categoryScoresList = scoreRepo.findByToken(token);
        for (CategoryScores c : categoryScoresList) {
            result.add(new CategoryScoreDto(c.getCategory(), c.getScore()));
            totalScore=totalScore+(c.getScore());
        }
        return (new ScoreResponseDto(result,totalScore));
    }
}

