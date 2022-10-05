package com.getir.job.bstm.order.mapper;

import com.getir.job.bstm.auth.exception.UserNotFoundException;
import com.getir.job.bstm.order.exception.MissingParameterException;
import com.getir.job.bstm.order.model.Order;
import com.getir.job.bstm.order.rest.request.OrderRequest;
import com.getir.job.bstm.order.rest.response.OrderLineItemResponse;
import com.getir.job.bstm.order.rest.response.OrderResponse;
import com.getir.job.bstm.user.model.User;
import com.getir.job.bstm.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private UserService userService;
    private OrderLineItemMapper orderLineItemMapper;

    public OrderMapper(UserService userService, OrderLineItemMapper orderLineItemMapper) {
        this.userService = userService;
        this.orderLineItemMapper = orderLineItemMapper;
    }

    public Order requestToEntity (String username, OrderRequest orderRequest){

        try{
            Order order = new Order();

            User user = userService.getByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException(username));
            order.setUser(user);

            order.setOrderLineItems(orderRequest.getOrderLineItemRequests()
                    .stream().map(orderLineItemRequest -> orderLineItemMapper.requestToEntity(orderLineItemRequest))
                    .collect(Collectors.toList()));

            return order;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public OrderResponse entityToResponse (Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setCreatedAt(order.getCreatedAt());
        orderResponse.setUserId(order.getUser().getId());

        List<OrderLineItemResponse> orderLineItemResponses = order.getOrderLineItems().stream()
                .map(orderLineItem -> orderLineItemMapper.entityToResponse(orderLineItem))
                .collect(Collectors.toList());

        orderResponse.setOrderLineItemResponses(orderLineItemResponses);

        return orderResponse;

    }
}
