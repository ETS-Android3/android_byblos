package com.example.segproject;

public class BranchProfile { // branch info
    public int streetNum;
    public String streetName;
    public String phoneNum;
    public String employeeID;
    public String city;
    public String state;
    public String country;
    public String zip;
    public String services;



    public String wholeAddress;

    public BranchProfile() {
    }

    public BranchProfile(int num, String street, String phoneNum, String id, String city,
                         String state, String country, String zip, String services){
        this.streetNum = num;
        this.streetName = street;
        this.phoneNum = phoneNum;
        this.employeeID = id;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zip = zip;
        this.services = services;
        this.wholeAddress = streetNum + ", "+ streetName + ", " + this.city + ", " +  this.state + ", " + this.country +", " + this.zip;
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

    public String getEmployeeID() {
        return employeeID;
    }

    public String getWholeAddress() { return (streetNum + ", "+ streetName + ", " + this.city + ", " +  this.state + ", " + this.country +", " + this.zip) ; }
}
