package com.example.segproject;

public class User {
    public String username, email, password, role;
    public String userID;
    public String branchID;

    public User (){
    }
    public User(String username, String email, String password, String role, String id, String branchID){
        this.userID = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.branchID = branchID;
    }
    //getters
    public String getUserID(){return userID;}

    public String getBranchID() {
        return branchID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }

    public String getPassword(){return password;}
    public String getUsername(){
        return username;
    }
    public String getEmail(){
        return email;
    }
    public String getRole(){
        return role;
    }
}
