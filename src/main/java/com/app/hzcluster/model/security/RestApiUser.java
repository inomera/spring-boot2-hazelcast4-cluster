package com.app.hzcluster.model.security;

import com.app.hzcluster.configuration.properties.CredentialInfo;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ToString
public class RestApiUser implements UserDetails {
    private static final long serialVersionUID = 1L;

    private final String username;
    @ToString.Exclude
    private final String hashedPassword;
    private final List<GrantedAuthority> authorities;
    @ToString.Exclude
    private String plainPassword = null;

    public RestApiUser(CredentialInfo credentials) {
        this.username = credentials.getUsername();
        this.hashedPassword = credentials.getPassword();
        this.authorities = Optional.ofNullable(credentials.getAuthorities())
                .map(collection -> collection.toArray(new String[0]))
                .map(AuthorityUtils::createAuthorityList)
                .orElseGet(ArrayList::new);
    }

    public void checkPassword(PasswordEncoder passwordEncoder, String presentedPassword) {
        if (plainPassword != null) {
            if (StringUtils.equals(plainPassword, presentedPassword)) {
                return;
            }
            throw new BadCredentialsException("Bad credentials");
        }

        if (passwordEncoder.matches(presentedPassword, hashedPassword)) {
            this.plainPassword = presentedPassword;
            return;
        }

        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return hashedPassword;
    }

    @Override
    public String getUsername() {
        return username;
    }

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
}
