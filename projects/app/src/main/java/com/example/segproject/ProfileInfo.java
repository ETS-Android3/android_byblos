package com.example.segproject;

public class ProfileInfo {
    public int streetNum;
    public String streetName;
    public String phoneNum;
    public String employeeID;
    public String services;

    public ProfileInfo() {
    }

    public ProfileInfo(int num, String street, String phoneNum, String id, String services){
        this.streetNum = num;
        this.streetName = street;
        this.phoneNum = phoneNum;
        this.employeeID = id;
        this.services = services;
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
}
