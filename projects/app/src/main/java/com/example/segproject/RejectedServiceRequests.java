package com.example.segproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class RejectedServiceRequests extends AppCompatActivity {

    DatabaseReference dbBranchRef;
    DatabaseReference dbRequests;
    String branchID;
    String userid;
    String hoursid;
    Button backBtn;

    ListView branchRejectedRequestsListView;
    List<ServiceRequest> branchRejectedRequestsServiceList;
    String[] branchRejectedRequests;
    String rejectedRequests;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rejected_requests);

        //rename db refs
        dbBranchRef = FirebaseDatabase.getInstance().getReference("branch"); // get reference to branches
        dbRequests = FirebaseDatabase.getInstance().getReference("ServiceRequests");

        branchID = getIntent().getStringExtra("branchID"); //branch id
        userid = getIntent().getStringExtra("id"); // user id
        hoursid = getIntent().getStringExtra("hoursid");;

        backBtn = findViewById(R.id.EmpRejectedRequests);
        branchRejectedRequestsListView = findViewById(R.id.branchRejectedRequestsListView);
        branchRejectedRequestsServiceList= new ArrayList<>();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RejectedServiceRequests.this,EmployeeProfile.class);
                intent.putExtra("branchID",branchID);
                intent.putExtra("id",userid);
                intent.putExtra("hoursid", hoursid);
                startActivity(intent);
                Toast.makeText(RejectedServiceRequests.this, "Back to branch profile", Toast.LENGTH_LONG).show();
            }
        });

        branchRejectedRequestsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { //listen for long press to see if you want to delete a service.
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ServiceRequest request = branchRejectedRequestsServiceList.get(position);

                // acceptRequestDialog(request.getServiceName(), request.getRequestID(),position);
                return true;
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
                    branchRejectedRequests = rejectedRequests.split(", ");

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RejectedServiceRequests.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        dbRequests.addValueEventListener(new ValueEventListener() { // outputs the services offered at the branch in listview.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //branchRequestsServiceList.clear();
                branchRejectedRequestsServiceList.clear();

                for (DataSnapshot info : snapshot.getChildren()) { // iterate through all global services and check if
                    ServiceRequest sr = info.getValue(ServiceRequest.class);

                    if (sr != null) {
                        if(branchRejectedRequests !=null){ // look here
//                            for (String s : branchRequests) {
//                                if (s.equals(sr.getRequestID())) {
//                                    branchRequestsServiceList.add(sr);
//                                }
//                            }

                            for (String s : branchRejectedRequests) {
                                if (s.equals(sr.getRequestID())) {
                                    branchRejectedRequestsServiceList.add(sr);
                                }
                            }
                        }

                    }
                }
//                ServiceRequestList branchRequestAdapter = new ServiceRequestList(EmployeeProfile.this, branchRequestsServiceList);
//                branchRequestsListView.setAdapter(branchRequestAdapter);

                ServiceRequestList branchAcceptedRequestAdapter = new ServiceRequestList(RejectedServiceRequests.this, branchRejectedRequestsServiceList);
                branchRejectedRequestsListView.setAdapter(branchAcceptedRequestAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RejectedServiceRequests.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });


    }
}
