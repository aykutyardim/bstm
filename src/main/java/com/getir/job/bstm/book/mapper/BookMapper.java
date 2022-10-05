package com.getir.job.bstm.book.mapper;

import com.getir.job.bstm.book.model.Book;
import com.getir.job.bstm.book.rest.request.BookRequest;
import com.getir.job.bstm.book.rest.response.BookResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class BookMapper {

    public abstract Book requestToEntity (BookRequest bookRequest);
    public abstract BookResponse entityToResponse (Book book);
}
