package com.getir.job.bstm.order.rest.controller;

import com.getir.job.bstm.order.exception.OrderNotFoundException;
import com.getir.job.bstm.order.mapper.OrderMapper;
import com.getir.job.bstm.order.model.Order;
import com.getir.job.bstm.order.rest.request.OrderRequest;
import com.getir.job.bstm.order.rest.response.OrderResponse;
import com.getir.job.bstm.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;
    private OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAll() {
        try {
            List<OrderResponse> orderResponses = orderService.getAll().stream()
                    .map(order -> orderMapper.entityToResponse(order))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(orderResponses);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Order order = orderService.getById(id)
                    .orElseThrow(() -> new OrderNotFoundException(id));
            return ResponseEntity.ok(orderMapper.entityToResponse(order));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/filter")
    public ResponseEntity<?> getAllByCreatedAtBetween(@RequestParam String start, @RequestParam String  end) {
        try {
            Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(start);
            Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(end);
            List<OrderResponse> orderResponses = orderService.getAllByCreatedAtBetween(startDate, endDate).stream()
                    .map(order -> orderMapper.entityToResponse(order))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(orderResponses);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    @PostMapping ResponseEntity<?> create(Principal principal, @RequestBody OrderRequest orderRequest) {
        try {
            Order order = orderMapper.requestToEntity(principal.getName(), orderRequest);
            OrderResponse orderResponse = orderMapper.entityToResponse(orderService.save(order));
            return ResponseEntity.ok(orderResponse);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
