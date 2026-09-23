package com.example.crud.repository;

import com.example.crud.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * Repository for roles table.
 */
public interface RoleRepository
        extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}