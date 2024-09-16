package com.example.yatzysimulator.repository;

import com.example.yatzysimulator.dto.ScoreRequest;
import com.example.yatzysimulator.entity.CategoryScores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryScoreRepository extends JpaRepository<CategoryScores,Integer> {
      List<CategoryScores> findByTokenAndCategory(String token, int category);
      List<CategoryScores> findByToken(String token);
}
