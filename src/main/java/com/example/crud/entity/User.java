package com.example.crud.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/*
 * ============================================================
 * DOMAIN USER ENTITY
 * ============================================================
 *
 * IMPORTANT:
 *
 * This is OUR application's User.
 *
 * It represents the user stored in the database.
 *
 * It is different from:
 *
 * org.springframework.security.core.userdetails.User
 *
 * Our User:
 *      -> Database/domain object
 *
 * Spring Security UserDetails:
 *      -> Security representation of the user
 *
 * ============================================================
 */

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Username is the login identity in this example.
     *
     * Example:
     *
     * mohit
     */
    @Column(nullable = false, unique = true)
    private String username;

    /*
     * Email is also unique.
     *
     * We are not using email for login right now.
     * Username is the login identity.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /*
     * VERY IMPORTANT:
     *
     * This field does NOT contain the raw password.
     *
     * It contains the BCrypt encoded password.
     *
     * Example:
     *
     * WRONG:
     * hello123
     *
     * CORRECT:
     * $2a$10$............
     */
    @Column(nullable = false)
    private String password;

    /*
     * MANY-TO-MANY relationship:
     *
     * One user can have multiple roles.
     *
     * Example:
     *
     * Mohit
     *    |
     *    +---- ROLE_USER
     *    |
     *    +---- ROLE_ADMIN
     *
     * The relationship is stored in:
     *
     * user_roles
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",

            // Foreign key pointing to users table
            joinColumns = @JoinColumn(name = "user_id"),

            // Foreign key pointing to roles table
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();


    // ---------------- CONSTRUCTORS ----------------

    public User() {
    }


    // ---------------- GETTERS / SETTERS ----------------

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}