package com.example.scripto.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NonNull
    private String fullName;  // ✅ New field for full name

    @Column(unique = true)
    @NonNull
    @Email
    private String email;

    @NonNull
    private String password;

    @NonNull
    private String role; // "BUYER" or "SELLER"

    private String mobileNo;

    private String shopName; // Only set for SELLER


    //One seller can add many books
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<BookListing> booksAdded;

}
