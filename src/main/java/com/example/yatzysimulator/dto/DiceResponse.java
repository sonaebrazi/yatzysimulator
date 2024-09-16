package com.example.yatzysimulator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiceResponse {

    private List<Integer> diceValues;
    private String token;
}
