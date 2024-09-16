package com.example.yatzysimulator.repository;

import com.example.yatzysimulator.entity.DiceValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DiceRollRepository extends JpaRepository<DiceValue,Integer> {
    DiceValue findByToken(String token);
}
