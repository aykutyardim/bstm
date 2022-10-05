package com.getir.job.bstm.book.exception;

import javax.persistence.EntityNotFoundException;

public class DataInconsistencyException extends EntityNotFoundException {

    public DataInconsistencyException() {
        super("Something went wrong! Try it again later.");
    }
}
