package com.example.yatzysimulator.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayerTotalScoreDto {
    public PlayerTotalScoreDto(String token,String name, Long totalScore) {
        this.token=token;
        this.name = name;
        this.totalScore = totalScore;
    }

    private String token;
    private String name;
    private Long totalScore;

}
