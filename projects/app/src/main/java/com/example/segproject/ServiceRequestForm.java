package com.example.segproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
    Button submitRequest;

    TextView tgeneralInfo, tlicenseType, tcarType, tpickupreturn, tmovinginfo, tmiscellaneous;

    EditText tfirstName, tlastName, tdob, taddress, temail, tpickupdate, tpickuptime, treturndate, treturntime, tmovingstartlocation,
            tmovingendlocation, tarea, tkmdriven, tnumberofmovers, tnumberofboxes;
    CheckBox cG1, cG2, cG3, ccompact, cintermediate, cSUV;
    String branchID;
    String servRequestID;

    String serviceID;
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

        cG1 = findViewById(R.id.cG1);
        cG2 = findViewById(R.id.cG2);
        cG3 = findViewById(R.id.cG3);
        ccompact = findViewById(R.id.ccompact);
        cintermediate = findViewById(R.id.cintermediate);
        cSUV = findViewById(R.id.cSUV);

        // get service id from previous page
        serviceID = getIntent().getStringExtra("serviceID");
        // get branch id from previous page
        branchID = getIntent().getStringExtra("branchID");

        // find specific service and set visibility for each attribute
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
                    cG1.setVisibility(View.GONE);
                }if (!ns.isG2()){
                    cG2.setVisibility(View.GONE);
                }if (!ns.isG3()){
                    cG3.setVisibility(View.GONE);
                }

                // set car type text visibility
                if(!ns.isSUV() && !ns.isCompact() && !ns.isIntermediate()){
                    tcarType.setVisibility(View.GONE);
                }if (!ns.isSUV()){
                    cSUV.setVisibility(View.GONE);
                }if (!ns.isCompact()){
                    ccompact.setVisibility(View.GONE);
                }if (!ns.isIntermediate()){
                    cintermediate.setVisibility(View.GONE);
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

    private void submitNewRequest(){

        //send form info to database
        firstName = tfirstName.getText().toString();
        lastName = tlastName.getText().toString();
        dob = tdob.getText().toString();
        address = taddress.getText().toString();
        email = temail.getText().toString();
        g1 = cG1.isChecked();
        g2 = cG2.isChecked();
        g3 = cG3.isChecked();
        compact = ccompact.isChecked();
        intermediate = cintermediate.isChecked();
        suv = cSUV.isChecked();
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

        servRequestID = dbService.push().getKey(); // get unique service request id.

        ServiceRequest sr = new ServiceRequest(address, area, compact, dob, email, firstName,
                g1, g2, g3, intermediate, kmdriven, lastName, movingendlocation,
                movingstartlocation, name, numberofboxes, numberofmovers,
                pickupdate, pickuptime, rate, returndate, returntime,
                suv, serviceID, branchID);

        // create service request sorted by service request id
        dbService.child(servRequestID).setValue(sr);

        // go back to branch display
        Intent intent = new Intent(this,BranchDisplay.class);
        startActivity(intent);
    }



}
