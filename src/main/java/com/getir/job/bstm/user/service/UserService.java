package com.getir.job.bstm.user.service;

import com.getir.job.bstm.order.model.Order;
import com.getir.job.bstm.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);
    Optional<User> getByUsername(String username);
    List<Order> getOrders(Long userId, Integer pageNumber, Integer pageSize);
    Optional<User> getById(Long id);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
