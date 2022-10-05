package com.getir.job.bstm.order.service.impl;

import com.getir.job.bstm.book.model.Book;
import com.getir.job.bstm.book.service.BookService;
import com.getir.job.bstm.order.exception.InvalidQuantityException;
import com.getir.job.bstm.order.model.OrderLineItem;
import com.getir.job.bstm.order.repository.OrderLineItemRepository;
import com.getir.job.bstm.order.service.OrderLineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderLineItemServiceImpl implements OrderLineItemService {

    private OrderLineItemRepository orderLineItemRepository;
    private BookService bookService;

    public OrderLineItemServiceImpl(OrderLineItemRepository orderLineItemRepository, BookService bookService) {
        this.orderLineItemRepository = orderLineItemRepository;
        this.bookService = bookService;
    }


    @Override
    public OrderLineItem save (OrderLineItem orderLineItem){
        if (orderLineItem.getQuantity() < 0) {
            throw new InvalidQuantityException();
        }
        Book book = bookService.updateStock(orderLineItem.getBook(), orderLineItem.getQuantity() * -1);
        OrderLineItem createdOrderLineItem = orderLineItemRepository.save(orderLineItem);
        createdOrderLineItem.setBook(book);
        return createdOrderLineItem;
    }
}
