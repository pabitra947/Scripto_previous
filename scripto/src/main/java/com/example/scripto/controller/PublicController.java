package com.example.scripto.controller;

import com.example.scripto.dto.UserDto;
import com.example.scripto.entity.User;
import com.example.scripto.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserServiceImpl userService;


    // for create user
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDto userDto) {
        return userService.createNewUser(userDto);
    }


    // for get all user from db
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }
}
