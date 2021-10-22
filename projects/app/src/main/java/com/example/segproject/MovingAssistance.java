package com.example.segproject;

public class MovingAssistance extends Service{

    Address startLocation;
    Address endLocation;
    int numberMovers;
    int numberBoxes;

    public MovingAssistance(String id ,String serviceType, int rate, String firstName, String lastName, String dateOfBirth, Address address, String email, Address startLocation, Address endLocation, int numberMovers, int numberBoxes){
        super(id,serviceType, rate, firstName, lastName, dateOfBirth, address, email);

        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.numberMovers = numberMovers;
        this.numberBoxes = numberBoxes;

    }
}
