package com.example.segproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddGlobalService extends AppCompatActivity {
    // here the admin will be able to add new services to the database

    DatabaseReference dbServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_global_service);

        DatabaseReference dbServices = FirebaseDatabase.getInstance().getReference("services");
    }



}
