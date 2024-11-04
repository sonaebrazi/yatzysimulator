package com.example.yatzysimulator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreResponseDto {
    private List<CategoryScoreDto> scoreCategoryList;
    private int totalScore;
}
