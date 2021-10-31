package com.example.segproject;

public class Address {
    String postalCode;
    int streetNumber;
    String streetName;
    String city;
    String country;

    //branch constructor
    public Address(String postalCode, int streetNumber, String streetName, String city, String country){
        this.postalCode = postalCode;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.country = country;
    }
}
