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

public class CustomerRejectedRequests extends AppCompatActivity {

    String rejectedRequests;
    String[] rejectedRequestsList;
    DatabaseReference dbBranchRef;
    DatabaseReference dbRequests;
    String branchID;
    String userid;
    String username;
    ListView branchRejectedRequestsListView;
    List<ServiceRequest> branchRejectedRequestsServiceList;
    Button  goBackCustButton;
    String serviceid;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_rejected_services);

        //rename db refs
        dbBranchRef = FirebaseDatabase.getInstance().getReference("branch"); // get reference to branches
        dbRequests = FirebaseDatabase.getInstance().getReference("ServiceRequests");
        branchID = getIntent().getStringExtra("branchID"); //branch id
        userid = getIntent().getStringExtra("id"); // user id
        username = getIntent().getStringExtra("username");
        goBackCustButton = findViewById(R.id.custRejectedServicesBackBTN);
        branchRejectedRequestsListView = findViewById(R.id.customerRejectedRequests);
        branchRejectedRequestsServiceList = new ArrayList<>();

        goBackCustButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerRejectedRequests.this, BranchDisplay.class);
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
                    rejectedRequests = profile.getRejectedRequests();
                    rejectedRequestsList = rejectedRequests.split(", ");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CustomerRejectedRequests.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });


        dbRequests.addValueEventListener(new ValueEventListener() { // outputs the services offered at the branch in listview.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchRejectedRequestsServiceList.clear();

                for (DataSnapshot info : snapshot.getChildren()) { // iterate through all global services and check if
                    ServiceRequest sr = info.getValue(ServiceRequest.class);

                    if (sr != null) {
                        if(rejectedRequestsList != null){ // look here
                            for (String s : rejectedRequestsList) {
                                if (s.equals(sr.getRequestID()) && userid.equals(sr.getUserID()) && branchID.equals(sr.getBranchID())) {
                                    branchRejectedRequestsServiceList.add(sr);
                                }
                            }
                        }
                    }
                }

                ServiceRequestList branchAcceptedRequestAdapter = new ServiceRequestList(CustomerRejectedRequests.this, branchRejectedRequestsServiceList);
                branchRejectedRequestsListView.setAdapter(branchAcceptedRequestAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CustomerRejectedRequests.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }
}

