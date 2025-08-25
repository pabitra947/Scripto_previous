package com.example.scripto.service;

import com.example.scripto.entity.BuyerAddress;
import com.example.scripto.response.buyer.address.BuyerAddressResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBuyerAddress {
    ResponseEntity<String> addAddress(BuyerAddress address);

    ResponseEntity<List<BuyerAddressResponse>> getAddress();

    ResponseEntity<String> updateAddress(Long id, BuyerAddress updatedAddress);
}
