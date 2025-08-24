package com.example.scripto.response.buyer.address;

import com.example.scripto.entity.BuyerAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerAddressResponse {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    public BuyerAddressResponse(BuyerAddress address){
        this.street = address.getStreet();
        this.city = address.getCity();
        this.state = address.getState();
        this.postalCode = address.getPostalCode();
        this.country = address.getCountry();
    }
}
