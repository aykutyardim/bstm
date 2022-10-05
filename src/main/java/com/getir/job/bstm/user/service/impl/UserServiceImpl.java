package com.getir.job.bstm.user.service.impl;

import com.getir.job.bstm.order.model.Order;
import com.getir.job.bstm.order.repository.OrderRepository;
import com.getir.job.bstm.order.rest.response.OrderResponse;
import com.getir.job.bstm.order.service.OrderService;
import com.getir.job.bstm.user.model.User;
import com.getir.job.bstm.user.repository.UserRepository;
import com.getir.job.bstm.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private OrderService orderService;

    public UserServiceImpl(UserRepository userRepository, OrderService orderService) {
        this.userRepository = userRepository;
        this.orderService = orderService;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getByUsername(String username){
        return userRepository.findByUsername(username);
    }


    @Override
    public Optional<User> getById(Long id){
        return userRepository.findById(id);
    }
    @Override
    public Boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<Order> getOrders(Long userId, Integer pageNumber, Integer pageSize){
        return orderService.getAllByUserIdAndPageNumberAndPageSize(userId, pageNumber, pageSize);
    }
}
