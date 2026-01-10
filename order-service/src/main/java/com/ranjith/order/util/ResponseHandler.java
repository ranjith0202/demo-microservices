package com.ranjith.order.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dto.main.ApiResponse;

public class ResponseHandler {
	
	 public static <T> ResponseEntity<ApiResponse<T>> success(
	            String message, T data, HttpStatus status) {

	        ApiResponse<T> response = new ApiResponse<>(
	                status.value(),
	                message,
	                data
	        );
	        return new ResponseEntity<>(response, status);
	    }

	    /**
	     * Generate an error response
	     * @param message - error message
	     * @param status - HTTP status
	     */
	    public static <T> ResponseEntity<ApiResponse<T>> error(
	            String message, HttpStatus status) {

	        ApiResponse<T> response = new ApiResponse<>(
	                status.value(),
	                message,
	                null
	        );
	        return new ResponseEntity<>(response, status);
	    }
}
