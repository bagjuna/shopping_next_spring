package org.zerock.apiserver.global.security.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.zerock.apiserver.domain.account.entity.AccountEntity;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final AccountEntity user;

    public CustomUserDetails(AccountEntity user) {
        this.user = user;
    }

    public static CustomUserDetails of(AccountEntity member) {
        return new CustomUserDetails(member);
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
//                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
                new SimpleGrantedAuthority("ROLE_USER")
        );
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}
