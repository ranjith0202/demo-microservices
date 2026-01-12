package com.ranjith.order.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrder extends BaseEntity{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
    private Long id;

    private Long productId;

    private String customerName;

    private String customerEmail;
    
    @Column(name = "order_quantity")
    private Integer orderQuantity;

    private LocalDateTime orderDate;

    private String status; // e.g., PENDING, COMPLETED, CANCELLED
}
