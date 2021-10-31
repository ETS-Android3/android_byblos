package com.example.segproject;

public class User {
    public String username, email, password, role;
    public String userID;

    public User (){
    }
    public User(String username, String email, String password, String role, String id){
        this.userID = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    //getters
    public String getUserID(){return userID;}

//    public void setUserID(String id){
//        userID= id;
//    }

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
