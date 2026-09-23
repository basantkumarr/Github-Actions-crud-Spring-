package com.example.crud.controller;

import org.springframework.web.bind.annotation.*;


/*
 * ============================================================
 * ADMIN CONTROLLER
 * ============================================================
 *
 * This demonstrates AUTHORIZATION.
 *
 * Authentication:
 *      Are you logged in?
 *
 * Authorization:
 *      Do you have the required role?
 *
 * ============================================================
 */

@RestController
@RequestMapping("/api/admin")
public class AdminController {


    /*
     * SecurityConfig says:
     *
     * /api/admin/**
     *
     * requires:
     *
     * ROLE_ADMIN
     */

    @GetMapping("/hello")
    public String helloAdmin() {

        return "Hello ADMIN! You are authorized.";
    }
}