package com.internship.tool.service;

import com.internship.tool.dto.UserRequestDto;
import com.internship.tool.entity.User;
import com.internship.tool.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {

        UserRequestDto dto = new UserRequestDto();

        dto.setUsername("veeresh");
        dto.setEmail("srahulgowda08@gmail.com");
        dto.setPassword("12345");
        dto.setRole("ADMIN");

        when(userRepository.existsByEmail(dto.getEmail()))
                .thenReturn(false);

        when(passwordEncoder.encode(dto.getPassword()))
                .thenReturn("encodedPassword");

        User savedUser = new User();

        savedUser.setId(1L);
        savedUser.setUsername(dto.getUsername());
        savedUser.setEmail(dto.getEmail());
        savedUser.setPassword("encodedPassword");
        savedUser.setRole(dto.getRole());

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        User result = userService.createUser(dto);

        assertNotNull(result);

        assertEquals(
                "srahulgowda08@gmail.com",
                result.getEmail()
        );
    }

    @Test
    void testGetUserById() {

        User user = new User();

        user.setId(1L);
        user.setUsername("Veeresh");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);

        assertEquals(
                "Veeresh",
                result.getUsername()
        );
    }

    @Test
    void testGetAllUsers() {

        User user1 = new User();
        user1.setUsername("User1");

        User user2 = new User();
        user2.setUsername("User2");

        when(userRepository.findAll())
                .thenReturn(List.of(user1, user2));

        List<User> users = userService.getAllUsers();

        assertEquals(2, users.size());
    }
}