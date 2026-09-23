package com.example.crud.controller;

import com.example.crud.dto.RegisterRequest;
import com.example.crud.entity.User;
import com.example.crud.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/*
 * ============================================================
 * AUTH CONTROLLER
 * ============================================================
 *
 * Handles authentication-related HTTP requests.
 *
 * Currently:
 *
 * POST /api/auth/register
 *
 * ============================================================
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    /*
     * ========================================================
     * USER REGISTRATION
     * ========================================================
     *
     * POST:
     *
     * /api/auth/register
     *
     * Body:
     *
     * {
     *     "username": "mohit",
     *     "email": "mohit@gmail.com",
     *     "password": "hello123"
     * }
     *
     * ========================================================
     */

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request) {


        /*
         * AuthService performs:
         *
         * validation
         * duplicate checking
         * role assignment
         * password encoding
         * database save
         */

        User user = authService.register(request);


        return ResponseEntity.ok(
                "User registered successfully: "
                        + user.getUsername()
        );
    }
}