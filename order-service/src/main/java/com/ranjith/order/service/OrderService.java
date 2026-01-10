package com.ranjith.order.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dto.main.ProductOrderDto;
import com.dto.main.ProductOrderPatchDto;
import com.ranjith.order.entity.ProductOrder;
import com.ranjith.order.exception.ResourceNotFoundException;
import com.ranjith.order.repository.OrderRepository;

@Service
public class OrderService {
	private final OrderRepository orderRepository;
	private final ModelMapper mapper;
	public OrderService(OrderRepository orderRepository, ModelMapper mapper) {
		this.orderRepository = orderRepository;
		this.mapper = mapper;
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
	
}
