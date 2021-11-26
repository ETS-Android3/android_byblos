package com.example.segproject;

public class WorkingHours {
    String employeeID; //employee id associated with working hours.
    String branchID;
    boolean mon;
    boolean tues;
    boolean wed;
    boolean thu;
    boolean fri;

    public WorkingHours(){}

    public WorkingHours(String employeeID, String branchID, boolean mon, boolean tues, boolean wed,boolean thu, boolean fri ) {
        this.employeeID = employeeID;
        this.branchID = branchID;
        this.mon = mon;
        this.tues = tues;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
    }
    String getEmployeeID(){return employeeID;}
    String getBranchID(){return branchID;}
    boolean getMon(){return mon;}
    boolean getTues(){return tues;}
    boolean getWed(){return wed;}
    boolean getThu(){return thu;}
    boolean getFri(){return fri;}

}
