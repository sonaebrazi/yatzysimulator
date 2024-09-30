package com.example.yatzysimulator.repository;

import com.example.yatzysimulator.dto.PlayerTotalScoreDto;
import com.example.yatzysimulator.entity.CategoryScores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryScoreRepository extends JpaRepository<CategoryScores,Integer> {
      List<CategoryScores> findByTokenAndCategory(String token, int category);
      List<CategoryScores> findByToken(String token);

      @Query("SELECT new com.example.yatzysimulator.dto.PlayerTotalScoreDto(p.token, p.name, SUM(c.score)) " +
              "FROM CategoryScores c " +
              "JOIN Player p ON c.token = p.token " +
              "GROUP BY p.token, p.name " +
              "HAVING COUNT(c) = 6")
      List<PlayerTotalScoreDto> findPlayersWith14Rolls();
}
