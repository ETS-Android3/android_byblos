package com.example.segproject;

import androidx.appcompat.app.AppCompatActivity;

public class BranchServiceList {
    public boolean truck;
    public boolean car;
    public boolean movingAssistance;
    public boolean bike;
    public boolean boat;
    public String branchServiceListUID;
    public String branchID;

    public BranchServiceList(){}

    public BranchServiceList(boolean truck, boolean car, boolean moving,
                             boolean bike, boolean boat, String branchServiceListUID, String branchID){
        this.truck = truck;
        this.car = car;
        this.movingAssistance = moving;
        this.bike = bike;
        this.boat = boat;
        this.branchID = branchID;
        this.branchServiceListUID = branchServiceListUID;
    }

    public String getBranchServiceListUID() {
        return branchServiceListUID;
    }

    public String getBranchID() {
        return branchID;
    }

    public boolean isTruck() {
        return truck;
    }

    public boolean isCar() {
        return car;
    }

    public boolean isMovingAssistance() {
        return movingAssistance;
    }

    public boolean isBike() {
        return bike;
    }

    public boolean isBoat() {
        return boat;
    }
}
