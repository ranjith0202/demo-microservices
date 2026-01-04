package com.ranjith.product.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ranjith.product.dto.ApiResponse;
import com.ranjith.product.dto.PatchUpdateDto;
import com.ranjith.product.dto.ProductDto;
import com.ranjith.product.service.ProductService;
import com.ranjith.product.util.ResponseHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<ProductDto>>> getAllUsers() {
	    List<ProductDto> allProductsList = productService.findAllProducts();
	    return ResponseHandler.success("Products fetched successfully",allProductsList, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<ProductDto>> createProduct(@Valid @RequestBody ProductDto productDto){
		productDto = productService.createProduct(productDto);
		 return ResponseHandler.success("Product "+productDto.getId()+" created Succesfully", productDto, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductDto>> geProductById(@PathVariable Long id){
		ProductDto productDto = productService.getProductById(id);
		 return ResponseHandler.success("Product "+id+" fetched Succesfully", productDto, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductDto>> updateProduct(@Valid @RequestBody ProductDto productDto,@PathVariable Long id){
		productDto = productService.updateProduct(id, productDto);
		 return ResponseHandler.success("Product "+id+" updated Succesfully", productDto, HttpStatus.OK);
	}
	
	@PatchMapping("{id}")
	public ResponseEntity<ApiResponse<ProductDto>> patchProduct(@Valid @RequestBody PatchUpdateDto patchUpdateDto,@PathVariable Long id){
		ProductDto productDto  = productService.patchProduct(id, patchUpdateDto);
		 return ResponseHandler.success("Product "+id+" updated Succesfully", productDto, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductDto>> deleteProducts(@PathVariable Long id){
		productService.deleteProduct(id);
		 return ResponseHandler.success("Product "+id+" deleted Succesfully",null, HttpStatus.OK);
	}

	
}
