package com.getir.job.bstm.order.service.impl;

import com.getir.job.bstm.order.model.Order;
import com.getir.job.bstm.order.repository.OrderRepository;
import com.getir.job.bstm.order.service.OrderLineItemService;
import com.getir.job.bstm.order.service.OrderService;
import com.getir.job.bstm.stats.service.StatsService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private OrderLineItemService orderLineItemService;
    private StatsService statsService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderLineItemService orderLineItemService, StatsService statsService) {
        this.orderRepository = orderRepository;
        this.orderLineItemService = orderLineItemService;
        this.statsService = statsService;
    }

    @Override
    public List<Order> getAll(){
        return orderRepository.findAll(Sort.by("id"));
    }

    @Override
    public Optional<Order> getById(Long id){
        return orderRepository.findById(id);
    }
    @Override
    public List<Order> getAllByCreatedAtBetween(Date startDate, Date endDate){
        return orderRepository.findByCreatedAtBetween(startDate, endDate);
    }

    public Order save(Order order) {

        // statistic values
        AtomicReference<Integer> count = new AtomicReference<>(0);
        AtomicReference<Double> amount = new AtomicReference<>(0.0);

        order.setCreatedAt(new Date());

        // create order
        Order createdOrder = orderRepository.save(order);

        // create order line items
        createdOrder.setOrderLineItems(
                order.getOrderLineItems().stream().map(orderLineItem -> {
                    orderLineItem.setOrder(createdOrder);
                    count.updateAndGet(v -> v + orderLineItem.getQuantity());
                    amount.updateAndGet(v -> v + orderLineItem.getQuantity() * orderLineItem.getBook().getPrice());
                    return orderLineItemService.save(orderLineItem);
                }).collect(Collectors.toList())
        );

        // update stats
        statsService.increaseByUserCountAndAmount(order.getUser(), count.get(), amount.get());

        return createdOrder;
    }

    @Override
    public List<Order> getAllByUserIdAndPageNumberAndPageSize(Long userId, Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findAllByUser_Id(userId, pageable);
    }

}
