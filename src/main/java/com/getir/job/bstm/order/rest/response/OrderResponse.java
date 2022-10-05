package com.getir.job.bstm.order.rest.response;

import com.getir.job.bstm.order.model.OrderLineItem;
import com.getir.job.bstm.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Long id;
    private Date createdAt;
    private Long userId;
    private List<OrderLineItemResponse> orderLineItemResponses;
}
