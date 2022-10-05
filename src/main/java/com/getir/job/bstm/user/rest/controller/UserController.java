package com.getir.job.bstm.user.rest.controller;

import com.getir.job.bstm.auth.exception.UserNotFoundException;
import com.getir.job.bstm.order.mapper.OrderMapper;
import com.getir.job.bstm.order.rest.response.OrderResponse;
import com.getir.job.bstm.user.model.User;
import com.getir.job.bstm.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private OrderMapper orderMapper;

    public UserController(UserService userService, OrderMapper orderMapper) {
        this.userService = userService;
        this.orderMapper = orderMapper;
    }

    @GetMapping("/{id}/order")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<?> getOrders(@PathVariable Long id, @RequestParam String pageNumber, @RequestParam String pageSize) {

        User user = userService.getById(id)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(id)));
        try {
            Integer pageN = Integer.valueOf(pageNumber);
            Integer pageS = Integer.valueOf(pageSize);

            List<OrderResponse> orderResponses = userService.getOrders(id, pageN, pageS).stream()
                    .map(order -> orderMapper.entityToResponse(order))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(orderResponses);

        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
