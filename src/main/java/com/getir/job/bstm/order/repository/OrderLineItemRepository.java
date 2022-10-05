package com.getir.job.bstm.order.repository;

import com.getir.job.bstm.order.model.OrderLineItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineItemRepository extends JpaRepository<OrderLineItem, Long> {
}
