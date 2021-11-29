package com.example.segproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.InputType;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;


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

        // submit request button redirects to branch profile page if fields are validated
        submitRequest = (Button) findViewById(R.id.submitRequestButton);
        submitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //submitNewRequest();
                validate();
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
//        //send form info to database
//        firstName = tfirstName.getText().toString();
//        lastName = tlastName.getText().toString();
//        dob = tdob.getText().toString();
//        address = taddress.getText().toString();
//        email = temail.getText().toString();
//        g1 = rG1.isChecked();
//        g2 = rG2.isChecked();
//        g3 = rG3.isChecked();
//        compact = rcompact.isChecked();
//        intermediate = rintermediate.isChecked();
//        suv = rSUV.isChecked();
//        pickupdate = tpickupdate.getText().toString();
//        pickuptime = tpickuptime.getText().toString();
//        returndate = treturndate.getText().toString();
//        returntime = treturntime.getText().toString();
//        movingstartlocation = tmovingstartlocation.getText().toString();
//        movingendlocation = tmovingendlocation.getText().toString();
//        area = tarea.getText().toString();
//        kmdriven = tkmdriven.getText().toString();
//        numberofmovers = tnumberofmovers.getText().toString();
//        numberofboxes = tnumberofboxes.getText().toString();

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

    // validation method
    private void validate(){

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

        boolean valid = true;

        // name field validation
        if (firstName.trim().isEmpty()) {
            tfirstName.setError("Please enter a name!");
            tfirstName.requestFocus();
            valid = false;
        } if (lastName.trim().isEmpty()) {
            tlastName.setError("Please enter a name!");
            tlastName.requestFocus();
            valid = false;
        }
        // dob field validation
        int type1 = tdob.getInputType();
        if ((type1 != (InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE)) || (dob.trim().isEmpty())){
            tdob.setError("Please enter a date!");
            tdob.requestFocus();
            valid = false;
        }
        // address field validation
        if (address.trim().isEmpty()) {
            taddress.setError("Please enter an address!");
            taddress.requestFocus();
            valid = false;
        }
        // email field validation
        if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() || (email.trim().isEmpty())){
            temail.setError("Not a valid email!");
            temail.requestFocus();
            valid = false;
        }
        // license field validation
        if (tlicenseType.getVisibility() == View.VISIBLE){
            if (!(g1 || g2 || g3)){
                rG1.setError("License type required!");
                rG1.requestFocus();
                valid = false;
            }
        }
        // car type field validation
        if (tcarType.getVisibility() == View.VISIBLE){
            if (!(suv || compact || intermediate)){
                rcompact.setError("Car type required!");
                rcompact.requestFocus();
                valid = false;
            }
        }
        // pickup/return field validation
        if (tpickupdate.getVisibility() == View.VISIBLE){
            int type2 = tpickupdate.getInputType();
            if ((type2 != (InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE)) || (pickupdate.trim().isEmpty())) {
                tpickupdate.setError("Please enter a valid date!");
                tpickupdate.requestFocus();
                valid = false;
            }
        } if (tpickuptime.getVisibility() == View.VISIBLE) {
            if (!pickuptime.trim().matches("[0-9]+(\\.){0,1}[0-9]*") || (pickuptime.trim().isEmpty())) {
                tpickuptime.setError("Please enter a valid time!");
                tpickuptime.requestFocus();
                valid = false;
            }
        } if (treturndate.getVisibility() == View.VISIBLE){
            int type3 = treturndate.getInputType();
            if ((type3 != (InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE)) || (returndate.trim().isEmpty())) {
                treturndate.setError("Please enter a valid date!");
                treturndate.requestFocus();
                valid = false;
            }
        } if (treturntime.getVisibility() == View.VISIBLE) {
            if (!returntime.trim().matches("[0-9]+(\\.){0,1}[0-9]*") || (returntime.trim().isEmpty())) {
                treturntime.setError("Please enter a valid time!");
                treturntime.requestFocus();
                valid = false;
            }
        }
        // moving info field validation
        if (tmovingstartlocation.getVisibility() == View.VISIBLE){
            if (movingstartlocation.trim().isEmpty()) {
                tmovingstartlocation.setError("Please enter a location!");
                tmovingstartlocation.requestFocus();
                valid = false;
            }
        }
        if (tmovingendlocation.getVisibility() == View.VISIBLE) {
            if (movingendlocation.trim().isEmpty()) {
                tmovingendlocation.setError("Please enter a location!");
                tmovingendlocation.requestFocus();
                valid = false;
            }
        }
        // miscellaneous field validation
        if (tarea.getVisibility() == View.VISIBLE){
            if (area.trim().isEmpty()) {
                tarea.setError("Please enter an area!");
                tarea.requestFocus();
                valid = false;
            }
        }
        if (tkmdriven.getVisibility() == View.VISIBLE){
            if (!kmdriven.trim().matches("[0-9]+(\\.){0,1}[0-9]*") || (kmdriven.trim().isEmpty())) {
                tkmdriven.setError("Please enter a number!");
                tkmdriven.requestFocus();
                valid = false;
            }
        }
        if (tnumberofmovers.getVisibility() == View.VISIBLE){
            if (!numberofmovers.trim().matches("[0-9]+(\\.){0,1}[0-9]*") || (numberofmovers.trim().isEmpty())) {
                tnumberofmovers.setError("Please enter a number!");
                tnumberofmovers.requestFocus();
                valid = false;
            }
        }
        if (tnumberofboxes.getVisibility() == View.VISIBLE) {
            if (!numberofboxes.trim().matches("[0-9]+(\\.){0,1}[0-9]*") || (numberofboxes.trim().isEmpty())) {
                tnumberofboxes.setError("Please enter a number!");
                tnumberofboxes.requestFocus();
                valid = false;
            }
        }
        // all fields are valid and request can be submitted
        if (valid){
            Log.d("request","VALID");
            submitNewRequest();
        }
    }



}
