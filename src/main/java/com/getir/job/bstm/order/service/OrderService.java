package com.getir.job.bstm.order.service;

import com.getir.job.bstm.order.model.Order;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> getAll();
    Optional<Order> getById(Long id);
    List<Order> getAllByCreatedAtBetween(Date startDate, Date endDate);
    Order save(Order order) throws Exception;

    List<Order> getAllByUserIdAndPageNumberAndPageSize(Long userId, Integer pageNumber, Integer pageSize);
}
