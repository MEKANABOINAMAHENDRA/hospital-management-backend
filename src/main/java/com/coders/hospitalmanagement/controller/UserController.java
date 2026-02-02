package com.coders.hospitalmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.coders.hospitalmanagement.model.User;
import com.coders.hospitalmanagement.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    // GET USERS BY ROLE (ADMIN → NURSES)
    @GetMapping
    public List<User> getUsersByRole(@RequestParam String role) {
        return userRepo.findByRole(role.toUpperCase());
    }

    // GET USER BY ID (EDIT)
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // UPDATE USER (EDIT NURSE DETAILS)
    @PutMapping("/{id}")
    public User updateUser(
            @PathVariable int id,
            @RequestBody User req) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(req.getUsername());
        // ❌ no password update
        // ❌ no role update

        return userRepo.save(user);
    }

    // ✅ ENABLE / DISABLE NURSE
    @PatchMapping("/{id}/status")
    public User updateUserStatus(
            @PathVariable int id,
            @RequestParam boolean enabled) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEnabled(enabled);
        return userRepo.save(user);
    }
}
