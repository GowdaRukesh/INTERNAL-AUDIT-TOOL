package com.internship.tool.controller;

import com.internship.tool.service.EmailService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/send")
    public String sendMail() {

        emailService.sendEmail(
                "srahulgowda08@gmail.com",
                "Test Email",
                "Spring Boot Email Working Successfully"
        );

        return "Email Sent Successfully";
    }
}