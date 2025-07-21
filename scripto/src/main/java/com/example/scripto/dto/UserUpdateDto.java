package com.example.scripto.dto;

import lombok.Data;

@Data
public class UserUpdateDto {
    private String fullName;
    private String password; // new password
}

