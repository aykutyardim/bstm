package com.getir.job.bstm.order.repository;

import com.getir.job.bstm.order.model.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    List<Order> findAll(Sort sort);
    List<Order> findByCreatedAtBetween(Date startDate, Date endDate);

    List<Order> findAllByUser_Id(Long userId, Pageable pageable);

}
