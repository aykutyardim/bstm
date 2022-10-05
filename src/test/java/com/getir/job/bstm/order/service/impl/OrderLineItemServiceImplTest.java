package com.getir.job.bstm.order.service.impl;

import com.getir.job.bstm.BstmApplication;
import com.getir.job.bstm.book.service.BookService;
import com.getir.job.bstm.order.exception.InvalidQuantityException;
import com.getir.job.bstm.order.model.OrderLineItem;
import com.getir.job.bstm.order.repository.OrderLineItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BstmApplication.class)
class OrderLineItemServiceImplTest {

    private OrderLineItem orderLineItem;
    private OrderLineItemServiceImpl orderLineItemService;

    @Mock
    private OrderLineItemRepository orderLineItemRepository;

    @Mock
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.orderLineItemService = new OrderLineItemServiceImpl(this.orderLineItemRepository, this.bookService);
        this.orderLineItem = new OrderLineItem();
        orderLineItem.setId(1L);
        orderLineItem.setQuantity(100);
    }


    @Test
    void shouldSave() {
        doReturn(this.orderLineItem).when(orderLineItemRepository).save(this.orderLineItem);
        OrderLineItem expected = this.orderLineItem;
        OrderLineItem actual = orderLineItemService.save(orderLineItem);
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    void shouldThrowInvalidQuantityExceptionSave() {
        this.orderLineItem.setQuantity(-1);
        Exception exception = assertThrows(InvalidQuantityException.class, () -> {
            orderLineItemService.save(this.orderLineItem);
        });

        String expected = "Requested book number should be greater than zero.";
        String actual = exception.getMessage();

        assertTrue(actual.contains(expected));
    }
}