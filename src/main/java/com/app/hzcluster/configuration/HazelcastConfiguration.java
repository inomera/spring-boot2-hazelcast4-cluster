package com.app.hzcluster.configuration;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class HazelcastConfiguration {
    private final Config config;

    @Bean
    public HazelcastInstance hazelcastInstance() {
        LOG.debug("Starting HzCluster with configuration: {}", config);
        return Hazelcast.newHazelcastInstance(config);
    }
}
