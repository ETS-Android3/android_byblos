package com.example.segproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ServiceRequestForm extends AppCompatActivity {
    // service request form where customer can fill out required info to submit a service request

    DatabaseReference dbService;
    DatabaseReference dbRequest;
    DatabaseReference dbBranch;
    DatabaseReference dbUser;

    Button submitRequest;

    TextView tgeneralInfo, tlicenseType, tcarType, tpickupreturn, tmovinginfo, tmiscellaneous;

    EditText tfirstName, tlastName, tdob, taddress, temail, tpickupdate, tpickuptime, treturndate, treturntime, tmovingstartlocation,
            tmovingendlocation, tarea, tkmdriven, tnumberofmovers, tnumberofboxes;
    RadioButton rG1, rG2, rG3, rcompact, rintermediate, rSUV;
    String branchID;
    String servRequestID;
    String serviceID;
    String userID;
    String name;
    double rate;
    String firstName;
    String lastName;
    String dob;
    String address;
    String email;
    boolean g1;
    boolean g2;
    boolean g3;
    boolean compact;
    boolean intermediate;
    boolean suv;
    String pickupdate;
    String pickuptime;
    String returndate;
    String returntime;
    String movingstartlocation;
    String movingendlocation;
    String area;
    String kmdriven;
    String numberofmovers;
    String numberofboxes;
    String username;
    String serviceName;

    String serviceRequest = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_request_form);

        tgeneralInfo = findViewById(R.id.tgeneralInfo);
        tlicenseType = findViewById(R.id.tlicenseType);
        tcarType = findViewById(R.id.tcarType);
        tpickupreturn = findViewById(R.id.tpickupreturn);
        tmovinginfo = findViewById(R.id.tmovinginfo);
        tmiscellaneous = findViewById(R.id.tmiscellaneous);

        tfirstName = findViewById(R.id.tfirstName);
        tlastName = findViewById(R.id.tlastName);
        tdob = findViewById(R.id.tdob);
        taddress = findViewById(R.id.taddress);
        temail = findViewById(R.id.temail);
        tpickupdate = findViewById(R.id.tpickupdate);
        tpickuptime = findViewById(R.id.tpickuptime);
        treturndate = findViewById(R.id.treturndate);
        treturntime = findViewById(R.id.treturntime);
        tmovingstartlocation = findViewById(R.id.tmovingstartlocation);
        tmovingendlocation = findViewById(R.id.tmovingendlocation);
        tarea = findViewById(R.id.tarea);
        tkmdriven = findViewById(R.id.tkmdriven);
        tnumberofmovers = findViewById(R.id.tnumberofmovers);
        tnumberofboxes = findViewById(R.id.tnumberofboxes);

        rG1 = findViewById(R.id.rG1);
        rG2 = findViewById(R.id.rG2);
        rG3 = findViewById(R.id.rG3);
        rcompact = findViewById(R.id.rcompact);
        rintermediate = findViewById(R.id.rintermediate);
        rSUV = findViewById(R.id.rSUV);

        // get service id from previous page
        serviceID = getIntent().getStringExtra("serviceID");
        // get branch id from previous page
        branchID = getIntent().getStringExtra("branchID");
        userID = getIntent().getStringExtra("id");

        dbService = FirebaseDatabase.getInstance().getReference("GlobalService");
        dbRequest = FirebaseDatabase.getInstance().getReference("ServiceRequests");
        dbBranch = FirebaseDatabase.getInstance().getReference("branch");
        dbUser = FirebaseDatabase.getInstance().getReference("users");

        // get userName
        dbUser.child(userID).addValueEventListener(new ValueEventListener() { // grabs services offered at branch
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User up = snapshot.getValue(User.class);
                if (up != null) {
                    username = up.getUsername();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ServiceRequestForm.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        // get serviceName
        dbService.child(serviceID).addValueEventListener(new ValueEventListener() { // grabs services offered at branch
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                NewService ns = snapshot.getValue(NewService.class);
                if (ns != null) {
                    serviceName = ns.getName();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ServiceRequestForm.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });


         //find specific service and set visibility for each attribute
        dbService.child(serviceID).addListenerForSingleValueEvent(new ValueEventListener() { // sets user's branch id.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                NewService ns = snapshot.getValue(NewService.class);
                name = ns.getName();
                rate = ns.getRate();

                // set general info text visibility
                if(!ns.isFirstName() && !ns.isLastName() && !ns.isDob() && !ns.isAddress() && !ns.isEmail()){
                    tgeneralInfo.setVisibility(View.GONE);
                }if (!ns.isFirstName()){
                    tfirstName.setVisibility(View.GONE);
                }if (!ns.isLastName()){
                    tlastName.setVisibility(View.GONE);
                }if (!ns.isDob()){
                    tdob.setVisibility(View.GONE);
                }if (!ns.isAddress()){
                    taddress.setVisibility(View.GONE);
                }if (!ns.isEmail()){
                    temail.setVisibility(View.GONE);
                }

                // set license type text visibility
                if(!ns.isG1() && !ns.isG2() && !ns.isG3()){
                    tlicenseType.setVisibility(View.GONE);
                }if (!ns.isG1()){
                    rG1.setVisibility(View.GONE);
                }if (!ns.isG2()){
                    rG2.setVisibility(View.GONE);
                }if (!ns.isG3()){
                    rG3.setVisibility(View.GONE);
                }

                // set car type text visibility
                if(!ns.isSUV() && !ns.isCompact() && !ns.isIntermediate()){
                    tcarType.setVisibility(View.GONE);
                }if (!ns.isSUV()){
                    rSUV.setVisibility(View.GONE);
                }if (!ns.isCompact()){
                    rcompact.setVisibility(View.GONE);
                }if (!ns.isIntermediate()){
                    rintermediate.setVisibility(View.GONE);
                }

                // set pickup/return info text visibility
                if(!ns.isPickupdate() && !ns.isPickuptime() && !ns.isReturndate() && !ns.isReturntime()){
                    tpickupreturn.setVisibility(View.GONE);
                }if (!ns.isPickupdate()){
                    tpickupdate.setVisibility(View.GONE);
                }if (!ns.isPickuptime()) {
                    tpickuptime.setVisibility(View.GONE);
                }if (!ns.isReturndate()){
                    treturndate.setVisibility(View.GONE);
                }if (!ns.isReturntime()){
                    treturntime.setVisibility(View.GONE);
                }

                // set moving info text visibility
                if(!ns.isMovingstartlocation() && !ns.isMovingendlocation()){
                    tmovinginfo.setVisibility(View.GONE);
                }if (!ns.isMovingstartlocation()){
                    tmovingstartlocation.setVisibility(View.GONE);
                }if (!ns.isMovingendlocation()){
                    tmovingendlocation.setVisibility(View.GONE);
                }

                // set miscellaneous text visibility
                if(!ns.isArea() && !ns.isKmdriven() && !ns.numberofmovers && !ns.isNumberofboxes()){
                    tmiscellaneous.setVisibility(View.GONE);
                }if (!ns.isArea()){
                    tarea.setVisibility(View.GONE);
                }if (!ns.isKmdriven()){
                    tkmdriven.setVisibility(View.GONE);
                }if (!ns.isNumberofmovers()){
                    tnumberofmovers.setVisibility(View.GONE);
                }if (!ns.isNumberofboxes()) {
                    tnumberofboxes.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // submit request button redirects to branch profile page
        submitRequest = (Button) findViewById(R.id.submitRequestButton);
        submitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitNewRequest();
                Toast.makeText(getApplicationContext(), "Request submitted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addBranchRequestMethod(String serviceRequestID){
        serviceRequest = serviceRequest + serviceRequestID + ", ";
        dbBranch.child(branchID).child("requests").setValue(serviceRequest);

    }

    private void submitNewRequest(){
//
        //send form info to database
        firstName = tfirstName.getText().toString();
        lastName = tlastName.getText().toString();
        dob = tdob.getText().toString();
        address = taddress.getText().toString();
        email = temail.getText().toString();
        g1 = rG1.isChecked();
        g2 = rG2.isChecked();
        g3 = rG3.isChecked();
        compact = rcompact.isChecked();
        intermediate = rintermediate.isChecked();
        suv = rSUV.isChecked();
        pickupdate = tpickupdate.getText().toString();
        pickuptime = tpickuptime.getText().toString();
        returndate = treturndate.getText().toString();
        returntime = treturntime.getText().toString();
        movingstartlocation = tmovingstartlocation.getText().toString();
        movingendlocation = tmovingendlocation.getText().toString();
        area = tarea.getText().toString();
        kmdriven = tkmdriven.getText().toString();
        numberofmovers = tnumberofmovers.getText().toString();
        numberofboxes = tnumberofboxes.getText().toString();

        servRequestID = dbRequest.push().getKey(); // get unique service request id.



        ServiceRequest sr = new ServiceRequest(address, area, compact, dob, email, firstName,
                g1, g2, g3, intermediate, kmdriven, lastName, movingendlocation,
                movingstartlocation, name, numberofboxes, numberofmovers,
                pickupdate, pickuptime, rate, returndate, returntime,
                suv, serviceID, branchID, userID, servRequestID, username, serviceName);

        // create service request sorted by service request id
        dbRequest.child(servRequestID).setValue(sr);


        addBranchRequestMethod(servRequestID);

        // go back to branch display
        Intent intent = new Intent(this,BranchDisplay.class);
        intent.putExtra("branchID",branchID);
        intent.putExtra("id",userID);
        startActivity(intent);
        Toast.makeText(ServiceRequestForm.this, "Back to branch profile", Toast.LENGTH_LONG).show();
    }



}
