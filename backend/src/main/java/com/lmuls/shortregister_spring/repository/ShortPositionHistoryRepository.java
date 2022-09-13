package com.lmuls.shortregister_spring.repository;


import com.lmuls.shortregister_spring.model.ShortPosition;
import com.lmuls.shortregister_spring.model.ShortPositionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;

public interface ShortPositionHistoryRepository extends JpaRepository<ShortPositionHistory, Integer> {
//    @Query("SELECT DISTINCT date from short_position_history WHERE ")
    ShortPositionHistory getDistinctFirstByShortPositionOrderByDate(ShortPosition shortPosition);

    boolean existsByDateAndShortPosition(OffsetDateTime date, ShortPosition shortPosition);
    ShortPositionHistory getByDateAndShortPosition(OffsetDateTime date, ShortPosition shortPosition);
}
