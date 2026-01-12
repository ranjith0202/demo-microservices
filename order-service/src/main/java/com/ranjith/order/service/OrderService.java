package com.ranjith.order.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dto.main.ApiResponse;
import com.dto.main.ProductDto;
import com.dto.main.ProductOrderDto;
import com.dto.main.ProductOrderPatchDto;
import com.ranjith.order.entity.ProductOrder;
import com.ranjith.order.exception.CustomException;
import com.ranjith.order.exception.OutOfStockException;
import com.ranjith.order.exception.ResourceNotFoundException;
import com.ranjith.order.repository.OrderRepository;
import com.ranjith.order.repository.ProductClient;

@Service
public class OrderService {
	private final OrderRepository orderRepository;
	private final ModelMapper mapper;
	private final ProductClient productClient;
	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
	public OrderService(OrderRepository orderRepository, ModelMapper mapper,ProductClient productClient) {
		this.orderRepository = orderRepository;
		this.mapper = mapper;
		this.productClient = productClient;
	}
	
	private ProductOrderDto convertToDto(ProductOrder order) {
		return mapper.map(order, ProductOrderDto.class);
	}
	
	private ProductOrder convertToEntity(ProductOrderDto orderDto) {
		return mapper.map(orderDto, ProductOrder.class);
	}
	
	public ProductOrderDto createOrder(ProductOrderDto orderDto) {
		ProductOrder order = convertToEntity(orderDto);
		order = orderRepository.save(order);
		return convertToDto(order);
	}
	
	public ProductOrderDto getOrderById(Long id) {
		return orderRepository.findById(id)
				.map(this::convertToDto)
				.orElseThrow( () -> new ResourceNotFoundException("Order not found")) ;
	}
	
	public ProductOrderDto updateOrder(Long id,ProductOrderDto orderDto) {
		ProductOrder existingOrder =  orderRepository.findById(id)
		.orElseThrow( () -> new ResourceNotFoundException("Order not found")) ;
		
		mapper.map(existingOrder, orderDto);
		
		ProductOrder updatedOrder = orderRepository.save(existingOrder);
		return convertToDto(updatedOrder);
	}
	
	public List<ProductOrderDto>  getAllorderList(){
		return orderRepository.findAll().stream()
				.map(this::convertToDto).toList();
	}
	
	public void deleteOrder(Long id) {
		if(!orderRepository.existsById(id)) {
			throw new ResourceNotFoundException("Order not found");
		}
		orderRepository.deleteById(id);
	}
	
	public ProductOrderDto PatchOrder(Long id,ProductOrderPatchDto orderDto) {
		ProductOrder existingOrder =  orderRepository.findById(id)
		.orElseThrow( () -> new ResourceNotFoundException("Order not found")) ;
		
		mapper.map(existingOrder, orderDto);
		
		ProductOrder updatedOrder = orderRepository.save(existingOrder);
		return convertToDto(updatedOrder);
	}

	public ProductOrderDto placeOrder(ProductOrderDto orderDto) {
		 ResponseEntity<ApiResponse<ProductDto>> response = 
		            productClient.getProductById(orderDto.getProductId());
		 logger.info("Response from product-service -> {}", response);
		    if (!response.getStatusCode().is2xxSuccessful()
		            || response.getBody() == null
		            || response.getBody().getData() == null) {
		        throw new ResourceNotFoundException(
		                "Product not found: " + orderDto.getProductId());
		    }
		    ProductDto product = response.getBody().getData();
		    if (product.getAvailableQuantity() < orderDto.getOrderQuantity()) {
		        throw new OutOfStockException("Insufficient stock for product: " + product.getId());
		    }
		    // Create & save order
		    orderDto = createOrder(orderDto);
		    try {
		        // Reduce stock in product-service
		        product.setAvailableQuantity(product.getAvailableQuantity() - orderDto.getOrderQuantity());
		        ResponseEntity<ApiResponse<ProductDto>> responseProductUpdate =
		                productClient.updateProduct(product, orderDto.getProductId());
		        if (!responseProductUpdate.getStatusCode().is2xxSuccessful()
		                || responseProductUpdate.getBody() == null
		                || responseProductUpdate.getBody().getData() == null) {
		            throw new CustomException("Product quantity update failed for product: " + product.getId());
		        }
		    } catch (Exception e) {
		        // Rollback: delete the order since product update failed
		        logger.error("Rolling back order due to product update failure", e);
		        deleteOrder(orderDto.getId()); // You need to implement this method
		        throw new CustomException("Order rolled back due to product update failure: " + e.getMessage());
		    }
		    return orderDto;
	}
	
}
