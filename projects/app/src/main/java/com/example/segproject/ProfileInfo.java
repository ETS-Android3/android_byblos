package com.example.segproject;

public class ProfileInfo {
    public int streetNum;
    public String streetName;
    public String phoneNum;
    public String employeeID;
    public String city;
    public String state;
    public String country;
    public String zip;
    public String services;

    public ProfileInfo() {
    }

    public ProfileInfo(int num, String street, String phoneNum, String id, String city,
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
}
