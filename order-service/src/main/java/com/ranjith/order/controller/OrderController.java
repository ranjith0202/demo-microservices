package com.ranjith.order.controller;

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

import com.ranjith.order.dto.ApiResponse;
import com.ranjith.order.dto.ProductOrderDto;
import com.ranjith.order.dto.ProductOrderPatchDto;
import com.ranjith.order.service.OrderService;
import com.ranjith.order.util.ResponseHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse< List<ProductOrderDto>>> getAllOrders(){
		List<ProductOrderDto> orderList = orderService.getAllorderList();
		return ResponseHandler.success("Order Data Fetched Succesfully", orderList, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductOrderDto>> getOrderById(@PathVariable Long id){
		ProductOrderDto orderDto = orderService.getOrderById(id);
		return ResponseHandler.success("Order Data Fetched Succesfully", orderDto, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<ProductOrderDto>> createOrder(@Valid @RequestBody ProductOrderDto orderDto){
		orderDto = orderService.createOrder(orderDto);
		return ResponseHandler.success("Order Created Succesfully", orderDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductOrderDto>> updateOrder
		(@Valid @RequestBody ProductOrderDto orderDto,@PathVariable Long id){
		orderDto = orderService.updateOrder(id, orderDto);
		return ResponseHandler.success("Order Updated Succesfully", orderDto, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Long id){
		orderService.deleteOrder(id);
		return ResponseHandler.success("Order deleted Succesfully", null, HttpStatus.OK);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductOrderDto>> updateOrder
		(@Valid @RequestBody ProductOrderPatchDto orderDto,@PathVariable Long id){
		ProductOrderDto productOrderDto = orderService.PatchOrder(id, orderDto);
		return ResponseHandler.success("Order Updated Succesfully", productOrderDto, HttpStatus.OK);
	}
}
