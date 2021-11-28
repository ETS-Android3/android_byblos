package com.example.segproject;

public class ServiceRequest {

    String serviceID;
    String branchID;
    String name;
    double rate;
    String firstName;
    String lastName;
    String dob;
    String address;
    String email;
    boolean g1;
    boolean g2;
    boolean g3;
    boolean compact;
    boolean intermediate;
    boolean suv;
    String pickupdate;
    String pickuptime;
    String returndate;
    String returntime;
    String movingstartlocation;
    String movingendlocation;
    String area;
    String kmdriven;
    String numberofmovers;
    String numberofboxes;

    public ServiceRequest (String address, String area, boolean compact, String dob,
                           String email, String firstName, boolean g1, boolean g2, boolean g3,
                           boolean intermediate, String kmdriven, String lastName, String movingendlocation,
                           String movingstartlocation, String name, String numberofboxes, String numberofmovers,
                           String pickupdate, String pickuptime, double rate,String returndate, String returntime,
                           boolean suv, String serviceID, String branchID){
        this.name = name;
        this.rate = rate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.address = address;
        this.email = email;
        this.g1 = g1;
        this.g2 = g2;
        this.g3 = g3;
        this.compact = compact;
        this.intermediate = intermediate;
        this.suv = suv;
        this.pickupdate = pickupdate;
        this.pickuptime = pickuptime;
        this.returndate = returndate;
        this.returntime = returntime;
        this.movingstartlocation = movingstartlocation;
        this.movingendlocation = movingendlocation;
        this.area = area;
        this.kmdriven = kmdriven;
        this.numberofmovers = numberofmovers;
        this.numberofboxes = numberofboxes;
        this.serviceID = serviceID;
        this.branchID = branchID;
    }


    public String getServiceID(){return serviceID;}

    public String getBranchID(){return branchID;}

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }

    public String getFirstName() { return firstName; }

    public String getLastName() {
        return lastName;
    }

    public String getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public boolean isG1() {
        return g1;
    }

    public boolean isG2() {
        return g2;
    }

    public boolean isG3() {
        return g3;
    }

    public boolean isCompact() {
        return compact;
    }

    public boolean isIntermediate() {
        return intermediate;
    }

    public boolean isSUV() {
        return suv;
    }

    public String getPickupdate() {
        return pickupdate;
    }

    public String getPickuptime() {
        return pickuptime;
    }

    public String getReturndate() {
        return returndate;
    }

    public String getReturntime() {
        return returntime;
    }

    public String getMovingstartlocation() {
        return movingstartlocation;
    }

    public String getMovingendlocation() {
        return movingendlocation;
    }

    public String getArea() {
        return area;
    }

    public String getKmdriven() {
        return kmdriven;
    }

    public String getNumberofmovers() {
        return numberofmovers;
    }

    public String getNumberofboxes() {
        return numberofboxes;
    }


}
