package com.example.shortregister_spring.repository;


import com.example.shortregister_spring.model.ShortPosition;
import com.example.shortregister_spring.model.Shorter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShortPositionRepository extends JpaRepository<ShortPosition, Integer> {
}
