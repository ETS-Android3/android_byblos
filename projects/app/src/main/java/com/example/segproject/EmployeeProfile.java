package com.example.segproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
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
    List<NewService> branchServiceList;
    DatabaseReference dbref;
    DatabaseReference dbserv;
    DatabaseReference dbUser;
    String services;
    String[] individualServices;
    String[] individualServicesRefined;
    String id;
    String temp = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        dbref = FirebaseDatabase.getInstance().getReference("branch");
        dbserv = FirebaseDatabase.getInstance().getReference("GlobalService");
        dbUser = FirebaseDatabase.getInstance().getReference("users");
        branchID = getIntent().getStringExtra("branchID");
        id = getIntent().getStringExtra("id");

        final TextView addressEBanner = (TextView) findViewById(R.id.addressEmployeeBanner);
        final TextView phoneNumberEBanner = (TextView) findViewById(R.id.phoneNumberEmployeeBanner);

        dbUser.child(id).addListenerForSingleValueEvent(new ValueEventListener() {

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

        dbref.child(branchID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProfileInfo profile = snapshot.getValue(ProfileInfo.class);

                if (profile != null) {
                    int addressNum = profile.streetNum;
                    addressName = profile.streetName;
                    city = profile.city;
                    state = profile.state;
                    country = profile.country;
                    zip = profile.zip;
                    phoneNumber = profile.phoneNum;
                    services = profile.getServices();

                    addressEBanner.setText("Address: " + addressNum + " " + addressName + ", " +
                            city + ", " + state + ", " + country + ", " + zip);
                    phoneNumberEBanner.setText("Phone number: " + phoneNumber + services +"P");

                    individualServices = services.split(",");
//                    individualServicesRefined = new String[individualServices.length - 1];
//                    for (int i = 1; i < individualServices.length; i++) {
//                        Arrays.fill(individualServicesRefined, individualServices[i]);
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EmployeeProfile.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        addService = findViewById(R.id.add);
        branchServiceListView = findViewById(R.id.branchServiceListView);
        branchServiceList = new ArrayList<>();

        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddBranchService();
            }
        });
    }


    protected void onStart() {//have list of all services
        super.onStart();
        dbserv.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchServiceList.clear();

                for (DataSnapshot info : snapshot.getChildren()) {
                    NewService ns = info.getValue(NewService.class);

                    if (ns != null) {
                        if(individualServices!=null){
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


    public void openAddBranchService(){

        Intent intent = new Intent(this,AddBranchService.class);
        intent.putExtra("branchID",branchID);
        intent.putExtra("id",id);
        startActivity(intent);

    }


}