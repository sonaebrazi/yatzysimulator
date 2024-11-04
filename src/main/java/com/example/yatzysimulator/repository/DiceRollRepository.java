package com.example.yatzysimulator.repository;
import java.util.List;
import java.util.Optional;

import com.example.yatzysimulator.entity.DiceValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DiceRollRepository extends JpaRepository<DiceValue,Integer> {
    DiceValue findByToken(String token);
    List<DiceValue> findByTokenAndCategoryIsNull(String token);

    List<DiceValue> findByTokenAndCategory(String token, int category);
}
