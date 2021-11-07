
package com.app.hzcluster.service.security;

import com.app.hzcluster.configuration.properties.SecurityConfigurationProperties;
import com.app.hzcluster.model.security.RestApiUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class HzClusterUserDetailsService implements UserDetailsService {
    private final SecurityConfigurationProperties securityConfigurationProperties;
    private Map<String, RestApiUser> userMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        initializeUserMap();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return Optional.ofNullable(this.userMap.get(username))
                .orElseThrow(() -> new UsernameNotFoundException("Bad Credentials"));
    }

    private void initializeUserMap() {
        this.userMap = securityConfigurationProperties.getUsers()
                .stream()
                .map(RestApiUser::new)
                .collect(Collectors.toConcurrentMap(RestApiUser::getUsername, Function.identity(), (lhs, rhs) -> rhs));
        LOG.info("Loaded {} RestApiUsers for REST authentication", userMap.size());
    }
}
