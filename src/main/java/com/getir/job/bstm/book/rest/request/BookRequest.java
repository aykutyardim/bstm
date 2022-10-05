package com.getir.job.bstm.book.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequest {

    private String name;
    private String author;
    private String publisher;
    private Integer publishedYear;
    private Integer stock;
    private Double price;

}
