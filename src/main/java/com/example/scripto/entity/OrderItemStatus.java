package com.example.scripto.entity;

public enum OrderItemStatus {
    PENDING,    // buyer placed, waiting seller action
    CONFIRMED,  // seller accepted
    REJECTED,   // seller rejected
    SHIPPED,    // seller shipped — reduce stock now
    DELIVERED   // delivered to buyer
}
