package com.simplebank.controller;

import com.simplebank.dto.LoginRequest;
import com.simplebank.dto.UserRequest;
import com.simplebank.dto.UserResponse;
import com.simplebank.model.User;
import com.simplebank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> user = userService.login(request);
        return user.<ResponseEntity<?>>map(ResponseEntity::ok)
           .orElseGet(() -> ResponseEntity.status(401).body("Invalid credentials"));

    }
}
