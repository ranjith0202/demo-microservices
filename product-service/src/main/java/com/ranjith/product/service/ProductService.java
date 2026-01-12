package com.ranjith.product.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dto.main.PatchUpdateDto;
import com.dto.main.ProductDto;
import com.ranjith.product.entity.Product;
import com.ranjith.product.exception.ResourceNotFoundException;
import com.ranjith.product.repository.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository productRepository;
	private final ModelMapper mapper;
	
	public ProductService(ProductRepository productRepository, ModelMapper mapper) {
		this.productRepository = productRepository;
		this.mapper = mapper;
	}
	
	private ProductDto convertToDto(Product product) {
		return mapper.map(product, ProductDto.class);
	}
	
	private Product convertToEntity(ProductDto productDto) {
		return mapper.map(productDto, Product.class);
	}
	
	public ProductDto createProduct(ProductDto productDto) {
		Product product = convertToEntity(productDto);
		return convertToDto(productRepository.save(product));
	}
	
	public ProductDto getProductById(Long id) {
		
		return productRepository.findById(id)
		        .map(this::convertToDto)
		        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
	}
	
	public List<ProductDto> findAllProducts(){
		List<Product> productList = productRepository.findAll();
		
		return productList.stream().map(this::convertToDto).toList();
	}
	
	public ProductDto updateProduct(Long id,ProductDto productDto) {
		Product existingproduct = productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		mapper.map(productDto, existingproduct);
		Product updated = productRepository.save(existingproduct);
		return convertToDto(updated);
	}
	
	public void deleteProduct(Long id) {
		if (!productRepository.existsById(id)) {
		    throw new ResourceNotFoundException("Product not found");
		}
		productRepository.deleteById(id);
	}
	
	public ProductDto patchProduct(Long id,PatchUpdateDto dto) {
		Product existingproduct = productRepository.findById(id)
		        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
				mapper.map(dto, existingproduct);
				Product updated = productRepository.save(existingproduct);
				return convertToDto(updated);
	}
	
}
