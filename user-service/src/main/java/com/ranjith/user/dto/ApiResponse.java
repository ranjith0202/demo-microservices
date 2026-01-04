package com.ranjith.user.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ApiResponse<T> {
	 private int status;             // HTTP status code
	    private String message;         // Success or error message
	    private LocalDateTime timestamp; // Response timestamp
	    private T data;                 // Payload (optional)
	    
		public ApiResponse(int status, String message, T data) {
			this.status = status;
			this.message = message;
			this.timestamp = LocalDateTime.now();
			this.data = data;
		}
	    
	    
}
