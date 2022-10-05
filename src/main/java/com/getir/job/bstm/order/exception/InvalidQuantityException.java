package com.getir.job.bstm.order.exception;

import javax.validation.ValidationException;

public class InvalidQuantityException extends ValidationException {

    public InvalidQuantityException() {
        super("Requested book number should be greater than zero.");
    }
}
