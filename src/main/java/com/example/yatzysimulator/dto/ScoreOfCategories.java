package com.example.yatzysimulator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreOfCategories {

    private int category;
    private  int score;
}
