package com.ranjith.product.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BaseDto {
	private String createdBy;
	private LocalDateTime createdTime;
	private String modifiedBy;
	private LocalDateTime modifiedTime;
}
