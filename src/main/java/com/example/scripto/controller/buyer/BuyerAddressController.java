package com.example.scripto.controller.buyer;

import com.example.scripto.entity.BuyerAddress;
import com.example.scripto.implementation.BuyerAddressServiceImpl;
import com.example.scripto.response.buyer.address.BuyerAddressResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buyer/address")
public class BuyerAddressController {

    @Autowired
    private BuyerAddressServiceImpl buyerAddressService;


    @PostMapping("/add")
    public ResponseEntity<String> addAddress(@RequestBody BuyerAddress address) {
        return buyerAddressService.addAddress(address);
    }

    @GetMapping("/see")
    public ResponseEntity<List<BuyerAddressResponse>> getMyAddresses() {
        return buyerAddressService.getAddress();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAddress(@PathVariable Long id,
                                                @RequestBody BuyerAddress updatedAddress) {
        return buyerAddressService.updateAddress(id, updatedAddress);
    }
}
