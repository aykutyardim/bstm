package com.getir.job.bstm.book.service;

import com.getir.job.bstm.book.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> getById(Long id);
    List<Book> getAll();
    Book save(Book book);
    Book updateStock(Book book, Integer updatedBy);
}
