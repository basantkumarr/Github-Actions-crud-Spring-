package com.example.crud.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;


/*
 * ============================================================
 * USER CONTROLLER
 * ============================================================
 *
 * Used to test authenticated requests.
 *
 * ============================================================
 */

@RestController
@RequestMapping("/api/user")
public class UserController {


    @GetMapping("/hello")
    public String hello() {

        return "Hello authenticated user!";
    }


    /*
     * ========================================================
     * SECURITY CONTEXT DEMO
     * ========================================================
     *
     * After successful authentication, Spring Security stores
     * the Authentication object inside SecurityContext.
     *
     * SecurityContextHolder gives access to that context.
     *
     * ========================================================
     */

    @GetMapping("/me")
    public String currentUser() {


        /*
         * Get SecurityContext for the current request/thread.
         */

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        /*
         * Authentication contains information about the
         * currently authenticated principal.
         */

        return "Logged in as: "
                + authentication.getName()
                + "\nAuthorities: "
                + authentication.getAuthorities();
    }
}