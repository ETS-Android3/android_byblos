package com.example.segproject;

public class Service {
    String firstName;
    String lastName;
    String dateOfBirth;
    String id;
    String serviceType;

    Address address;
    String email;

    int rate;

    public Service (String id, String serviceType, int rate, String firstName, String lastName, String dateOfBirth, Address address, String email){
        this.id = id;
        this.serviceType = serviceType;
        this.rate = rate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.email = email;
    }

}
