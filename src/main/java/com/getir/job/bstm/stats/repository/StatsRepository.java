package com.getir.job.bstm.stats.repository;

import com.getir.job.bstm.stats.model.Stats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StatsRepository extends JpaRepository<Stats, Long> {
    List<Stats> findAllByUser_IdAndYear(Long userId, Integer year);
    Optional<Stats> findByUser_IdAndMonthAndYear(Long userId, Integer month, Integer year);
}
