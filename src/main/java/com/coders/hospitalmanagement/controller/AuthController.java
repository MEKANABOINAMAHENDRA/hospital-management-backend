package com.coders.hospitalmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.coders.hospitalmanagement.model.User;
import com.coders.hospitalmanagement.security.JwtUtil;
import com.coders.hospitalmanagement.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // REGISTER
    @PostMapping("/register")
    public String register(@RequestBody User user) {

        // role must come as ADMIN / DOCTOR / PATIENT from frontend
    	  user.setRole(user.getRole().toUpperCase());

        userService.saveUser(user);
        return "User registered successfully";
    }

    // LOGIN
    @PostMapping("/login")
    public String login(@RequestBody User user) {

        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()
            )
        );

        UserDetails details =
            userService.loadUserByUsername(user.getUsername());

        String role = details.getAuthorities()
                .iterator()
                .next()
                .getAuthority()
                .replace("ROLE_", "");

        return jwtUtil.generateToken(
            details.getUsername(),
            role
        );
    }
}
