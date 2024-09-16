package com.example.yatzysimulator.service;

import com.example.yatzysimulator.dto.DiceResponse;
import com.example.yatzysimulator.dto.ScoreOfCategories;
import com.example.yatzysimulator.dto.ScoreRequest;
import com.example.yatzysimulator.dto.SumOfScoreAndListedCategoryScore;
import com.example.yatzysimulator.entity.CategoryScores;
import com.example.yatzysimulator.entity.DiceValue;
import com.example.yatzysimulator.repository.CategoryScoreRepository;
import com.example.yatzysimulator.repository.DiceRollRepository;
import com.sun.source.tree.BreakTree;
import org.apache.tomcat.util.collections.CaseInsensitiveKeyMap;
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

    public DiceResponse rollDice(String token) {

        List<Integer> diceRolls = new ArrayList<>();
        for (int i = 0; i < numOfDices; i++) {
            int diceNum = random.nextInt(6) + 1;
            diceRolls.add(diceNum);
        }

        String tok = token;
        DiceValue diceValues = new DiceValue(
                null,
                diceRolls.get(0),
                diceRolls.get(1),
                diceRolls.get(2),
                diceRolls.get(3),
                diceRolls.get(4),
                tok
        );

        diceRepo.save(diceValues);
        System.out.println("new values  " + diceValues);

        DiceResponse diceResponse = new DiceResponse(diceRolls, tok);
        return diceResponse;
    }

    public int scoreCalculation(ScoreRequest request) {

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
            System.out.println("category " + category + " is already filled! choose another category");
        } else {

            CategoryScores scores = new CategoryScores(null, category, token, score);
            scoreRepo.save(scores);
        }

        return score;
    }

    private int yatzy(List<Integer> values) {
        int checker = values.get(0);
        for (int i = 1; i < numOfDices; i++) {
            if (values.get(i) != checker) {
                return 0;
            }
        }
        return 50;
    }

    private int largeStraight(List<Integer> values) {
        values.sort(Comparator.naturalOrder());
        for (int i = 0; i < numOfDices; i++) {
            if (values.get(i) != i + 2) {
                return 0;
            }
        }
        return 50;
    }

    private int smallStraight(List<Integer> values) {
        values.sort(Comparator.naturalOrder());
        for (int i = 0; i < numOfDices; i++) {
            if (values.get(i) != i + 1) {
                return 0;
            }
        }
        return 45;
    }

    private int tripleFiveDoubleSix(List<Integer> values) {
        int counter5 = 0;
        int counter6 = 0;
        for (int i = 0; i < numOfDices; i++) {
            int dice = values.get(i);
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

    private int quadFives(List<Integer> vaues) {
        int counter = 0;
        for (int i = 0; i < numOfDices; i++) {
            if (vaues.get(i) == 5) {
                counter++;
                if (counter == 4) {
                    return 20;
                }
            }
        }
        return 0;
    }

    private int tripleSix(List<Integer> values) {
        int counter = 0;
        for (int i = 0; i < numOfDices; i++) {
            if (values.get(i) == 6) {
                counter++;
                if (counter == 3) {
                    return 18;
                }
            }
        }
        return 0;
    }


    private int doubleSixDoubleFive(List<Integer> values) {
        int counter5 = 0;
        int counter6 = 0;
        int sum = 0;
        for (int i = 0; i < numOfDices; i++) {

            int dice = values.get(i);
            if (dice == 6 && counter6 < 2) {
                counter6++;
            } else if (dice == 5 && (counter5 < 2)) {
                counter5++;

            }
        }

        if (counter6 == 2 && counter5 == 2) {
            return 22;
        } else return 0;

    }

    private int doubleSix(List<Integer> values) {
        int counter = 0;
        for (int i = 0; i < numOfDices; i++) {
            if (values.get(i) == 6) {
                counter++;
                if (counter == 2) {
                    return 12;
                }
            }
        }
        return 0;
    }

    public SumOfScoreAndListedCategoryScore getScore(String token) {
        int totalScore=0;
        List<ScoreOfCategories> result = new ArrayList<>();
        List<CategoryScores> categoryScoresList = scoreRepo.findByToken(token);
        for (CategoryScores c : categoryScoresList) {
            result.add(new ScoreOfCategories(c.getCategory(), c.getScore()));
            totalScore=totalScore+(c.getScore());
        }
        return (new SumOfScoreAndListedCategoryScore(result,totalScore));
    }
}

