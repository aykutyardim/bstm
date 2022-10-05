package com.getir.job.bstm.stats.model;

import com.getir.job.bstm.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer totalOrderCount;
    private Integer totalBookCount;
    private Double totalPurchasedAmount;
    private Integer year;
    private Integer month;
}
