package com.ranjith.product.entity;

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
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor	
@Getter
@Setter
public class Product extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name = "product_id")
	private Long id;
	
	 	@Column(nullable = false)
	    private String name;

	    private String description;

	    @Column(nullable = false)
	    private Double price;

	    @Column(nullable = false)
	    private Integer quantity;

	    private String category;

	    @Column( nullable = false)
	    private String unit; // Stock Keeping Unit

	    private Boolean status = true;
}
