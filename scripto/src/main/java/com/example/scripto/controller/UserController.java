package com.example.scripto.controller;

import com.example.scripto.dto.UserUpdateDto;
import com.example.scripto.dto.UserDto;
import com.example.scripto.entity.User;
import com.example.scripto.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;



    // for create user
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDto userDto) {
        return userServiceImpl.createNewUser(userDto);
    }



    // for update password
    @PutMapping("/update/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody UserUpdateDto updateDto) {
        return userServiceImpl.updateUser(email,updateDto);
    }




    // for delete user
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String email) {
        return userServiceImpl.deleteUser(email);
    }



    // for get all user from db
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
       return userServiceImpl.getAllUsers();
    }

}
