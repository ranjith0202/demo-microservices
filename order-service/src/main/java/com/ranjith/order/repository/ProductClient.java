package com.ranjith.order.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dto.main.ApiResponse;
import com.dto.main.ProductDto;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {
	
	@GetMapping("/api/products/{id}")
    ResponseEntity<ApiResponse<ProductDto>> getProductById(@PathVariable("id") Long productId);
	
	@PutMapping("/api/products/{id}")
	public ResponseEntity<ApiResponse<ProductDto>> updateProduct(@RequestBody ProductDto productDto,@PathVariable("id") Long id);
}
