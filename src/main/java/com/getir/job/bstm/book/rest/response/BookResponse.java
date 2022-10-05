package com.getir.job.bstm.book.rest.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookResponse {

    private String id;
    private String name;
    private String author;
    private String publisher;
    private Integer publishedYear;
    private Integer stock;
    private Double price;
}
