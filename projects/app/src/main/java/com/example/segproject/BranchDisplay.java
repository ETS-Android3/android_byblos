package com.example.segproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BranchDisplay extends AppCompatActivity {

    String branchID;
    String addressName;
    String phoneNumber;
    String city;
    String state;
    String country;
    String zip;
    Button addService;
    Button empLogout;
    Button viewHours;
    ListView branchServiceListView;

    List<NewService> branchServiceList; // stores list of global services associated with branch (branch associated with a user)


    DatabaseReference dbBranchRef;
    DatabaseReference dbGlobServ;
    DatabaseReference dbUser;
    String services;
    String[] branchServices;


    String userid;
    String hoursID;
    String temp = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_branch_display);

        //rename db refs
        dbBranchRef = FirebaseDatabase.getInstance().getReference("branch"); // get reference to branches
        dbGlobServ = FirebaseDatabase.getInstance().getReference("GlobalService"); // get reference to Global services
        dbUser = FirebaseDatabase.getInstance().getReference("users"); // get reference to users.

        branchID = getIntent().getStringExtra("branchID"); //branch id
        userid = getIntent().getStringExtra("id"); // user id.
        hoursID = getIntent().getStringExtra("hoursid");


        TextView addressEBanner = (TextView) findViewById(R.id.branchAddress);
        TextView phoneNumberEBanner = (TextView) findViewById(R.id.branchPhoneNumber);


        branchServiceListView = findViewById(R.id.branchServiceList);
        branchServiceList = new ArrayList<>();


        viewHours.setOnClickListener(new View.OnClickListener() { // go to hours page.
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.segproject.BranchDisplay.this, EmpWorkingHours.class);
                intent.putExtra("branchID", branchID);
                intent.putExtra("id", userid);
                intent.putExtra("hoursid", hoursID);
                startActivity(intent);
            }
        });

        dbBranchRef.child(branchID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BranchProfile profile = snapshot.getValue(BranchProfile.class);

                if (profile != null) {
                    int addressNum = profile.streetNum;
                    addressName = profile.streetName;
                    city = profile.city;
                    state = profile.state;
                    country = profile.country;
                    zip = profile.zip;
                    phoneNumber = profile.phoneNum;

                    addressEBanner.setText("Address: " + addressNum + " " + addressName + ", " +
                            city + ", " + state + ", " + country + ", " + zip);
                    phoneNumberEBanner.setText("Phone number: " + phoneNumber);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BranchDisplay.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });


    }

    protected void onStart() {//have list of all services
        super.onStart();

        dbBranchRef.child(branchID).addValueEventListener(new ValueEventListener() { // grabs services offered at branch
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BranchProfile profile = snapshot.getValue(BranchProfile.class);
                if (profile != null) {
                    services = profile.getServices();
                    branchServices = services.split(",");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BranchDisplay.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        dbGlobServ.addValueEventListener(new ValueEventListener() { // outputs the services offered at the branch in listview.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchServiceList.clear();

                for (DataSnapshot info : snapshot.getChildren()) { // iterate through all global services and check if
                    NewService ns = info.getValue(NewService.class);

                    if (ns != null) {
                        if (branchServices != null) { // look here
                            for (String s : branchServices) {
                                if (s.equals(ns.getServiceID())) {
                                    branchServiceList.add(ns);
                                }
                            }
                        }
                    }
                }
                NewServiceList branchAdapter = new NewServiceList(BranchDisplay.this, branchServiceList);
                branchServiceListView.setAdapter(branchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BranchDisplay.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

    }
}
