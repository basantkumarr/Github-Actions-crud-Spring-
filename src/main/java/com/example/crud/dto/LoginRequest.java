package com.example.crud.dto;

/*
 * ============================================================
 * LOGIN REQUEST
 * ============================================================
 *
 * This represents credentials supplied during login.
 *
 * Example:
 *
 * {
 *     "username": "mohit",
 *     "password": "hello123"
 * }
 *
 * ============================================================
 */

public class LoginRequest {

    private String username;

    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}