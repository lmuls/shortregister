package com.example.shortregister_spring.repository;


import com.example.shortregister_spring.model.ShortPosition;
import com.example.shortregister_spring.model.ShortPositionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface ShortPositionHistoryRepository extends JpaRepository<ShortPositionHistory, Integer> {
//    @Query("SELECT DISTINCT date from short_position_history WHERE ")
    ShortPositionHistory getDistinctFirstByShortPositionOrderByDate(ShortPosition shortPosition);

    boolean existsByDateAndShortPosition(Date date, ShortPosition shortPosition);
    ShortPositionHistory getByDateAndShortPosition(Date date, ShortPosition shortPosition);
}
