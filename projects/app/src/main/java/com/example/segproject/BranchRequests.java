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

import java.util.ArrayList;
import java.util.List;

public class BranchRequests extends AppCompatActivity {

    String requests;
    String[] branchRequests;
    DatabaseReference dbBranchRef;
    DatabaseReference dbRequests;
    String branchID;
    String userid;
    String hoursID;
    ListView branchRequestsListView;
    List<ServiceRequest> branchRequestsServiceList;
    String acceptedRequests;
    Button backButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branch_requests);

        //rename db refs
        dbBranchRef = FirebaseDatabase.getInstance().getReference("branch"); // get reference to branches
        dbRequests = FirebaseDatabase.getInstance().getReference("ServiceRequests");

        backButton = findViewById(R.id.branchRequestsBackBTN);

        branchID = getIntent().getStringExtra("branchID"); //branch id
        userid = getIntent().getStringExtra("id"); // user id
        hoursID = getIntent().getStringExtra("hoursid");

        branchRequestsListView = findViewById(R.id.branchRequestList);
        branchRequestsServiceList = new ArrayList<>();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchRequests.this,EmployeeProfile.class);
                intent.putExtra("branchID",branchID);
                intent.putExtra("id",userid);
                intent.putExtra("hoursid", hoursID);
                startActivity(intent);
            }
        });
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
        TextView custName = dialogView.findViewById(R.id.acceptName);
        TextView DOB = dialogView.findViewById(R.id.acceptDOBTV);
        TextView addy = dialogView.findViewById(R.id.acceptAddyTV);
        TextView email = dialogView.findViewById(R.id.acceptEmailTV);
        TextView license = dialogView.findViewById(R.id.acceptLicenseTV);
        TextView carPref = dialogView.findViewById(R.id.acceptCartypeTV);
        TextView pickDate = dialogView.findViewById(R.id.acceptPickDateTV);
        TextView pickTime = dialogView.findViewById(R.id.acceptPickTimeTV);
        TextView retDate = dialogView.findViewById(R.id.acceptReturnDateTV);
        TextView retTime = dialogView.findViewById(R.id.acceptReturnTimeTV);
        TextView startLoc = dialogView.findViewById(R.id.acceptStartLocationTV);
        TextView endLoc = dialogView.findViewById(R.id.acceptEndLocTV);
        TextView area = dialogView.findViewById(R.id.acceptAreaTV);
        TextView kmDriven = dialogView.findViewById(R.id.acceptKMTV);
        TextView numMovers = dialogView.findViewById(R.id.acceptNumMoversTV);
        TextView custNameNumBoxes = dialogView.findViewById(R.id.acceptNumBoxesTV);


        dbRequests.child(requestID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ServiceRequest servReq = snapshot.getValue(ServiceRequest.class);

                if (servReq != null){
                    custName.setText("Customer Name: " + servReq.getFirstName() + " " + servReq.getLastName());
                    DOB.setText("Date of Birth: " + servReq.getDob());
                    addy.setText("Address: " + servReq.getAddress());
                    email.setText("Email: " + servReq.getEmail());

                    //License
                    if (servReq.isG1())
                        license.setText("License: G1");
                    else if (servReq.isG2())
                        license.setText("License: G2");
                    else if (servReq.isG3())
                        license.setText("License: G3");
                    else
                        license.setVisibility(View.GONE);

                    //car type
                    if (servReq.isCompact())
                        carPref.setText("Car Type: Compact");
                    else if (servReq.isIntermediate())
                        carPref.setText("Car Type: Intermediate");
                    else if (servReq.isSUV())
                        carPref.setText("Car Type: SUV");
                    else
                        carPref.setVisibility(View.GONE);

                    //pickup date
                    if (servReq.getPickupdate().equals(""))
                        pickDate.setVisibility(View.GONE);
                    else
                        pickDate.setText("Pickup Date: " + servReq.getPickupdate());
                    //pickup time
                    if (servReq.getPickuptime().equals(""))
                        pickTime.setVisibility(View.GONE);
                    else
                        pickTime.setText("Pickup Time: " + servReq.getPickuptime());
                    //return date
                    if (servReq.getReturndate().equals(""))
                        retDate.setVisibility(View.GONE);
                    else
                        retDate.setText("Return Date: " + servReq.getReturndate());
                    //return time
                    if (servReq.getReturntime().equals(""))
                        retTime.setVisibility(View.GONE);
                    else
                        retTime.setText("Return Time: " + servReq.getReturntime());

                    //start location
                    if (servReq.getMovingstartlocation().equals(""))
                        startLoc.setVisibility(View.GONE);
                    else
                        startLoc.setText("Moving Start Location: " + servReq.getMovingstartlocation());
                    if (servReq.getMovingendlocation().equals(""))
                        endLoc.setVisibility(View.GONE);
                    else
                        endLoc.setText("Moving End Location: " + servReq.getMovingendlocation());
                    //area
                    if (servReq.getArea().equals(""))
                        area.setVisibility(View.GONE);
                    else
                        area.setText("Area: " + servReq.getArea());
                    if (servReq.getKmdriven().equals(""))
                        kmDriven.setVisibility(View.GONE);
                    else
                        kmDriven.setText("KM Driven: " + servReq.getKmdriven());

                    //num movers
                    if (servReq.getNumberofmovers().equals(""))
                        numMovers.setVisibility(View.GONE);
                    else
                        numMovers.setText("Number of Movers: " + servReq.getNumberofmovers());
                    //num boxes
                    if (servReq.getNumberofboxes().equals(""))
                        custNameNumBoxes.setVisibility(View.GONE);
                    else
                        custNameNumBoxes.setText("Number of Boxes: " + servReq.getNumberofboxes());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
