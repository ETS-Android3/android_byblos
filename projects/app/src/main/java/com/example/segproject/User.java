package com.example.segproject;

public class User {
    public String username, email, password, role;

    public User (){
    }
    public User(String username, String email, String password, String role){
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    //getters
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
