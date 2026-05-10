package com.internship.tool.controller;

import com.internship.tool.dto.UserRequestDto;
import com.internship.tool.entity.User;
import com.internship.tool.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CREATE USER
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(
            @Valid @RequestBody UserRequestDto requestDto) {

        return userService.createUser(requestDto);
    }

    // GET ALL USERS
    @GetMapping("/all")
    public List<User> getAllUsers() {

        return userService.getAllUsers();
    }

    // GET USER BY ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {

        return userService.getUserById(id);
    }

    // ADMIN API
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {

        return "Welcome Admin";
    }
}