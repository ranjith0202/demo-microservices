package com.ranjith.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ranjith.order.entity.ProductOrder;

@Repository
public interface OrderRepository extends JpaRepository<ProductOrder, Long> {
	
}
