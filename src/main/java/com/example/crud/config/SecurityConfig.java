package com.example.crud.config;

import com.example.crud.security.CustomUserDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;


/*
 * ============================================================
 * SPRING SECURITY CONFIGURATION
 * ============================================================
 *
 * This class configures Spring Security.
 *
 * Main concepts:
 *
 * SecurityFilterChain
 * HttpSecurity
 * AuthenticationProvider
 * DaoAuthenticationProvider
 * UserDetailsService
 * PasswordEncoder
 * AuthenticationManager
 *
 * ============================================================
 */

@Configuration
public class SecurityConfig {


    private final CustomUserDetailsService userDetailsService;


    public SecurityConfig(
            CustomUserDetailsService userDetailsService) {

        this.userDetailsService = userDetailsService;
    }


    /*
     * ========================================================
     * PASSWORD ENCODER
     * ========================================================
     *
     * BCrypt is used to hash passwords.
     *
     * Registration:
     *
     * raw password
     *      |
     *      v
     * encode()
     *      |
     *      v
     * BCrypt hash
     *
     * Login:
     *
     * raw password
     *      |
     *      v
     * matches(raw, storedHash)
     *
     * ========================================================
     */

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    /*
     * ========================================================
     * DAO AUTHENTICATION PROVIDER
     * ========================================================
     *
     * DaoAuthenticationProvider is an AuthenticationProvider.
     *
     * It uses:
     *
     * UserDetailsService
     * +
     * PasswordEncoder
     *
     * to authenticate a username/password.
     *
     * ========================================================
     */

    @Bean
    public AuthenticationProvider authenticationProvider(
            PasswordEncoder passwordEncoder) {


        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(
                        userDetailsService
                );


        /*
         * Tell DaoAuthenticationProvider which password
         * encoder should be used.
         */
        provider.setPasswordEncoder(passwordEncoder);


        return provider;
    }


    /*
     * ========================================================
     * AUTHENTICATION MANAGER
     * ========================================================
     *
     * AuthenticationManager is the main authentication
     * entry point.
     *
     * In Spring Security's default implementation:
     *
     * AuthenticationManager
     *          |
     *          v
     * ProviderManager
     *          |
     *          v
     * AuthenticationProvider
     *          |
     *          v
     * DaoAuthenticationProvider
     *
     * ========================================================
     */

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }


    /*
     * ========================================================
     * SECURITY FILTER CHAIN
     * ========================================================
     *
     * HTTP requests enter Spring Security's filter chain
     * before reaching our Controller.
     *
     * Conceptually:
     *
     * HTTP Request
     *      |
     *      v
     * Security Filters
     *      |
     *      v
     * Authentication
     *      |
     *      v
     * Authorization
     *      |
     *      v
     * Controller
     *
     * ========================================================
     */

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {


        http


                /*
                 * ------------------------------------------------
                 * CSRF
                 * ------------------------------------------------
                 *
                 * Disabled for this learning REST API.
                 *
                 * For browser/session applications, CSRF should
                 * be handled according to the application's
                 * authentication architecture.
                 */
                .csrf(csrf -> csrf.disable())


                /*
                 * =================================================
                 * AUTHORIZATION RULES
                 * =================================================
                 */

                .authorizeHttpRequests(auth -> auth


                        /*
                         * FIRST MATCHING RULE WINS.
                         *
                         * Therefore place specific rules before
                         * broader rules.
                         *
                         * Registration must be public.
                         */

                        .requestMatchers(
                                "/api/auth/register"
                        ).permitAll()


                        /*
                         * Only ADMIN can access this URL.
                         */
                        .requestMatchers(
                                "/api/admin/**"
                        ).hasRole("ADMIN")


                        /*
                         * USER or ADMIN can access this URL.
                         */
                        .requestMatchers(
                                "/api/user/**"
                        ).hasAnyRole("USER", "ADMIN")


                        /*
                         * Your existing CRUD APIs:
                         *
                         * /api/students/**
                         *
                         * will require authentication because
                         * they fall into this rule.
                         */
                        .anyRequest().authenticated()
                )


                /*
                 * =================================================
                 * HTTP BASIC
                 * =================================================
                 *
                 * Postman can send:
                 *
                 * Authorization:
                 * Basic username/password
                 *
                 * Spring Security uses:
                 *
                 * BasicAuthenticationFilter
                 *
                 * to process the credentials.
                 */
                .httpBasic(basic -> {})


                /*
                 * =================================================
                 * FORM LOGIN
                 * =================================================
                 *
                 * Form login uses:
                 *
                 * UsernamePasswordAuthenticationFilter
                 *
                 * Default login URL:
                 *
                 * POST /login
                 *
                 * with:
                 *
                 * username
                 * password
                 *
                 * =================================================
                 */

                .formLogin(form -> form

                        /*
                         * Spring provides a default login page.
                         *
                         * We are not creating an HTML login page
                         * yet.
                         */
                        .permitAll()
                );


        return http.build();
    }
}