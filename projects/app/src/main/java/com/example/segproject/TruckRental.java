package com.example.segproject;

public class TruckRental extends Service {
    String license;
    int pickupTime;
    String pickUpDate;
    int returnTime;
    String returnDate;

    int maxKM;
    String areaDriven;

    public TruckRental(String id,String serviceType ,int rate, String firstName, String lastName, String dateOfBirth, Address address, String email, String license, int pickupTime, String pickUpDate, int returnTime, String returnDate, int maxKM, String areaDriven){
        super(id, serviceType, rate, firstName, lastName, dateOfBirth, address, email);
        this.license = license;
        this.pickupTime = pickupTime;
        this.pickUpDate = pickUpDate;
        this.returnTime = returnTime;
        this.returnDate = returnDate;
        this.maxKM = maxKM;
        this.areaDriven = areaDriven;
    }

}
