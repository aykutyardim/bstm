package com.getir.job.bstm.order.mapper;

import com.getir.job.bstm.book.exception.BookNotFoundException;
import com.getir.job.bstm.book.model.Book;
import com.getir.job.bstm.book.service.BookService;
import com.getir.job.bstm.order.exception.MissingParameterException;
import com.getir.job.bstm.order.model.OrderLineItem;
import com.getir.job.bstm.order.rest.request.OrderLineItemRequest;
import com.getir.job.bstm.order.rest.response.OrderLineItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderLineItemMapper {

    private BookService bookService;

    public OrderLineItemMapper(BookService bookService) {
        this.bookService = bookService;
    }

    public OrderLineItem requestToEntity (OrderLineItemRequest orderLineItemRequest) {

        if (orderLineItemRequest == null)  {
            throw new MissingParameterException("orderLineItemRequest");
        }

        if (orderLineItemRequest.getBookId() == null) {
            throw new MissingParameterException("bookId");
        }

        if (orderLineItemRequest.getQuantity() == null) {
            throw new MissingParameterException("quantity");
        }

        OrderLineItem orderLineItem = new OrderLineItem();

        Book book = bookService.getById(orderLineItemRequest.getBookId())
                .orElseThrow(() -> new BookNotFoundException(orderLineItemRequest.getBookId()));
        orderLineItem.setBook(book);
        orderLineItem.setQuantity(orderLineItemRequest.getQuantity());

        return orderLineItem;
    }

    public OrderLineItemResponse entityToResponse (OrderLineItem orderLineItem) {
        OrderLineItemResponse orderLineItemResponse = new OrderLineItemResponse();
        orderLineItemResponse.setId(orderLineItem.getId());
        orderLineItemResponse.setQuantity(orderLineItem.getQuantity());
        Book book = bookService.getById(orderLineItem.getBook().getId())
                .orElseThrow(() -> new BookNotFoundException(orderLineItem.getBook().getId()));
        orderLineItemResponse.setBook(book);
        return orderLineItemResponse;
    }

}
