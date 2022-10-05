package com.getir.job.bstm.book.rest.controller;

import com.getir.job.bstm.book.exception.BookNotFoundException;
import com.getir.job.bstm.book.mapper.BookMapper;
import com.getir.job.bstm.book.model.Book;
import com.getir.job.bstm.book.rest.request.BookRequest;
import com.getir.job.bstm.book.rest.response.BookResponse;
import com.getir.job.bstm.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BookController {

    private BookService bookService;
    private BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAll(){
        try {
            List<BookResponse> bookResponses = bookService.getAll().stream()
                    .map(book -> bookMapper.entityToResponse(book))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(bookResponses);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<?> getById (@PathVariable Long id) {

        try {
            Book book = bookService.getById(id)
                    .orElseThrow(() -> new BookNotFoundException(id));
            return ResponseEntity.ok(book);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create (@Valid @RequestBody BookRequest bookRequest) {

        try{
            Book book = bookMapper.requestToEntity(bookRequest);
            bookService.save(book);
            return ResponseEntity.ok(bookMapper.entityToResponse(book));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> update (@PathVariable Long id, @Valid @RequestBody BookRequest bookRequest) {

        try{
            Book book = bookService.getById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Book is not found with id: " + id));
            Book updatedBook = bookService.updateStock(book, bookRequest.getStock() - book.getStock());
            bookService.save(updatedBook);
            return ResponseEntity.ok(bookMapper.entityToResponse(book));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
