package com.example.scripto.implementation;

import com.example.scripto.entity.BuyerAddress;
import com.example.scripto.entity.User;
import com.example.scripto.repository.BuyerAddressRepo;
import com.example.scripto.repository.UserRepository;
import com.example.scripto.response.buyer.address.BuyerAddressResponse;
import com.example.scripto.service.IBuyerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyerAddressServiceImpl implements IBuyerAddress {

    @Autowired private BuyerAddressRepo addressRepo;
    @Autowired private UserRepository userRepository;


    //Used to save the address
    @Override
    public ResponseEntity<String> addAddress(BuyerAddress address){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String buyerEmail = authentication.getName();

        User buyer = userRepository.findByEmail(buyerEmail);

        if(buyer == null){
            throw new RuntimeException("Buyer not exists.");
        }

        address.setUser(buyer);
        addressRepo.save(address);

        return new ResponseEntity<>("The address is saved.", HttpStatus.OK);
    }


    //used to get the buyer address
    @Override
    public ResponseEntity<List<BuyerAddressResponse>> getAddress(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String buyerEmail = authentication.getName();

        User buyer = userRepository.findByEmail(buyerEmail);

        if(buyer == null){
            throw new RuntimeException("Buyer not exists.");
        }

        List<BuyerAddress> byUser = addressRepo.findByUser(buyer);

        List<BuyerAddressResponse> newList = byUser.stream().map(BuyerAddressResponse::new).toList();

        return new ResponseEntity<>(newList, HttpStatus.OK);
    }


    //used to update the address of the buyer
    @Override
    public ResponseEntity<String> updateAddress(Long id, BuyerAddress updatedAddress){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String buyerEmail = authentication.getName();

        User buyer = userRepository.findByEmail(buyerEmail);

        if(buyer == null){
            throw new RuntimeException("Buyer not exists.");
        }

        BuyerAddress existingAddress = addressRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!existingAddress.getUser().getEmail().equals(buyerEmail)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("You can only update your own address");
        }

        existingAddress.setStreet(updatedAddress.getStreet());
        existingAddress.setCity(updatedAddress.getCity());
        existingAddress.setState(updatedAddress.getState());
        existingAddress.setPostalCode(updatedAddress.getPostalCode());
        existingAddress.setCountry(updatedAddress.getCountry());

        addressRepo.save(existingAddress);

        return ResponseEntity.ok("Address updated successfully");
    }
}
