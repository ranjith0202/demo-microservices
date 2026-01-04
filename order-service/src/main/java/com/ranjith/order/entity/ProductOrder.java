package com.ranjith.order.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "product_orders")
public class ProductOrder extends BaseEntity{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
    private Long id;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotBlank(message = "Customer name cannot be blank")
    private String customerName;

    @NotBlank(message = "Customer email is required")
    @Email(message = "Invalid email format")
    private String customerEmail;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than zero")
    private Integer quantity;

    @NotNull(message = "Order date is required")
    private LocalDateTime orderDate;

    @NotBlank(message = "Order status is required")
    private String status; // e.g., PENDING, COMPLETED, CANCELLED
}
