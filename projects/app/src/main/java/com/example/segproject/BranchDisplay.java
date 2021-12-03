package com.example.segproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
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
    String hours;
    Button rateUs;
    Button backButton;
    Button logout;
    Button acceptedServices;
    Button viewRejectedServices;
    ListView branchServiceListView;

    List<NewService> branchServiceList; // stores list of global services associated with branch (branch associated with a user)

    DatabaseReference dbBranchRef;
    DatabaseReference dbGlobServ;
    DatabaseReference dbUser;
    DatabaseReference dbFeedback;
    String services;
    String[] branchServices;
    String acceptedRequests;
    String[] acceptedRequestsList;

    String userid;
    String username;
    String hoursID;
    String temp = "";

    String serviceID;
    String name;

    TextView customerRating;
    TextView customerComment;
    TextView avgRate;

    double rate = 0.0;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_branch_display);

        //rename db refs
        dbBranchRef = FirebaseDatabase.getInstance().getReference("branch"); // get reference to branches
        dbGlobServ = FirebaseDatabase.getInstance().getReference("GlobalService"); // get reference to Global services
        dbUser = FirebaseDatabase.getInstance().getReference("users"); // get reference to users.
        dbFeedback = FirebaseDatabase.getInstance().getReference("feedback");

        branchID = getIntent().getStringExtra("branchID"); //branch id
        userid = getIntent().getStringExtra("id"); // user id
        username = getIntent().getStringExtra("username");
        logout = findViewById(R.id.custLogoutButton2);
        acceptedServices = findViewById(R.id.acceptedServices);
        viewRejectedServices = findViewById(R.id.viewrejectedServiceRequests);

        TextView addressEBanner = (TextView) findViewById(R.id.branchAddress);
        TextView phoneNumberEBanner = (TextView) findViewById(R.id.branchPhoneNumber);
        TextView hoursEBanner = (TextView) findViewById(R.id.branchHours);
        rateUs = findViewById(R.id.rateButton);
        backButton = findViewById(R.id.custBranchDisplayBackBTN);
        TextView customerRating = findViewById(R.id.customerRating);
        TextView customerComment = findViewById(R.id.customerComment);
        avgRate = findViewById(R.id.avgRatingDisplay);

        branchServiceListView = findViewById(R.id.branchServiceList);
        branchServiceList = new ArrayList<>();

        viewRejectedServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BranchDisplay.this, CustomerRejectedRequests.class);
                intent.putExtra("branchID",branchID);
                intent.putExtra("id",userid);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchDisplay.this, CustomerWelcomeActivity.class);
                intent.putExtra("branchID",branchID);
                intent.putExtra("id",userid);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() { // logout button listener.
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BranchDisplay.this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
            }
        });

        acceptedServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), CustomerAcceptedRequests.class);
                intent.putExtra("branchID",branchID);
                intent.putExtra("id",userid);
                intent.putExtra("username", username);
                startActivity(intent);
                Toast.makeText(BranchDisplay.this, "Redirecting to accepted requests", Toast.LENGTH_LONG).show();
            }
        });

        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), FeedbackPage.class);
                intent.putExtra("branchID",branchID);
                intent.putExtra("id",userid);
                intent.putExtra("username", username);
                startActivity(intent);
                Toast.makeText(BranchDisplay.this, "Redirecting to feedback form", Toast.LENGTH_LONG).show();
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
                    hours = profile.getHours();

                    String[] times = hours.split(", ");
                    String hourDisplay = "";
                    for(int i = 0; i < times.length; i++){
                        hourDisplay = hourDisplay + times[i] + ": 9:00 AM - 5:00PM" + "\n";
                    }

                    addressEBanner.setText("Address: " + addressNum + " " + addressName + ", " +
                            city + ", " + state + ", " + country + ", " + zip);
                    phoneNumberEBanner.setText("Phone number: " + phoneNumber);
                    hoursEBanner.setText("Working hours: " + "\n" + hourDisplay + "\nClosed all other days");

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BranchDisplay.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });


        branchServiceListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                NewService serv = branchServiceList.get(position);
                serviceID = serv.getServiceID();
                sendServiceRequestDialog(serviceID);
                return false;
            }
        });

        dbFeedback.addValueEventListener(new ValueEventListener() { // outputs the services offered at the branch in listview.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot info : snapshot.getChildren()) { // iterate through all global services and check if
                    Feedback fb = info.getValue(Feedback.class);
                    if (fb != null) {
                       if(fb.getUserID().equals(userid) && fb.getBranchID().equals(branchID)){ // match user with branch
                               customerRating.setText("" + fb.getRating());
                               customerComment.setText(fb.getComment());

                           if (fb.getBranchID().equals(serviceID)){
                               rate = rate + (double)fb.getRating();
                               counter++;
                           }
                       };
                    }
                }
                NewServiceList branchAdapter = new NewServiceList(BranchDisplay.this, branchServiceList);
                branchServiceListView.setAdapter(branchAdapter);

                rate = rate / counter;

                if(Double.isNaN(rate)){
                    avgRate.setText("No ratings");
                    TextView ratingBanner = findViewById(R.id.yourRatingBanner);
                    ratingBanner.setVisibility(View.GONE);
                    customerRating.setVisibility(View.GONE);
                }else{
                    avgRate.setText("Average rating: " + new DecimalFormat("##.#").format(rate));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BranchDisplay.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sendServiceRequestDialog(String servID){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.send_request, null);
        dialogBuilder.setView(dialogView);

        final Button sendButton = (Button) dialogView.findViewById(R.id.sendRequestButton);

        Button DialogBackButton = dialogView.findViewById(R.id.custSendServiceRequestBackBTN);

        dialogBuilder.setTitle(name);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        DialogBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendServiceRequest(servID);
                b.dismiss();

            }
        });
    }

    private void sendServiceRequest(String serviceID){
        Intent intent = new Intent(this,ServiceRequestForm.class);
        intent.putExtra("serviceID", serviceID);
        intent.putExtra("branchID",branchID);
        intent.putExtra("username", username);
        intent.putExtra("id",userid);
        startActivity(intent);
        Toast.makeText(BranchDisplay.this, "Redirecting to form", Toast.LENGTH_LONG).show();
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
