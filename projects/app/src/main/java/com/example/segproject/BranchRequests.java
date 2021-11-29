package com.example.segproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BranchRequests extends AppCompatActivity {

    String requests;
    String[] branchRequests;
    DatabaseReference dbBranchRef;
    DatabaseReference dbRequests;
    String branchID;
    String userid;
    ListView branchRequestsListView;
    List<ServiceRequest> branchRequestsServiceList;
    String acceptedRequests;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branch_requests);

        //rename db refs
        dbBranchRef = FirebaseDatabase.getInstance().getReference("branch"); // get reference to branches
        dbRequests = FirebaseDatabase.getInstance().getReference("ServiceRequests");

        branchID = getIntent().getStringExtra("branchID"); //branch id
        userid = getIntent().getStringExtra("id"); // user id

        branchRequestsListView = findViewById(R.id.branchRequestList);
        branchRequestsServiceList = new ArrayList<>();

        // request long click
        branchRequestsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { //listen for long press to see if you want to delete a service.
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ServiceRequest request = branchRequestsServiceList.get(position);

                acceptRequestDialog(request.getServiceName(), request.getRequestID(),position);
                return true;
            }
        });

        dbBranchRef.child(branchID).addValueEventListener(new ValueEventListener() { // grabs services offered at branch
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BranchProfile profile = snapshot.getValue(BranchProfile.class);
                if (profile != null) {
                    requests = profile.getRequests();
                    acceptedRequests = profile.getAcceptedRequests();
                    branchRequests = requests.split(", ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BranchRequests.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

    }



    protected void onStart() {//have list of all services
        super.onStart();

        dbRequests.addValueEventListener(new ValueEventListener() { // outputs the services offered at the branch in listview.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchRequestsServiceList.clear();


                for (DataSnapshot info : snapshot.getChildren()) { // iterate through all global services and check if
                    ServiceRequest sr = info.getValue(ServiceRequest.class);

                    if (sr != null) {
                        if(branchRequests!=null){ // look here
                            for (String s : branchRequests) {
                                if (s.equals(sr.getRequestID())) {
                                    branchRequestsServiceList.add(sr);
                                }
                            }
                        }
                    }
                }
                ServiceRequestList branchRequestAdapter = new ServiceRequestList(BranchRequests.this, branchRequestsServiceList);
                branchRequestsListView.setAdapter(branchRequestAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BranchRequests.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void acceptRequestDialog(String serviceName, String requestID, int position){ // delete service offered at branch
//service id = id of global service we want to delete from branch.

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.accept_request, null);
        dialogBuilder.setView(dialogView);

        final Button buttonAcceptRequest = (Button) dialogView.findViewById(R.id.acceptButton);

        dialogBuilder.setTitle("Accept " + serviceName + " service request?");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonAcceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                acceptRequest(requestID, position);
                b.dismiss();
            }
        });
    }

    private void acceptRequest(String requestID, int position){ // delete service by position.
        branchRequestsServiceList.remove(position);
        String request = "";
        for (ServiceRequest r: branchRequestsServiceList) {
            request = request + ", " + r.getRequestID();
        }


        dbBranchRef.child(branchID).child("requests").setValue(request);// send services to db.
        addAcceptedBranchRequestMethod(requestID);
        onStart();
        Toast.makeText(getApplicationContext(), "Request added", Toast.LENGTH_LONG).show();
    }


    public void addAcceptedBranchRequestMethod(String serviceRequestID){
        acceptedRequests = acceptedRequests + serviceRequestID + ", ";
        dbBranchRef.child(branchID).child("acceptedRequests").setValue(acceptedRequests);

    }
}
