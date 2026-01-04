package com.ranjith.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ranjith.user.entity.Roles;

public interface RolesInterface extends JpaRepository<Roles, Long> {

}
