package com.getir.job.bstm.book.exception;

import javax.persistence.EntityNotFoundException;

public class BookNotFoundException extends EntityNotFoundException {

    public BookNotFoundException(Long id) {
        super("Book is not found with id: " + id);
    }
}
