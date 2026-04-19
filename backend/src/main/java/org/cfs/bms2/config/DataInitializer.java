package org.cfs.bms2.config;


import lombok.RequiredArgsConstructor;
import org.cfs.bms2.entity.Role;
import org.cfs.bms2.entity.User;
import org.cfs.bms2.repository.RoleRepository;
import org.cfs.bms2.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdmin() {
        return args -> {

            // ✅ check if admin already exists
            if (userRepository.existsByUsername("admin")) {
                System.out.println("Admin already exists");
                return;
            }

            // ✅ get admin role
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found"));

            // ✅ create admin
            User admin = User.builder()
                    .username("admin")
                    .email("admin@gmail.com")
                    .phone("9999999999")
                    .password(passwordEncoder.encode("admin123"))
                    .roles(Set.of(adminRole))
                    .build();

            userRepository.save(admin);

            System.out.println("🔥 Default admin created: admin / admin123");
        };
    }
}