package com.example.crud.security;

import com.example.crud.entity.User;
import com.example.crud.repository.UserRepository;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;


/*
 * ============================================================
 * CUSTOM USER DETAILS SERVICE
 * ============================================================
 *
 * This class is the bridge between:
 *
 * Spring Security
 *       |
 *       v
 * UserDetailsService
 *       |
 *       v
 * UserRepository
 *       |
 *       v
 * MySQL
 *
 * ============================================================
 */

@Service
public class CustomUserDetailsService
        implements UserDetailsService {


    private final UserRepository userRepository;


    public CustomUserDetailsService(
            UserRepository userRepository) {

        this.userRepository = userRepository;
    }


    /*
     * ========================================================
     * loadUserByUsername()
     * ========================================================
     *
     * IMPORTANT:
     *
     * Spring Security calls this method when it needs to
     * authenticate a username.
     *
     * ========================================================
     */

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {


        /*
         * Search our database.
         *
         * Conceptually:
         *
         * SELECT *
         * FROM users
         * WHERE username = ?
         */
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + username
                        )
                );


        /*
         * Convert:
         *
         * Our User
         *      ↓
         * CustomUserDetails
         *
         * Spring Security will use the returned object.
         */

        return new CustomUserDetails(user);
    }
}