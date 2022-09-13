package com.lmuls.shortregister_spring.repository;


import com.lmuls.shortregister_spring.model.Shorter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShorterRepository extends JpaRepository<Shorter, Integer> {
    Shorter getByCompanyName(String company_name);
    boolean existsByCompanyName(String company_name);
}
