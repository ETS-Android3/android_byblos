package com.example.segproject;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    public String servicesNames;
    public String requests;
    public String acceptedRequests;


    public BranchProfile() {
    }

    public BranchProfile(int num, String street, String phoneNum, String id, String city,
                         String state, String country, String zip, String services, String servicesNames, String hours,
                         String requests, String acceptedRequests){
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
        this.servicesNames = servicesNames;
        this.requests = requests;
        this.acceptedRequests = acceptedRequests;
    }

    public String getRequests() {
        return requests;
    }

    public String getAcceptedRequests() {
        return acceptedRequests;
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

    public String getServicesNames(){return servicesNames; }


}
