package com.app.hzcluster.service.security;

import com.app.hzcluster.model.security.RestApiUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class RestApiAuthenticationProvider extends DaoAuthenticationProvider {
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            LOG.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException("Bad credentials");
        }

        final var presentedPassword = authentication.getCredentials().toString();

        if (userDetails instanceof RestApiUser) {
            ((RestApiUser) userDetails).checkPassword(getPasswordEncoder(), authentication.getCredentials().toString());
            return;
        }

        if (!getPasswordEncoder().matches(presentedPassword, userDetails.getPassword())) {
            logger.debug("Authentication failed: password does not match stored value");
            throw new BadCredentialsException("Bad credentials");
        }
    }
}
