package com.example.scripto.service;

import com.example.scripto.dto.UserDto;
import com.example.scripto.dto.UserUpdateDto;
import com.example.scripto.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUser {

    ResponseEntity<User> createNewUser(UserDto userDto);

    ResponseEntity<User> updateUser(UserUpdateDto updateDto);

    ResponseEntity<String> deleteUser();

    ResponseEntity<List<User>> getAllUsers();
}
