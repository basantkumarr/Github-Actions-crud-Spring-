package com.example.crud.entity;

import jakarta.persistence.*;

/*
 * ============================================================
 * ROLE ENTITY
 * ============================================================
 *
 * Represents the "roles" table.
 *
 * Example:
 *
 * id | name
 * ----------------
 * 1  | ROLE_USER
 * 2  | ROLE_ADMIN
 *
 * Role is used for AUTHORIZATION.
 *
 * Authentication:
 *      "Who are you?"
 *
 * Authorization:
 *      "What are you allowed to do?"
 *
 * ============================================================
 */

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Examples:
     *
     * ROLE_USER
     * ROLE_ADMIN
     */
    @Column(nullable = false, unique = true)
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}