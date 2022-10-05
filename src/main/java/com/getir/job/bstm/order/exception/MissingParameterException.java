package com.getir.job.bstm.order.exception;

import org.springframework.web.bind.MissingRequestValueException;

import javax.validation.ValidationException;

public class MissingParameterException extends ValidationException {

    public MissingParameterException(String parameter) {
        super("Missing parameter: " + parameter);
    }
}
