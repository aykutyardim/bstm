package com.getir.job.bstm.stats.service.impl;

import com.getir.job.bstm.BstmApplication;
import com.getir.job.bstm.stats.model.Stats;
import com.getir.job.bstm.stats.repository.StatsRepository;
import com.getir.job.bstm.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BstmApplication.class)
class StatsServiceImplTest {

    private Stats stats;
    private User user;
    private StatsServiceImpl statsService;

    @Mock
    private StatsRepository statsRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.statsService = new StatsServiceImpl(statsRepository);
        this.stats = new Stats();
        stats.setId(1L);
        this.user = new User();
        user.setId(1L);
        this.stats.setTotalPurchasedAmount(10.00);
        this.stats.setTotalOrderCount(1);
        this.stats.setTotalBookCount(1);

    }

    @Test
    void getAllByUserIdAndYear() {

        List<Stats> statsList = new ArrayList<>();
        statsList.add(this.stats);

        doReturn(statsList).when(statsRepository).findAllByUser_IdAndYear(this.user.getId(), 2022);

        List<Stats> actual = statsService.getAllByUserIdAndYear(this.user.getId(), 2022);
        List<Stats> expected = statsList;

        assertNotNull(actual);
        assertNotNull(actual.get(0));
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
    }

    @Test
    void shouldIncreaseByUserCountAndAmount() {


        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Integer month = localDate.getMonthValue();
        Integer year = localDate.getYear();

        doReturn(Optional.of(this.stats)).when(statsRepository).findByUser_IdAndMonthAndYear(this.user.getId(), month, year);

        this.stats.setTotalOrderCount(this.stats.getTotalOrderCount() + 1);
        this.stats.setTotalBookCount(this.stats.getTotalBookCount() + 1);
        this.stats.setTotalPurchasedAmount(this.stats.getTotalPurchasedAmount() + 10.00);

        doReturn(this.stats).when(statsRepository).save(this.stats);

        Stats actual = statsService.increaseByUserCountAndAmount(this.user, 1, 10.00);
        Stats expected = this.stats;

        assertEquals(expected.getTotalBookCount(), actual.getTotalBookCount());
        assertEquals(expected.getTotalPurchasedAmount(), actual.getTotalPurchasedAmount());
        assertEquals(expected.getTotalOrderCount(), actual.getTotalOrderCount());

    }

    @Test
    void shouldCreateAndIncreaseByUserCountAndAmount() {


        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Integer month = localDate.getMonthValue();
        Integer year = localDate.getYear();

        Optional<Stats> optionalStats = Optional.empty();

        doReturn(optionalStats).when(statsRepository).findByUser_IdAndMonthAndYear(this.user.getId(), month, year);

        when(statsRepository.save(any(Stats.class))).thenReturn(this.stats);

        Stats actual = statsService.increaseByUserCountAndAmount(this.user, 1, 10.0);
        Stats expected = this.stats;

        assertEquals(expected.getTotalBookCount(), actual.getTotalBookCount());
        assertEquals(expected.getTotalPurchasedAmount(), actual.getTotalPurchasedAmount());
        assertEquals(expected.getTotalOrderCount(), actual.getTotalOrderCount());

    }
}