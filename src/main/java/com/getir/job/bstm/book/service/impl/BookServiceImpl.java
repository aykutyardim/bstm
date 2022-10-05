package com.getir.job.bstm.book.service.impl;

import com.getir.job.bstm.book.exception.DataInconsistencyException;
import com.getir.job.bstm.book.exception.OutOfStockException;
import com.getir.job.bstm.book.model.Book;
import com.getir.job.bstm.book.repository.BookRepository;
import com.getir.job.bstm.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Optional<Book> getById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> getAll(){
        return bookRepository.findAll();
    }

    @Override
    public Book save(Book book) {

        if (book != null && (book.getStock() == null || book.getStock() < 1 )) {
            throw new OutOfStockException(book.getStock());
        }
        book.setCreatedAt(new Date());
        book.setUpdatedAt(new Date());
        return bookRepository.save(book);
    }

    @Override
    public Book updateStock(Book book, Integer updatedBy) {

        Integer existingStock = book.getStock();
        Integer newStock = existingStock + updatedBy;

        if (newStock <= 0) {
            throw new OutOfStockException(newStock);
        }

        book.setUpdatedAt(new Date());
        Integer updatedRows = bookRepository.updateStock(book.getId(), existingStock, newStock);

        if (updatedRows > 0) {
            book.setStock(newStock);
            return book;
        }
        else {
            throw new DataInconsistencyException();
        }
    }

}
