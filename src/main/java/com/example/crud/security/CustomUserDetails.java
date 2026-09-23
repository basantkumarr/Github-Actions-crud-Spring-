package com.example.crud.security;

import com.example.crud.entity.Role;
import com.example.crud.entity.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;


/*
 * ============================================================
 * CUSTOM USER DETAILS
 * ============================================================
 *
 * Spring Security does not directly understand our:
 *
 *      com.example.crud.entity.User
 *
 * Spring Security understands:
 *
 *      UserDetails
 *
 * Therefore we create an adapter:
 *
 *              Database User
 *                    |
 *                    v
 *             CustomUserDetails
 *                    |
 *                    v
 *              Spring Security
 *
 * ============================================================
 */

public class CustomUserDetails
        implements UserDetails {


    /*
     * Our database/domain User.
     */
    private final User user;


    public CustomUserDetails(User user) {
        this.user = user;
    }


    /*
     * ========================================================
     * getAuthorities()
     * ========================================================
     *
     * Converts our Role objects into Spring Security
     * GrantedAuthority objects.
     *
     * Example:
     *
     * Role:
     *      ROLE_USER
     *
     * becomes:
     *
     * SimpleGrantedAuthority:
     *      ROLE_USER
     *
     * ========================================================
     */

    @Override
    public Collection<? extends GrantedAuthority>
    getAuthorities() {

        return user.getRoles()
                .stream()
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }


    /*
     * ========================================================
     * getPassword()
     * ========================================================
     *
     * Returns the BCrypt password stored in database.
     *
     * It is NOT the raw password entered by the user.
     * ========================================================
     */

    @Override
    public String getPassword() {
        return user.getPassword();
    }


    /*
     * ========================================================
     * getUsername()
     * ========================================================
     *
     * Returns our login identity.
     *
     * In this application:
     *
     * username = login identity
     *
     * ========================================================
     */

    @Override
    public String getUsername() {
        return user.getUsername();
    }


    /*
     * ========================================================
     * ACCOUNT STATUS
     * ========================================================
     *
     * These methods allow us to disable/lock accounts later.
     *
     * For now we return true.
     * ========================================================
     */

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    /*
     * Optional helper method.
     *
     * Allows us to access our original database User if needed.
     */
    public User getUser() {
        return user;
    }
}