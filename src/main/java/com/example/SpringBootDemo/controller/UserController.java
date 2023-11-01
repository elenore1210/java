package com.example.SpringBootDemo.controller;

import com.example.SpringBootDemo.entity.User;
import com.example.SpringBootDemo.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class UserController {

    private final UserService userService;
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String hello() {
        logger.info("hih777789999");
        return "hih77778999";
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody @NotNull UserLoginRequest userLoginRequest) {
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();

        User user = userService.loginUser(username, password);
        if (user != null) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }

    @GetMapping("/getAll")
    public String getAllUsers() {
        List<User> users = userService.getAllUsers();
        StringBuilder result = new StringBuilder();
        result.append("All Users:<br>");
        for (User user : users) {
            result.append("ID: ").append(user.getId()).append(", Username: ").append(user.getUsername()).append("<br>");
        }
        return result.toString();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        boolean isUserCreated = userService.saveUser(user);
        if (isUserCreated) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User creation failed");
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        boolean isUserDeleted = userService.deleteUser(userId);
        if (isUserDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User deletion failed");
        }
    }

}