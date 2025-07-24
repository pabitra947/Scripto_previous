package com.example.scripto.dto;

import lombok.Data;

@Data
public class UserDto {
    private String fullName;  // Add this
    private String email;
    private String password;
    private String role;      // BUYER or SELLER
    private String mobileNo;
    private String shopName;  // Optional, only for SELLER
}