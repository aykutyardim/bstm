package com.getir.job.bstm.order.exception;

import javax.persistence.EntityNotFoundException;

public class OrderNotFoundException extends EntityNotFoundException {

    public OrderNotFoundException(Long id) {
        super("Order is not found with id: " + id);
    }
}
