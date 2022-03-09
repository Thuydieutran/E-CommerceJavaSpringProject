package com.thuydieutran.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordEncoderTest {
    @Test
    public void testEncodePassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String initialPassword = "password";
        String encodedPassword = passwordEncoder.encode(initialPassword);
        System.out.println(encodedPassword);
        boolean matches = passwordEncoder.matches(initialPassword, encodedPassword);
        assertThat(matches).isTrue();
    }
}
