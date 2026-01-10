package com.ranjith.product.entity;

import java.time.LocalDateTime;

import com.ranjith.product.util.SecurityUtil;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
	private String createdBy;
	private LocalDateTime createdTime;
	private String modifiedBy;
	private LocalDateTime modifiedTime;
	
	@PrePersist
	public  void onCreate() {
		this.createdTime = LocalDateTime.now();
		this.modifiedTime = LocalDateTime.now();
		this.createdBy = SecurityUtil.getCurrentUsername();
	}
	
	@PostUpdate
	public void onUpdate() {
		this.modifiedTime = LocalDateTime.now();
		this.modifiedBy = SecurityUtil.getCurrentUsername();
	}
	
}
