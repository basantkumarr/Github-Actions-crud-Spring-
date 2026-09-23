package com.example.crud.repository;

import com.example.crud.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * ============================================================
 * USER REPOSITORY
 * ============================================================
 *
 * Spring Security needs to find the user from our database.
 *
 * UserDetailsService will use this repository.
 *
 * ============================================================
 */

public interface UserRepository
        extends JpaRepository<User, Long> {

    /*
     * Spring Security will eventually call:
     *
     * findByUsername("mohit")
     *
     * Hibernate generates the required SQL.
     */
    Optional<User> findByUsername(String username);

    /*
     * Used during registration.
     */
    boolean existsByUsername(String username);

    /*
     * Used during registration.
     */
    boolean existsByEmail(String email);
}