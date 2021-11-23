package com.example.segproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployeeProfile extends AppCompatActivity {

    String branchID;
    String addressName;
    String phoneNumber;
    String city;
    String state;
    String country;
    String zip;
    Button addService;
    ListView branchServiceListView;

    List<NewService> branchServiceList; // stores list of global services associated with branch (branch associated with a user)

    DatabaseReference dbBranchRef;
    DatabaseReference dbGlobServ;
    DatabaseReference dbUser;
    String services;
    String[] individualServices;
    String[] individualServicesRefined;
    String id;
    String temp = "";

//implement a long click listener for delete later.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        //rename db refs
        dbBranchRef = FirebaseDatabase.getInstance().getReference("branch"); // get reference to branches
        dbGlobServ = FirebaseDatabase.getInstance().getReference("GlobalService"); // get reference to Global services
        dbUser = FirebaseDatabase.getInstance().getReference("users"); // get reference to users.

        branchID = getIntent().getStringExtra("branchID"); //branch id
        id = getIntent().getStringExtra("id"); // user id.

//        final TextView addressEBanner = (TextView) findViewById(R.id.addressEmployeeBanner);
//        final TextView phoneNumberEBanner = (TextView) findViewById(R.id.phoneNumberEmployeeBanner);
// removed final

        TextView addressEBanner = (TextView) findViewById(R.id.addressEmployeeBanner);
        TextView phoneNumberEBanner = (TextView) findViewById(R.id.phoneNumberEmployeeBanner);

        addService = findViewById(R.id.add);
        branchServiceListView = findViewById(R.id.branchServiceListView);
        branchServiceList = new ArrayList<>();

        addService.setOnClickListener(new View.OnClickListener() { // listen for add service button click.
            @Override
            public void onClick(View view) {
                openAddBranchService();
            }
        });



        dbUser.child(id).addListenerForSingleValueEvent(new ValueEventListener() { // sets user branch id.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    String role = userProfile.getRole();
                    if(role.equals("Employee")){
                        temp = temp + branchID;
                        dbUser.child(id).child("branchID").setValue(temp);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
//                    services = profile.getServices();

                    addressEBanner.setText("Address: " + addressNum + " " + addressName + ", " +
                            city + ", " + state + ", " + country + ", " + zip);
                    phoneNumberEBanner.setText("Phone number: " + phoneNumber);

//                    individualServices = services.split(",");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EmployeeProfile.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }


    protected void onStart() {//have list of all services
        super.onStart();

        dbBranchRef.child(branchID).addValueEventListener(new ValueEventListener() { //changed from addListenerForSingleValueEvent to addValueEventListener
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BranchProfile profile = snapshot.getValue(BranchProfile.class);
                if (profile != null) {
                    services = profile.getServices();
                    individualServices = services.split(","); // assume this is correct for now
//                    Toast.makeText(EmployeeProfile.this, individualServices[0], Toast.LENGTH_LONG).show(); // test later to see if services being stored correctly
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EmployeeProfile.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });


        dbGlobServ.addValueEventListener(new ValueEventListener() { // grab
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchServiceList.clear();

                for (DataSnapshot info : snapshot.getChildren()) { // iterate through all global services and check if
                    NewService ns = info.getValue(NewService.class);

                    if (ns != null) {
                        if(individualServices!=null){ // look here
                            for (String s : individualServices) {
                                if (s.equals(ns.getServiceID())) {
                                    branchServiceList.add(ns);
                                }
                            }
                        }
                    }
                }
                NewServiceList branchAdapter = new NewServiceList(EmployeeProfile.this, branchServiceList);
                branchServiceListView.setAdapter(branchAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EmployeeProfile.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }

//    protected void onResume() {//have list of all services
//        super.onResume();
//
//        dbBranchRef.child(branchID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                BranchProfile profile = snapshot.getValue(BranchProfile.class);
//                if (profile != null) {
//                    services = profile.getServices();
//                    individualServices = services.split(",");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(EmployeeProfile.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        dbGlobServ.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                branchServiceList.clear();
//
//                for (DataSnapshot info : snapshot.getChildren()) {
//                    NewService ns = info.getValue(NewService.class);
//
//                    if (ns != null) {
//                        if(individualServices!=null){
//                            for (String s : individualServices) {
//                                if (s.equals(ns.getServiceID())) {
//                                    branchServiceList.add(ns);
//                                }
//                            }
//                        }
//                    }
//                }
//                NewServiceList branchAdapter = new NewServiceList(EmployeeProfile.this, branchServiceList);
//                branchServiceListView.setAdapter(branchAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(EmployeeProfile.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    public void openAddBranchService(){

        Intent intent = new Intent(this,AddBranchService.class);
        intent.putExtra("branchID",branchID);
        intent.putExtra("id",id);
        startActivity(intent);

    }

}