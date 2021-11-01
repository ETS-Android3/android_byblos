package com.example.segproject;

public class NewService {

    String name;
    int rate;
    boolean firstName;
    boolean lastName;
    boolean dob;
    boolean address;
    boolean email;
    boolean G1;
    boolean G2;
    boolean G3;
    boolean compact;
    boolean intermediate;
    boolean SUV;
    boolean pickupdate;
    boolean pickuptime;
    boolean returndate;
    boolean returntime;
    boolean movingstartlocation;
    boolean movingendlocation;
    boolean area;
    boolean kmdriven;
    boolean numberofmovers;
    boolean numberofboxes;

    public NewService(){

    }

    public NewService(boolean address, boolean area, boolean compact, boolean dob,
                      boolean email, boolean firstName, boolean g1, boolean g2, boolean g3,
                      boolean intermediate, boolean kmdriven, boolean lastName, boolean movingendlocation,
                      boolean movingstartlocation, String name, boolean numberofboxes,boolean numberofmovers,
                      boolean pickupdate, boolean pickuptime,int rate,boolean returndate, boolean returntime,
                      boolean SUV) {
        this.name = name;
        this.rate = rate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.address = address;
        this.email = email;
        G1 = g1;
        G2 = g2;
        G3 = g3;
        this.compact = compact;
        this.intermediate = intermediate;
        this.SUV = SUV;
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
    }

    public String getName() {
        return name;
    }

    public int getRate() {
        return rate;
    }

    public boolean isFirstName() {
        return firstName;
    }

    public boolean isLastName() {
        return lastName;
    }

    public boolean isDob() {
        return dob;
    }

    public boolean isAddress() {
        return address;
    }

    public boolean isEmail() {
        return email;
    }

    public boolean isG1() {
        return G1;
    }

    public boolean isG2() {
        return G2;
    }

    public boolean isG3() {
        return G3;
    }

    public boolean isCompact() {
        return compact;
    }

    public boolean isIntermediate() {
        return intermediate;
    }

    public boolean isSUV() {
        return SUV;
    }

    public boolean isPickupdate() {
        return pickupdate;
    }

    public boolean isPickuptime() {
        return pickuptime;
    }

    public boolean isReturndate() {
        return returndate;
    }

    public boolean isReturntime() {
        return returntime;
    }

    public boolean isMovingstartlocation() {
        return movingstartlocation;
    }

    public boolean isMovingendlocation() {
        return movingendlocation;
    }

    public boolean isArea() {
        return area;
    }

    public boolean isKmdriven() {
        return kmdriven;
    }

    public boolean isNumberofmovers() {
        return numberofmovers;
    }

    public boolean isNumberofboxes() {
        return numberofboxes;
    }

}
