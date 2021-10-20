package com.example.segproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }
}

//mDatabase.child("users").child(userId).child("username").setValue(name);
//to update specific elements of a user. ^ this would change the username.