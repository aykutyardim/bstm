package com.getir.job.bstm.order.service.impl;

import com.getir.job.bstm.BstmApplication;
import com.getir.job.bstm.book.model.Book;
import com.getir.job.bstm.order.model.Order;
import com.getir.job.bstm.order.model.OrderLineItem;
import com.getir.job.bstm.order.repository.OrderRepository;
import com.getir.job.bstm.order.service.OrderLineItemService;
import com.getir.job.bstm.stats.model.Stats;
import com.getir.job.bstm.stats.service.StatsService;
import com.getir.job.bstm.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BstmApplication.class)
class OrderServiceImplTest {

    private Order order;

    private OrderLineItem orderLineItem;

    private User user;

    private Book book;

    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private StatsService statsService;

    @Mock
    private OrderLineItemService orderLineItemService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.orderService = new OrderServiceImpl(orderRepository, orderLineItemService, statsService);
        this.order = new Order();
        order.setId(1L);
        this.orderLineItem = new OrderLineItem();
        orderLineItem.setId(1L);
        this.user = new User();
        user.setId(1L);
        this.book = new Book();
        book.setId(1L);
        book.setPrice(10.00);
    }

    @Test
    void shouldGetAll() {

        List<Order> orders = new ArrayList<>();
        orders.add(this.order);
        doReturn(orders).when(orderRepository).findAll(Sort.by("id"));

        List<Order> expected = orders;
        List<Order> actual = this.orderService.getAll();

        assertNotNull(actual);
        assertNotNull(actual.get(0));
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());

    }

    @Test
    void shouldGetById() {

        doReturn(Optional.of(this.order)).when(orderRepository).findById(1L);

        Order expected = this.order;
        Order actual = orderService.getById(1L).orElse(null);

        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    void getAllByCreatedAtBetween() {

        List<Order> orders = new ArrayList<>();
        orders.add(this.order);
        Date start = new Date();
        Date end = new Date();
        doReturn(orders).when(orderRepository).findByCreatedAtBetween(start, end);

        List<Order> expected = orders;
        List<Order> actual = this.orderService.getAllByCreatedAtBetween(start, end);

        assertNotNull(actual);
        assertNotNull(actual.get(0));
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());

    }

    @Test
    void shouldSave() {

        this.orderLineItem.setBook(this.book);
        this.orderLineItem.setQuantity(1);
        List<OrderLineItem> orderLineItems = new ArrayList<>();
        orderLineItems.add(orderLineItem);
        this.order.setOrderLineItems(orderLineItems);
        this.order.setUser(this.user);

        doReturn(this.order).when(orderRepository).save(order);
        doReturn(this.orderLineItem).when(orderLineItemService).save(any(OrderLineItem.class));
        doReturn(new Stats()).when(statsService).increaseByUserCountAndAmount(this.user, 1, 10.00);

        Order expected = this.order;
        Order actual = orderService.save(this.order);

        assertEquals(expected.getId(), actual.getId());

    }

    @Test
    void getAllByUserIdAndPageNumberAndPageSize() {

        List<Order> orders = new ArrayList<>();
        orders.add(this.order);
        Pageable pageable = PageRequest.of(0, 10);
        doReturn(orders).when(orderRepository).findAllByUser_Id(this.user.getId(), pageable);

        List<Order> expected = orders;
        List<Order> actual = this.orderService.getAllByUserIdAndPageNumberAndPageSize(this.user.getId(), 0, 10);

        assertNotNull(actual);
        assertNotNull(actual.get(0));
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
    }
}