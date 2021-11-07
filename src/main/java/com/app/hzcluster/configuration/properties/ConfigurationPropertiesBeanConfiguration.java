package com.app.hzcluster.configuration.properties;

import com.hazelcast.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationPropertiesBeanConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "hz-cluster")
    public Config hazelcastServerConfig() {
        return new Config();
    }

    @Bean
    @ConfigurationProperties(prefix = "security-configurations")
    public SecurityConfigurationProperties securityConfigurationProperties() {
        return new SecurityConfigurationProperties();
    }
}
