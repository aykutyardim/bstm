package com.getir.job.bstm.stats.service;

import com.getir.job.bstm.stats.model.Stats;
import com.getir.job.bstm.user.model.User;

import java.util.List;

public interface StatsService {
    List<Stats> getAllByUserIdAndYear(Long userId, Integer year);
    Stats increaseByUserCountAndAmount(User user, Integer count, Double amount);
}
