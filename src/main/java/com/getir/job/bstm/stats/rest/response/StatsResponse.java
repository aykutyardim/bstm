package com.getir.job.bstm.stats.rest.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatsResponse {
    private Integer totalOrderCount;
    private Integer totalBookCount;
    private Double totalPurchasedAmount;
    private Integer year;
    private Integer month;
}
