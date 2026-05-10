package com.internship.tool.controller;

import com.internship.tool.dto.LoginRequestDto;
import com.internship.tool.entity.User;
import com.internship.tool.security.JwtUtil;
import com.internship.tool.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto) {

        User user = userService.login(loginRequestDto);

        return jwtUtil.generateToken(
        user.getEmail(),
        user.getRole()
);
    }
}