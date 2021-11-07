package com.app.hzcluster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;

@SpringBootApplication(exclude = {HazelcastAutoConfiguration.class})
public class HzClusterApplication {

    public static void main(String[] args) {
        SpringApplication.run(HzClusterApplication.class, args);
    }
}
