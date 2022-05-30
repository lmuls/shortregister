package com.example.shortregister_spring.repository;


import com.example.shortregister_spring.model.Shorter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShorterRepository extends JpaRepository<Shorter, Integer> {
    Shorter findByCompanyName(String company_name);
}
