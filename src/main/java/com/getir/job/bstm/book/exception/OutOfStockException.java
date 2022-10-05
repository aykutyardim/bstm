package com.getir.job.bstm.book.exception;

import javax.validation.ValidationException;

public class OutOfStockException extends ValidationException {

    public OutOfStockException(Integer stock) {
        super("Given insufficient stock: " + stock);
    }
}
