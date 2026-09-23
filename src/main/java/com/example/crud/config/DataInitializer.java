package com.example.crud.config;

import com.example.crud.entity.Role;
import com.example.crud.repository.RoleRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/*
 * ============================================================
 * DATA INITIALIZER
 * ============================================================
 *
 * When the application starts, this creates:
 *
 * ROLE_USER
 * ROLE_ADMIN
 *
 * if they don't already exist.
 *
 * ============================================================
 */

@Configuration
public class DataInitializer {


    @Bean
    CommandLineRunner initRoles(
            RoleRepository roleRepository) {


        return args -> {


            /*
             * Create ROLE_USER if it doesn't exist.
             */

            if (roleRepository
                    .findByName("ROLE_USER")
                    .isEmpty()) {

                roleRepository.save(
                        new Role("ROLE_USER")
                );
            }


            /*
             * Create ROLE_ADMIN if it doesn't exist.
             */

            if (roleRepository
                    .findByName("ROLE_ADMIN")
                    .isEmpty()) {

                roleRepository.save(
                        new Role("ROLE_ADMIN")
                );
            }
        };
    }
}