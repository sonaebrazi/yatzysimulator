package com.example.yatzysimulator.service;

import com.example.yatzysimulator.dto.ScoreRequestDto;
import com.example.yatzysimulator.dto.ScoreValueDto;
import com.example.yatzysimulator.entity.CategoryScores;
import com.example.yatzysimulator.entity.DiceValue;
import com.example.yatzysimulator.entity.Player;
import com.example.yatzysimulator.repository.CategoryScoreRepository;
import com.example.yatzysimulator.repository.DiceRollRepository;
import com.example.yatzysimulator.repository.PlayerRepository;
import jakarta.persistence.Column;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class YatzyServiceTest {
    @Mock
    private DiceRollRepository diceRollRepo;
    @Mock
    private PlayerRepository playerRepo;
    @Mock
    private CategoryScoreRepository categoryScoreRepo;

    @InjectMocks
    private YatzyService yatzyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);   // Initializes the mocks and injects them into the service
    }

    @Test
    public void testScoreCalculationCat1() {

        String token = "test-token";
        int category = 1;

        when(categoryScoreRepo.findByTokenAndCategory(anyString(), anyInt())).thenReturn(null);
        when(diceRollRepo.findByTokenAndCategoryIsNull(anyString())).thenReturn(
                List.of(
                        new DiceValue(1, 1, 1, 1, 4, 5, token, null)
                )
        );

        when(categoryScoreRepo.save(ArgumentMatchers.<CategoryScores>any())).thenReturn(null);

        ScoreRequestDto request = new ScoreRequestDto(1, token);
        ScoreValueDto result = yatzyService.scoreCalculation(request);
        assertEquals(3, result.getScore());
    }

    @Test
    public void testScoreCalculationCat2() {

        String token = "test-token";
        int category = 2;

        when(categoryScoreRepo.findByTokenAndCategory(anyString(), anyInt())).thenReturn(null);
        when(diceRollRepo.findByTokenAndCategoryIsNull(anyString())).thenReturn(
                List.of(
                        new DiceValue(1, 2, 2, 2, 2, 2, token, null)
                )
        );

        when(categoryScoreRepo.save(ArgumentMatchers.<CategoryScores>any())).thenReturn(null);

        ScoreRequestDto request = new ScoreRequestDto(2, token);
        ScoreValueDto result = yatzyService.scoreCalculation(request);
        assertEquals(result.getScore(), 10);
    }

    @Test
    public void testScoreCalculationCat3() {

        String token = "test-token";
        int category = 3;

        when(categoryScoreRepo.findByTokenAndCategory(anyString(), anyInt())).thenReturn(null);
        when(diceRollRepo.findByTokenAndCategoryIsNull(anyString())).thenReturn(
                List.of(
                        new DiceValue(1, 3, 2, 3, 2, 3, token, null)
                )
        );

        when(categoryScoreRepo.save(ArgumentMatchers.<CategoryScores>any())).thenReturn(null);

        ScoreRequestDto request = new ScoreRequestDto(3, token);
        ScoreValueDto result = yatzyService.scoreCalculation(request);
        assertEquals(result.getScore(), 9);
    }

    @Test
    public void testScoreCalculationCat4() {

        String token = "test-token";
        int category = 4;

        when(categoryScoreRepo.findByTokenAndCategory(anyString(), anyInt())).thenReturn(null);
        when(diceRollRepo.findByTokenAndCategoryIsNull(anyString())).thenReturn(
                List.of(
                        new DiceValue(1, 2, 4, 2, 2, 2, token, null)
                )
        );

        when(categoryScoreRepo.save(ArgumentMatchers.<CategoryScores>any())).thenReturn(null);

        ScoreRequestDto request = new ScoreRequestDto(4, token);
        ScoreValueDto result = yatzyService.scoreCalculation(request);
        assertEquals( 4,result.getScore());
    }

    @Test
    public void testScoreCalculationCat5() {

        String token = "test-token";
        int category = 5;

        when(categoryScoreRepo.findByTokenAndCategory(anyString(), anyInt())).thenReturn(null);
        when(diceRollRepo.findByTokenAndCategoryIsNull(anyString())).thenReturn(
                List.of(
                        new DiceValue(1, 2, 5, 5, 2, 2, token, null)
                )
        );

        when(categoryScoreRepo.save(ArgumentMatchers.<CategoryScores>any())).thenReturn(null);

        ScoreRequestDto request = new ScoreRequestDto(5, token);
        ScoreValueDto result = yatzyService.scoreCalculation(request);
        assertEquals( 10,result.getScore());
    }

    @Test
    public void testScoreCalculationCat6() {

        String token = "test-token";
        int category = 6;

        when(categoryScoreRepo.findByTokenAndCategory(anyString(), anyInt())).thenReturn(null);
        when(diceRollRepo.findByTokenAndCategoryIsNull(anyString())).thenReturn(
                List.of(
                        new DiceValue(1, 6, 6, 6, 6, 6, token, null)
                )
        );

        when(categoryScoreRepo.save(ArgumentMatchers.<CategoryScores>any())).thenReturn(null);

        ScoreRequestDto request = new ScoreRequestDto(6, token);
        ScoreValueDto result = yatzyService.scoreCalculation(request);
        assertEquals( 30,result.getScore());
    }

    @Test
    public void testScoreCalculationCatDoubleSix() {

        String token = "test-token";
        int category = 7;

        when(categoryScoreRepo.findByTokenAndCategory(anyString(), anyInt())).thenReturn(null);
        when(diceRollRepo.findByTokenAndCategoryIsNull(anyString())).thenReturn(
                List.of(
                        new DiceValue(1, 6, 6, 6, 4, 1, token, null)
                )
        );

        when(categoryScoreRepo.save(ArgumentMatchers.<CategoryScores>any())).thenReturn(null);

        ScoreRequestDto request = new ScoreRequestDto(7, token);
        ScoreValueDto result = yatzyService.scoreCalculation(request);
        assertEquals( 12,result.getScore());
    }

    @Test
    public void testScoreCalculationCatDoubleSixDoubleFive() {

        String token = "test-token";
        int category = 8;

        when(categoryScoreRepo.findByTokenAndCategory(anyString(), anyInt())).thenReturn(null);
        when(diceRollRepo.findByTokenAndCategoryIsNull(anyString())).thenReturn(
                List.of(
                        new DiceValue(1, 6, 6, 5, 5, 6, token, null)
                )
        );

        when(categoryScoreRepo.save(ArgumentMatchers.<CategoryScores>any())).thenReturn(null);

        ScoreRequestDto request = new ScoreRequestDto(8, token);
        ScoreValueDto result = yatzyService.scoreCalculation(request);
        assertEquals(22,result.getScore());
    }

    @Test
    public void testScoreCalculationCatTripleSix() {

        String token = "test-token";
        int category = 9;

        when(categoryScoreRepo.findByTokenAndCategory(anyString(), anyInt())).thenReturn(null);
        when(diceRollRepo.findByTokenAndCategoryIsNull(anyString())).thenReturn(
                List.of(
                        new DiceValue(1, 6, 2, 4, 5, 3, token, null)
                )
        );

        when(categoryScoreRepo.save(ArgumentMatchers.<CategoryScores>any())).thenReturn(null);

        ScoreRequestDto request = new ScoreRequestDto(9, token);
        ScoreValueDto result = yatzyService.scoreCalculation(request);
        assertEquals( 0,result.getScore());
    }

    @Test
    public void testScoreCalculationCatQuadFives() {

        String token = "test-token";
        int category = 10;

        when(categoryScoreRepo.findByTokenAndCategory(anyString(), anyInt())).thenReturn(null);
        when(diceRollRepo.findByTokenAndCategoryIsNull(anyString())).thenReturn(
                List.of(
                        new DiceValue(1, 1, 4, 5, 5, 6, token, null)
                )
        );

        when(categoryScoreRepo.save(ArgumentMatchers.<CategoryScores>any())).thenReturn(null);

        ScoreRequestDto request = new ScoreRequestDto(10, token);
        ScoreValueDto result = yatzyService.scoreCalculation(request);
        assertEquals( 0,result.getScore());
    }

    @Test
    public void testScoreCalculationCatSmallStraight() {

        String token = "test-token";
        int category = 11;

        when(categoryScoreRepo.findByTokenAndCategory(anyString(), anyInt())).thenReturn(null);
        when(diceRollRepo.findByTokenAndCategoryIsNull(anyString())).thenReturn(
                List.of(
                        new DiceValue(1, 1, 2, 3, 4, 5, token, null)
                )
        );

        when(categoryScoreRepo.save(ArgumentMatchers.<CategoryScores>any())).thenReturn(null);

        ScoreRequestDto request = new ScoreRequestDto(11, token);
        ScoreValueDto result = yatzyService.scoreCalculation(request);
        assertEquals( 45,result.getScore());
    }

    @Test
    public void testScoreCalculationCatLargeStraight() {

        String token = "test-token";
        int category = 12;

        when(categoryScoreRepo.findByTokenAndCategory(anyString(), anyInt())).thenReturn(null);
        when(diceRollRepo.findByTokenAndCategoryIsNull(anyString())).thenReturn(
                List.of(
                        new DiceValue(1, 2, 3, 4, 5, 6, token, null)
                )
        );

        when(categoryScoreRepo.save(ArgumentMatchers.<CategoryScores>any())).thenReturn(null);

        ScoreRequestDto request = new ScoreRequestDto(12, token);
        ScoreValueDto result = yatzyService.scoreCalculation(request);
        assertEquals( 50,result.getScore());
    }

    @Test
    public void testScoreCalculationCatTripleFiveDoubleSix() {

        String token = "test-token";
        int category = 13;

        when(categoryScoreRepo.findByTokenAndCategory(anyString(), anyInt())).thenReturn(null);
        when(diceRollRepo.findByTokenAndCategoryIsNull(anyString())).thenReturn(
                List.of(
                        new DiceValue(1, 5, 5, 5, 6, 6, token, null)
                )
        );

        when(categoryScoreRepo.save(ArgumentMatchers.<CategoryScores>any())).thenReturn(null);

        ScoreRequestDto request = new ScoreRequestDto(13, token);
        ScoreValueDto result = yatzyService.scoreCalculation(request);
        assertEquals(27,result.getScore());
    }

    @Test
    public void testScoreCalculationCatYatzy() {

        String token = "test-token";
        int category = 14;

        when(categoryScoreRepo.findByTokenAndCategory(anyString(), anyInt())).thenReturn(null);
        when(diceRollRepo.findByTokenAndCategoryIsNull(anyString())).thenReturn(
                List.of(
                        new DiceValue(1, 6, 6, 6, 6, 6, token, null)
                )
        );

        when(categoryScoreRepo.save(ArgumentMatchers.<CategoryScores>any())).thenReturn(null);

        ScoreRequestDto request = new ScoreRequestDto(14, token);
        ScoreValueDto result = yatzyService.scoreCalculation(request);
        assertEquals(50,result.getScore());
    }

}
