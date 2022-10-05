package com.getir.job.bstm.book.service.impl;

import com.getir.job.bstm.BstmApplication;
import com.getir.job.bstm.book.exception.DataInconsistencyException;
import com.getir.job.bstm.book.exception.OutOfStockException;
import com.getir.job.bstm.book.model.Book;
import com.getir.job.bstm.book.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BstmApplication.class)
class BookServiceImplTest {

    private Book book;
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.bookService = new BookServiceImpl(bookRepository);
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
        Book actual = bookService.getById(1L).orElse(null);

        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    void shouldGetAll() {

        List<Book> books = new ArrayList<>();
        books.add(this.book);

        doReturn(books).when(bookRepository).findAll();

        List<Book> expected = books;
        List<Book> actual = bookService.getAll();

        assertNotNull(actual);
        assertNotNull(actual.get(0));
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
    }

    @Test
    void shouldSave() {
        doReturn(this.book).when(bookRepository).save(this.book);
        Book expected = this.book;
        Book actual = bookService.save(book);
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    void shouldThrowOutOfStockExceptionSave() {
        this.book.setStock(-1);
        Exception exception = assertThrows(OutOfStockException.class, () -> {
            bookService.save(this.book);
        });

        String expected = "Given insufficient stock:";
        String actual = exception.getMessage();

        assertTrue(actual.contains(expected));
    }

    @Test
    void shouldUpdateStock() {

        doReturn(1).when(bookRepository).updateStock(1L, 100, 110);

        Integer expected = 110;
        Integer actual = bookService.updateStock(this.book, 10).getStock();

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowOutOfStockExceptionUpdateStock() {

        Exception exception = assertThrows(OutOfStockException.class, () -> {
            bookService.updateStock(this.book, -110);
        });


        String expected = "Given insufficient stock:";
        String actual = exception.getMessage();

        assertTrue(actual.contains(expected));
    }

    @Test
    void shouldThrowDataInconsistencyUpdateStock() {

        doReturn(0).when(bookRepository).updateStock(1L, 100, 110);

        Exception exception = assertThrows(DataInconsistencyException.class, () -> {
            bookService.updateStock(this.book, 10);
        });

        String expected = "Something went wrong!";
        String actual = exception.getMessage();

        assertTrue(actual.contains(expected));
    }
}