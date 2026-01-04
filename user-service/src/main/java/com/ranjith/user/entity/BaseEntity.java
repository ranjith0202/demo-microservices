package com.ranjith.user.entity;

import java.time.LocalDateTime;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity {
	private String createdBy;
	private String modifiedBy;
	private LocalDateTime createdTime;
	private LocalDateTime modifiedTime;
	
	@PrePersist
	public void onCreate() {
		this.createdTime = LocalDateTime.now();
		this.modifiedTime = LocalDateTime.now();
	}
	
	@PostConstruct
	public void onUpdate() {
		this.modifiedTime = LocalDateTime.now();
	}
	
}
