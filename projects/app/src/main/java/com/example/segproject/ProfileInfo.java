package com.example.segproject;

public class ProfileInfo {
    int locationNum;
    String locationStreet;
    String locationPhoneNum;
    String employeeID;

    public ProfileInfo() {
    }

    public ProfileInfo(int num, String street, String phoneNum, String id){
        this.locationNum = num;
        this.locationStreet = street;
        this.locationPhoneNum = phoneNum;
        this.employeeID = id;
    }


    public int getLocationNum() {
        return locationNum;
    }

    public String getLocationStreet() {
        return locationStreet;
    }

    public String getLocationPhoneNum() {
        return locationPhoneNum;
    }

    public String getEmployeeID() {
        return employeeID;
    }
}
