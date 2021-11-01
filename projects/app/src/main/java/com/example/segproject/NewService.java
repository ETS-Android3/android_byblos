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


    public NewService(String name, int rate, boolean firstName, boolean lastName,
                      boolean dob, boolean address, boolean email, boolean g1,
                      boolean g2, boolean g3, boolean compact, boolean intermediate,
                      boolean SUV, boolean pickupdate, boolean pickuptime,
                      boolean returndate, boolean returntime,
                      boolean movingstartlocation, boolean movingendlocation,
                      boolean area, boolean kmdriven, boolean numberofmovers,
                      boolean numberofboxes) {
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

    //    public String getName() {
//        return name;
//    }
//
//    public int getRate() {
//        return rate;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public String getDob() {
//        return dob;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public String getG1() {
//        return G1;
//    }
//
//    public String getG2() {
//        return G2;
//    }
//
//    public String getG3() {
//        return G3;
//    }
//
//    public String getCompact() {
//        return compact;
//    }
//
//    public String getIntermediate() {
//        return intermediate;
//    }
//
//    public String getSUV() {
//        return SUV;
//    }
//
//    public String getPickupdate() {
//        return pickupdate;
//    }
//
//    public String getPickuptime() {
//        return pickuptime;
//    }
//
//    public String getReturndate() {
//        return returndate;
//    }
//
//    public String getReturntime() {
//        return returntime;
//    }
//
//    public String getMovingstartlocation() {
//        return movingstartlocation;
//    }
//
//    public String getMovingendlocation() {
//        return movingendlocation;
//    }
//
//    public String getArea() {
//        return area;
//    }
//
//    public String getKmdriven() {
//        return kmdriven;
//    }
//
//    public String getNumberofmovers() {
//        return numberofmovers;
//    }
//
//    public String getNumberofboxes() {
//        return numberofboxes;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setRate(int rate) {
//        this.rate = rate;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public void setDob(String dob) {
//        this.dob = dob;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setG1(String g1) {
//        G1 = g1;
//    }
//
//    public void setG2(String g2) {
//        G2 = g2;
//    }
//
//    public void setG3(String g3) {
//        G3 = g3;
//    }
//
//    public void setCompact(String compact) {
//        this.compact = compact;
//    }
//
//    public void setIntermediate(String intermediate) {
//        this.intermediate = intermediate;
//    }
//
//    public void setSUV(String SUV) {
//        this.SUV = SUV;
//    }
//
//    public void setPickupdate(String pickupdate) {
//        this.pickupdate = pickupdate;
//    }
//
//    public void setPickuptime(String pickuptime) {
//        this.pickuptime = pickuptime;
//    }
//
//    public void setReturndate(String returndate) {
//        this.returndate = returndate;
//    }
//
//    public void setReturntime(String returntime) {
//        this.returntime = returntime;
//    }
//
//    public void setMovingstartlocation(String movingstartlocation) {
//        this.movingstartlocation = movingstartlocation;
//    }
//
//    public void setMovingendlocation(String movingendlocation) {
//        this.movingendlocation = movingendlocation;
//    }
//
//    public void setArea(String area) {
//        this.area = area;
//    }
//
//    public void setKmdriven(String kmdriven) {
//        this.kmdriven = kmdriven;
//    }
//
//    public void setNumberofmovers(String numberofmovers) {
//        this.numberofmovers = numberofmovers;
//    }
//
//    public void setNumberofboxes(String numberofboxes) {
//        this.numberofboxes = numberofboxes;
//    }
}