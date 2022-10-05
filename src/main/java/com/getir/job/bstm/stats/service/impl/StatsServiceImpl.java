package com.getir.job.bstm.stats.service.impl;

import com.getir.job.bstm.stats.model.Stats;
import com.getir.job.bstm.stats.repository.StatsRepository;
import com.getir.job.bstm.stats.service.StatsService;
import com.getir.job.bstm.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StatsServiceImpl implements StatsService {

    private StatsRepository statsRepository;

    public StatsServiceImpl(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    @Override
    public List<Stats> getAllByUserIdAndYear(Long userId, Integer year) {
        return statsRepository.findAllByUser_IdAndYear(userId, year);
    }

    @Override
    public Stats increaseByUserCountAndAmount(User user, Integer count, Double amount){

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Integer month = localDate.getMonthValue();
        Integer year = localDate.getYear();


        Optional<Stats> stats = statsRepository.findByUser_IdAndMonthAndYear(
                user.getId(), month, year);

        if (!stats.isPresent()) {
            Stats newStats = new Stats();
            newStats.setUser(user);
            newStats.setMonth(month);
            newStats.setYear(year);
            newStats.setTotalBookCount(count);
            newStats.setTotalOrderCount(1);
            newStats.setTotalPurchasedAmount(amount);
            return statsRepository.save(newStats);
        }
        else {
            Stats existing = stats.get();
            existing.setTotalBookCount(existing.getTotalBookCount() + count);
            existing.setTotalOrderCount(existing.getTotalOrderCount() + 1);
            existing.setTotalPurchasedAmount(existing.getTotalPurchasedAmount() + amount);
            return statsRepository.save(existing);
        }
    }
}
