package com.getir.job.bstm.book.repository;

import com.getir.job.bstm.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Book SET stock = ?3 WHERE id = ?1 AND stock = ?2")
    Integer updateStock(Long id, Integer oldStock, Integer newStock);
}