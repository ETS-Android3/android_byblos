package com.example.segproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
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
    Button empLogout;
    Button viewHours;
    Button viewServices;
    ListView branchServiceListView;

    List<NewService> branchServiceList; // stores list of global services associated with branch (branch associated with a user)



    DatabaseReference dbBranchRef;
    DatabaseReference dbGlobServ;
    DatabaseReference dbUser;
    DatabaseReference dbRequests;
    DatabaseReference dbFeedback;
    DatabaseReference dbWorkinghours;


    ArrayList<String> branchServicesNamesList;

    Button serviceRequests;
    Button acceptedRequests;
    Button rejectedRequests;


    String userid;
    String hoursID;
    String temp = "";

    double rate = 0.0;
    int counter = 0;
    TextView avgRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);


        dbBranchRef = FirebaseDatabase.getInstance().getReference("branch"); // get reference to branches
        dbGlobServ = FirebaseDatabase.getInstance().getReference("GlobalService"); // get reference to Global services
        dbUser = FirebaseDatabase.getInstance().getReference("users"); // get reference to users.
        dbRequests= FirebaseDatabase.getInstance().getReference("ServiceRequests"); // get reference to users.
        dbFeedback= FirebaseDatabase.getInstance().getReference("feedback");


        branchID = getIntent().getStringExtra("branchID"); //branch id
        userid = getIntent().getStringExtra("id"); // user id.
        hoursID = getIntent().getStringExtra("hoursid");
        if (hoursID == null){ // if hours id is not passed through activities then get it manually.
            dbWorkinghours = FirebaseDatabase.getInstance().getReference("hours");
            dbWorkinghours.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot info : snapshot.getChildren()){
                        if (info.child("employeeID").getValue().equals(userid)){
                            hoursID = info.child("hoursID").getValue().toString();
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

        TextView addressEBanner = (TextView) findViewById(R.id.addressEmployeeBanner);
        TextView phoneNumberEBanner = (TextView) findViewById(R.id.phoneNumberEmployeeBanner);

        empLogout = findViewById(R.id.empLogOutBTN);
        viewHours = findViewById(R.id.empHoursBTN);
        serviceRequests = findViewById(R.id.serviceRequests);
        acceptedRequests = findViewById(R.id.acceptedRequests);
        rejectedRequests = findViewById(R.id.rejectedRequests);
        viewServices = findViewById(R.id.viewServices);
        addService = findViewById(R.id.add);
        branchServiceListView = findViewById(R.id.branchServiceListView);
        branchServiceList = new ArrayList<>();

        branchServicesNamesList = new ArrayList<>();

        // feedback
        avgRate = findViewById(R.id.avgRating);

        viewServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeProfile.this,ViewBranchServices.class);
                intent.putExtra("branchID",branchID);
                intent.putExtra("id",userid);
                intent.putExtra("hoursid", hoursID);
                startActivity(intent);
                Toast.makeText(EmployeeProfile.this, "Viewing services", Toast.LENGTH_LONG).show();
            }
        });

        serviceRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeProfile.this,BranchRequests.class);
                intent.putExtra("branchID",branchID);
                intent.putExtra("id",userid);
                intent.putExtra("hoursid", hoursID);
                startActivity(intent);
                Toast.makeText(EmployeeProfile.this, "Viewing service requests", Toast.LENGTH_LONG).show();
            }
        });

        acceptedRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeProfile.this,AcceptedServiceRequests.class);
                intent.putExtra("branchID",branchID);
                intent.putExtra("id",userid);
                intent.putExtra("hoursid", hoursID);
                startActivity(intent);
                Toast.makeText(EmployeeProfile.this, "Viewing accepted service requests", Toast.LENGTH_LONG).show();
            }
        });

       rejectedRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeProfile.this,RejectedServiceRequests.class);
                intent.putExtra("branchID",branchID);
                intent.putExtra("id",userid);
                intent.putExtra("hoursid", hoursID);
                startActivity(intent);
                Toast.makeText(EmployeeProfile.this, "Viewing rejected service requests", Toast.LENGTH_LONG).show();
            }
        });

        viewHours.setOnClickListener(new View.OnClickListener() { // go to hours page.
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeProfile.this,EmpWorkingHours.class);
                intent.putExtra("branchID",branchID);
                intent.putExtra("id",userid);
                intent.putExtra("hoursid",hoursID);
                startActivity(intent);
                Toast.makeText(EmployeeProfile.this, "Viewing hours", Toast.LENGTH_LONG).show();
            }
        });


        empLogout.setOnClickListener(new View.OnClickListener() { // listen for logout.
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeProfile.this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
            }
        });

        addService.setOnClickListener(new View.OnClickListener() { // listen for add service button click.
            @Override
            public void onClick(View view) {
                openAddBranchService();
            }

        });



// use singleValueEvent since we only set branchID once.
        dbUser.child(userid).addListenerForSingleValueEvent(new ValueEventListener() { // sets user's branch id.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    String role = userProfile.getRole();
                    if(role.equals("Employee")){
                        temp = temp + branchID;
                        dbUser.child(userid).child("branchID").setValue(temp);
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

                    addressEBanner.setText("Address: " + addressNum + " " + addressName + ", " +
                            city + ", " + state + ", " + country + ", " + zip);
                    phoneNumberEBanner.setText("Phone number: " + phoneNumber);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EmployeeProfile.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        dbFeedback.addValueEventListener(new ValueEventListener() { // outputs the services offered at the branch in listview.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot info : snapshot.getChildren()) { // iterate through all global services and check if
                    Feedback fb = info.getValue(Feedback.class);

                    if (fb != null) {
                        if (fb.getBranchID().equals(branchID)){
                            rate = rate + (double) fb.getRating();
                            counter++;
                        }
                    }
                }
                rate = rate / counter;
                if(Double.isNaN(rate)){
                    avgRate.setText("No ratings");
                }else{
                    avgRate.setText("Average rating: " + new DecimalFormat("##.#").format(rate));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EmployeeProfile.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openAddBranchService(){
        Intent intent = new Intent(this,AddBranchService.class);
        intent.putExtra("branchID",branchID);
        intent.putExtra("id",userid);
        intent.putExtra("hoursid", hoursID);
        startActivity(intent);
        Toast.makeText(EmployeeProfile.this, "Adding branch service", Toast.LENGTH_LONG).show();
    }

}