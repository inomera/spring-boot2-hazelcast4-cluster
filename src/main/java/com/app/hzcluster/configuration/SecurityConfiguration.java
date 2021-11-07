package com.app.hzcluster.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.app.hzcluster.configuration.properties.SecurityConfigurationProperties;
import com.app.hzcluster.service.security.HzClusterUserDetailsService;
import com.app.hzcluster.service.security.JsonSecurityExceptionHandler;
import com.app.hzcluster.service.security.RestApiAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Value("${spring.application.name}")
    private final String applicationName;
    private final ObjectMapper objectMapper;
    private final HzClusterUserDetailsService userDetailsService;
    private final SecurityConfigurationProperties securityConfigurationProperties;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        final var authenticationProvider = new RestApiAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final var handler = new JsonSecurityExceptionHandler(applicationName, objectMapper);
        http.httpBasic().and()
                .authorizeRequests()
                .antMatchers("/**")
                .authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(handler)
                .accessDeniedHandler(handler)
                .and()
                .csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        final var passwordStrength = securityConfigurationProperties.getBcryptPasswordStrength();
        return new BCryptPasswordEncoder(passwordStrength);
    }
}
