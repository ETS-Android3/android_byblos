package com.example.segproject;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BranchProfile { // branch info
    public int streetNum;
    public String streetName;
    public String phoneNum;
    public String city;
    public String state;
    public String country;
    public String zip;
    public String services;
    public String hours;
    public String branchID;

//    public DatabaseReference dbGloServs;
//    public DatabaseReference dbBranches;
//    public DatabaseReference dbWorkingHours;

    public WorkingHours branchHours;

    public BranchProfile() {
    }

    public BranchProfile(int num, String street, String phoneNum, String id, String city,
                         String state, String country, String zip, String services, String hours){
        this.streetNum = num;
        this.streetName = street;
        this.phoneNum = phoneNum;
        this.branchID = id;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zip = zip;
        this.services = services;
        this.hours = hours;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getZip() {
        return zip;
    }

    public String getServices() {
        return services;
    }

    public int getStreetNum() {
        return streetNum;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getBranchID() {
        return branchID;
    }

    public String getHours(){return hours;}

    public String getWholeAddress() { return (streetNum + ", "+ streetName + ", " + this.city + ", " +  this.state + ", " + this.country +", " + this.zip) ; }

    public String getServiceNames(){
//        dbGloServs = FirebaseDatabase.getInstance().getReference("GlobalService");
//        dbBranches = FirebaseDatabase.getInstance().getReference("branch");

        return "";
    }

}
