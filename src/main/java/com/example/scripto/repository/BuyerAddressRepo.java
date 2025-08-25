package com.example.scripto.repository;

import com.example.scripto.entity.BuyerAddress;
import com.example.scripto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuyerAddressRepo extends JpaRepository<BuyerAddress, Long> {
    List<BuyerAddress> findByUser(User user);
}
