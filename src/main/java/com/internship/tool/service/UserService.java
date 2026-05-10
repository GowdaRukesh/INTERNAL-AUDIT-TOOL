package com.internship.tool.service;

import com.internship.tool.dto.LoginRequestDto;
import com.internship.tool.dto.UserRequestDto;
import com.internship.tool.entity.User;
import com.internship.tool.exception.UserAlreadyExistsException;
import com.internship.tool.exception.UserNotFoundException;
import com.internship.tool.repository.UserRepository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // LOGIN
    public User login(LoginRequestDto loginRequestDto) {

        Optional<User> user =
                userRepository.findByEmail(loginRequestDto.getEmail());

        if (user.isPresent()
                && passwordEncoder.matches(
                loginRequestDto.getPassword(),
                user.get().getPassword())) {

            return user.get();
        }

        throw new RuntimeException("Invalid email or password");
    }

    // CREATE USER
    @CacheEvict(value = "users", allEntries = true)
    public User createUser(UserRequestDto requestDto) {

        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = new User();

        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());

        // PASSWORD ENCRYPTION
        user.setPassword(
                passwordEncoder.encode(requestDto.getPassword())
        );

        user.setRole(requestDto.getRole());

        return userRepository.save(user);
    }

    // GET ALL USERS
    @Cacheable("users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // GET USER BY ID
    @Cacheable(value = "user", key = "#id")
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));
    }
}