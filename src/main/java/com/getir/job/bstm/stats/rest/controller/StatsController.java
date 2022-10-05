package com.getir.job.bstm.stats.rest.controller;

import com.getir.job.bstm.stats.mapper.StatsMapper;
import com.getir.job.bstm.stats.rest.response.StatsResponse;
import com.getir.job.bstm.stats.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stats")
public class StatsController {
    private StatsService statsService;
    private StatsMapper statsMapper;

    public StatsController(StatsService statsService, StatsMapper statsMapper) {
        this.statsService = statsService;
        this.statsMapper = statsMapper;
    }

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllByUserIdAndYear(@RequestParam String userId, @RequestParam String year) {
        try{
            Long userId_ = Long.valueOf(userId);
            Integer year_ = Integer.valueOf(year);
            List<StatsResponse> statsResponses = statsService.getAllByUserIdAndYear(userId_, year_)
                    .stream().map(stats -> statsMapper.entityToResponse(stats))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(statsResponses);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
