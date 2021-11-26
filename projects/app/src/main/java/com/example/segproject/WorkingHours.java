package com.example.segproject;

public class WorkingHours {
    String employeeID; //employee id associated with working hours.
    String branchID;
    String hoursID;
    boolean mon;
    boolean tues;
    boolean wed;
    boolean thu;
    boolean fri;

    public WorkingHours(){}

    public WorkingHours(String employeeID, String branchID, String hoursID, boolean mon, boolean tues, boolean wed,boolean thu, boolean fri ) {
        this.employeeID = employeeID;
        this.branchID = branchID;
        this.hoursID = hoursID;
        this.mon = mon;
        this.tues = tues;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getBranchID() {
        return branchID;
    }

    public boolean isMon() {
        return mon;
    }

    public boolean isTues() {
        return tues;
    }

    public boolean isWed() {
        return wed;
    }

    public boolean isThu() {
        return thu;
    }

    public boolean isFri() {
        return fri;
    }

    public String getHoursID() {
        return hoursID;
    }
}
