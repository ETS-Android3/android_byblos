package com.example.segproject;

public class CarRental extends Service{
    String carType;
    int pickupTime;
    String pickUpDate;
    int returnTime;
    String returnDate;

    public CarRental(String id, String serviceType, int rate, String firstName, String lastName, String dateOfBirth, Address address, String email, String carType, int pickupTime, String pickUpDate, int returnTime, String returnDate){
        super(id, serviceType, rate, firstName, lastName, dateOfBirth, address, email);

        this.carType = carType;
        this.pickupTime = pickupTime;
        this.pickUpDate = pickUpDate;
        this.returnTime = returnTime;
        this.returnDate = returnDate;
    }
}
