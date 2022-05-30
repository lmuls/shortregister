package com.example.shortregister_spring.repository;


import com.example.shortregister_spring.model.ShortPosition;
import com.example.shortregister_spring.model.ShortPositionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortPositionHistoryRepository extends JpaRepository<ShortPositionHistory, Integer> {
//    List<Shorter> findShorterByName(String name);
}
