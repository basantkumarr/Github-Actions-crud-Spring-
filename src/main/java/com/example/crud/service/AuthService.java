package com.example.crud.service;

import com.example.crud.dto.RegisterRequest;
import com.example.crud.entity.Role;
import com.example.crud.entity.User;
import com.example.crud.repository.RoleRepository;
import com.example.crud.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/*
 * ============================================================
 * AUTH SERVICE
 * ============================================================
 *
 * Responsible for user registration.
 *
 * Authentication happens later through Spring Security.
 *
 * ============================================================
 */

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;


    public AuthService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    /*
     * ========================================================
     * USER REGISTRATION
     * ========================================================
     */

    public User register(RegisterRequest request) {


        // -----------------------------------------------
        // 1. Check duplicate username
        // -----------------------------------------------

        if (userRepository.existsByUsername(
                request.getUsername())) {

            throw new RuntimeException(
                    "Username already exists"
            );
        }


        // -----------------------------------------------
        // 2. Check duplicate email
        // -----------------------------------------------

        if (userRepository.existsByEmail(
                request.getEmail())) {

            throw new RuntimeException(
                    "Email already exists"
            );
        }


        // -----------------------------------------------
        // 3. Find default ROLE_USER
        // -----------------------------------------------

        Role userRole = roleRepository
                .findByName("ROLE_USER")
                .orElseThrow(() ->
                        new RuntimeException(
                                "ROLE_USER not found"
                        )
                );


        // -----------------------------------------------
        // 4. Create our domain User
        // -----------------------------------------------

        User user = new User();

        user.setUsername(request.getUsername());

        user.setEmail(request.getEmail());


        /*
         * =================================================
         * 5. HASH PASSWORD
         * =================================================
         *
         * NEVER:
         *
         * user.setPassword(request.getPassword());
         *
         * Correct:
         *
         * raw password
         *      ↓
         * PasswordEncoder
         *      ↓
         * BCrypt
         *      ↓
         * encoded password
         *      ↓
         * database
         */

        String encodedPassword =
                passwordEncoder.encode(
                        request.getPassword()
                );

        user.setPassword(encodedPassword);


        // -----------------------------------------------
        // 6. Assign ROLE_USER
        // -----------------------------------------------

        user.getRoles().add(userRole);


        // -----------------------------------------------
        // 7. Save user
        // -----------------------------------------------

        return userRepository.save(user);
    }
}