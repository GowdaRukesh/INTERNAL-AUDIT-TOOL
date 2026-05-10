package com.internship.tool.config;

import com.internship.tool.entity.User;
import com.internship.tool.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository,
                      PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() == 0) {

            User admin = new User();

            admin.setUsername("Admin User");
            admin.setEmail("admin@gmail.com");

            admin.setPassword(
                    passwordEncoder.encode("admin123")
            );

            admin.setRole("ADMIN");

            userRepository.save(admin);

            User user = new User();

            user.setUsername("Normal User");
            user.setEmail("user@gmail.com");

            user.setPassword(
                    passwordEncoder.encode("user123")
            );

            user.setRole("USER");

            userRepository.save(user);

            System.out.println("Demo users inserted successfully");
        }
    }
}