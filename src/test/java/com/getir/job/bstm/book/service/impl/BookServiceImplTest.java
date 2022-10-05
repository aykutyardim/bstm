package com.getir.job.bstm.book.service.impl;

import com.getir.job.bstm.BstmApplication;
import com.getir.job.bstm.book.model.Book;
import com.getir.job.bstm.book.repository.BookRepository;
import com.getir.job.bstm.book.service.BookService;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ActiveProfiles("bookServiceImplTest")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BstmApplication.class)
class BookServiceImplTest {

    private Book book;
    private BookServiceImpl bookServiceImpl;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.bookServiceImpl = new BookServiceImpl(bookRepository);
        this.book = new Book();
        this.book.setId(1L);
        this.book.setName("Test Name");
        this.book.setAuthor("Test Author");
        this.book.setPublisher("Test Publisher");
        this.book.setStock(100);
        this.book.setPrice(29.99);
        this.book.setPublishedYear(2022);
    }

    @Test
    void shouldGetById() {

        doReturn(Optional.ofNullable(this.book)).when(bookRepository).findById(1L);

        Book expected = this.book;
        Book actual = bookServiceImpl.getById(1L).orElse(null);

        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    void shouldGetAll() {

        List<Book> books = new ArrayList<>();
        books.add(this.book);

        doReturn(books).when(bookRepository).findAll();

        List<Book> expected = books;
        List<Book> actual = bookServiceImpl.getAll();

        assertNotNull(actual);
        assertNotNull(actual.get(0));
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
    }

    @Test
    void shouldSave() {
        Book expected = this.book;
        Book actual = bookServiceImpl.save(book);
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    void shouldUpdateStock() {
    }
}