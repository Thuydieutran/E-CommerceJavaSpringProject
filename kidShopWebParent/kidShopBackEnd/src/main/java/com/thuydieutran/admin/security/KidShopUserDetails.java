package com.thuydieutran.admin.security;

import com.thuydieutran.kidshopcommon.entities.Role;
import com.thuydieutran.kidshopcommon.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class KidShopUserDetails implements UserDetails {

    private User user;

    public KidShopUserDetails(User user) {
        this.user = user;
    }

    // This method is used to get the list of users' roles
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();
        // Create a list of authenticated roles
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        for(Role role: roles) {
            authorityList.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorityList;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public String getName() {
        return this.user.getFirstname() + " " + this.user.getLastname();
    }

    // This method returns true means the users' accounts will not expire
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
        return user.isEnable();
    }
}
