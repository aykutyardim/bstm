package com.getir.job.bstm.user.service.impl;

import com.getir.job.bstm.BstmApplication;
import com.getir.job.bstm.book.model.Book;
import com.getir.job.bstm.order.model.Order;
import com.getir.job.bstm.order.service.OrderService;
import com.getir.job.bstm.user.model.User;
import com.getir.job.bstm.user.repository.UserRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BstmApplication.class)
class UserServiceImplTest {

    private User user;
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;


    @Mock
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.userService = new UserServiceImpl(userRepository, orderService);
        this.user = new User();
        this.user.setId(1L);
    }

    @Test
    void shouldSave() {

        doReturn(this.user).when(userRepository).save(this.user);

        User expected = this.user;
        User actual = userService.save(this.user);

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetByUsername() {
        doReturn(Optional.of(this.user)).when(userRepository).findByUsername("test");

        User expected = this.user;
        User actual = userService.getByUsername("test").orElse(null);

        assertNotNull(actual);
        assertEquals(expected, actual);

    }

    @Test
    void shouldGetById() {

        doReturn(Optional.of(this.user)).when(userRepository).findById(1L);

        User expected = this.user;
        User actual = userService.getById(1L).orElse(null);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldExistsByUsername() {

        doReturn(true).when(userRepository).existsByUsername("test");

        Boolean expected = true;
        Boolean actual = userService.existsByUsername("test");

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldExistsByEmail() {

        doReturn(true).when(userRepository).existsByEmail("test");

        Boolean expected = true;
        Boolean actual = userService.existsByEmail("test");

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldGetOrders() {
        Order order = new Order();
        order.setId(1L);
        List<Order> orders = new ArrayList<>();
        orders.add(order);

        doReturn(orders).when(orderService).getAllByUserIdAndPageNumberAndPageSize(this.user.getId(), 0, 10);

        List<Order> expected = orders;
        List<Order> actual = userService.getOrders(this.user.getId(), 0, 10);

        assertEquals(expected, actual);
    }
}