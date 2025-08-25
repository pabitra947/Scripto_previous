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



    // for update password
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody UserUpdateDto updateDto) {
        return userServiceImpl.updateUser(updateDto);
    }



    // for delete user
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser() {
        return userServiceImpl.deleteUser();
    }


}
