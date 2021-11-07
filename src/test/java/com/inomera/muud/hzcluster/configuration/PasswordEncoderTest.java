package com.app.hzcluster.configuration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordEncoderTest {
    @Test
    @DisplayName("This test is written not to forget production password's plain value :) ")
    void bcryptEncoder_shouldEncodePassword() {
        final var password = "d8a194e0b35f23f6fd5126d60296eb4f";
        final var encoded = "$2a$11$xrDaYpnpILVS3CEwe2twROxJaF9UImNHHlgQjxpe3CcEF1vqNrvPm";
        assertTrue(passwordEncoder().matches(password, encoded));
    }

    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }
}
