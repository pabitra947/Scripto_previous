package com.example.scripto.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    private LocalDateTime createdAt;
}
