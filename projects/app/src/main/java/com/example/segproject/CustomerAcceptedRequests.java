package com.example.segproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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

public class CustomerAcceptedRequests extends AppCompatActivity {

    String acceptedRequests;
    String[] acceptedRequestsList;
    DatabaseReference dbBranchRef;
    DatabaseReference dbRequests;
    String branchID;
    String userid;
    String username;
    ListView branchAcceptedRequestsListView;
    List<ServiceRequest> branchAcceptedRequestsServiceList;
    Button backButton;
    String serviceid;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_accepted_services);

        //rename db refs
        dbBranchRef = FirebaseDatabase.getInstance().getReference("branch"); // get reference to branches
        dbRequests = FirebaseDatabase.getInstance().getReference("ServiceRequests");
        branchID = getIntent().getStringExtra("branchID"); //branch id
        userid = getIntent().getStringExtra("id"); // user id
        username = getIntent().getStringExtra("username");
        backButton = findViewById(R.id.custAcceptedServicesBackBTN);
        branchAcceptedRequestsListView = findViewById(R.id.customerAcceptedRequests);
        branchAcceptedRequestsServiceList = new ArrayList<>();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerAcceptedRequests.this, BranchDisplay.class);
                intent.putExtra("branchID",branchID);
                intent.putExtra("id",userid);
                intent.putExtra("username", username);
                startActivity(intent);
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
                    acceptedRequests = profile.getAcceptedRequests();
                    acceptedRequestsList = acceptedRequests.split(", ");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CustomerAcceptedRequests.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });


        dbRequests.addValueEventListener(new ValueEventListener() { // outputs the services offered at the branch in listview.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchAcceptedRequestsServiceList.clear();

                for (DataSnapshot info : snapshot.getChildren()) { // iterate through all global services and check if
                    ServiceRequest sr = info.getValue(ServiceRequest.class);

                    if (sr != null) {
                        if(acceptedRequestsList != null){ // look here
                            for (String s : acceptedRequestsList) {
                                if (s.equals(sr.getRequestID()) && userid.equals(sr.getUserID()) && branchID.equals(sr.getBranchID())) {
                                    branchAcceptedRequestsServiceList.add(sr);
                                }
                            }
                        }
                    }
                }

                ServiceRequestList branchAcceptedRequestAdapter = new ServiceRequestList(CustomerAcceptedRequests.this, branchAcceptedRequestsServiceList);
                branchAcceptedRequestsListView.setAdapter(branchAcceptedRequestAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CustomerAcceptedRequests.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
