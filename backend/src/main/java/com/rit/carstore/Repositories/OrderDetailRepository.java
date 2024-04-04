package com.rit.carstore.Repositories;

import com.rit.carstore.Entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
}
