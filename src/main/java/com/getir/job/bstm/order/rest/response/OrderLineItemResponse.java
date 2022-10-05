package com.getir.job.bstm.order.rest.response;

import com.getir.job.bstm.book.model.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemResponse {

    private Long id;
    private Book book;
    private Integer quantity;
}
