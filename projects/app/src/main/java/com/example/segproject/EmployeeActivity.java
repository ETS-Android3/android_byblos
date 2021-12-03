package com.example.segproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EmployeeActivity extends AppCompatActivity {

    private EditText eTnumAddressEmployee, eTstreetAddressEmployee, eTPhoneNumber, eTCity, eTState, eTCountry, eTZip;

    boolean mon;
    boolean tue;
    boolean wed;
    boolean thurs;
    boolean fri;


    CheckBox monCB, tuesCB, wedCB, thuCB, friCB;
    Button btnComplete;
    String userID;
    String hoursid;
    DatabaseReference dbBranch;
    DatabaseReference dbWorkingHours;
    DatabaseReference dbUser = FirebaseDatabase.getInstance().getReference("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        userID = getIntent().getStringExtra("id");
        hoursid = getIntent().getStringExtra("hoursid");

        eTnumAddressEmployee = findViewById(R.id.numAddressEmployee);
        eTstreetAddressEmployee = findViewById(R.id.streetAddressEmployee);
        eTPhoneNumber = findViewById(R.id.phoneEmployee);
        eTCity = findViewById(R.id.city);
        eTState = findViewById(R.id.stateProvinceRegion);
        eTZip = findViewById(R.id.zipCode);
        eTCountry = findViewById(R.id.country);
        btnComplete = findViewById(R.id.completeButton);

        monCB = (CheckBox) findViewById(R.id.monCheckBox);
        tuesCB = (CheckBox) findViewById(R.id.tueCheckBox);
        wedCB = (CheckBox) findViewById(R.id.wedCheckBox);
        thuCB = (CheckBox) findViewById(R.id.thursCheckBox);
        friCB = (CheckBox) findViewById(R.id.friCheckBox);

//        dbBranch = FirebaseDatabase.getInstance().getReference().child("branch");
        dbBranch = FirebaseDatabase.getInstance().getReference("branch");
        dbWorkingHours = FirebaseDatabase.getInstance().getReference().child("hours");



        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean valid = true;
                if (eTnumAddressEmployee.getText().toString().isEmpty()){
                    eTnumAddressEmployee.setError("Please enter location number!");
                    eTnumAddressEmployee.requestFocus();
                    valid = false;
                }if (eTstreetAddressEmployee.getText().toString().isEmpty()) {
                    eTstreetAddressEmployee.setError("Please enter street name and type!");
                    eTstreetAddressEmployee.requestFocus();
                    valid = false;
                 }if (eTPhoneNumber.getText().toString().isEmpty()) {
                    eTPhoneNumber.setError("Please enter a phone number!");
                    eTPhoneNumber.requestFocus();
                    valid = false;
                }if (!eTPhoneNumber.getText().toString().trim().matches("^[0-9]*$")) {
                    eTPhoneNumber.setError("Phone number must be a number!");
                    eTPhoneNumber.requestFocus();
                    valid = false;
                }if (eTPhoneNumber.getText().toString().trim().length() > 12 || eTPhoneNumber.getText().toString().trim().length() < 8){
                    eTPhoneNumber.setError("Phone number must be between 8 and 12 characters");
                    eTPhoneNumber.requestFocus();
                    valid = false;
                }if (eTCity.getText().toString().isEmpty()){
                    eTCity.setError("Please enter city!");
                    eTCity.requestFocus();
                    valid = false;
                }if (eTState.getText().toString().isEmpty()){
                    eTState.setError("Please enter state, province, region!");
                    eTState.requestFocus();
                    valid = false;
                }if (eTCountry.getText().toString().isEmpty()){
                    eTCountry.setError("Please enter country!");
                    eTCountry.requestFocus();
                    valid = false;
                }if (eTZip.getText().toString().isEmpty()){
                    eTZip.setError("Please enter zip/postal code!");
                    eTZip.requestFocus();
                    valid = false;
                }
                if (eTZip.getText().toString().replaceAll("//s", "").length() != 6){
                    eTZip.setError("Please enter a valid postal code!");
                    eTZip.requestFocus();
                    valid = false;
                }
                if (eTZip.getText().toString().replaceAll("//s", "").length() == 6){ // canadian zip
                    String temp = eTZip.getText().toString();
                    if (!( Character.isLetter(temp.charAt(0)) && Character.isDigit(temp.charAt(1)) && Character.isLetter(temp.charAt(2)) && Character.isDigit(temp.charAt(3)) && Character.isLetter(temp.charAt(4)) && Character.isDigit(temp.charAt(0)) ))
                        valid = false;
                }

                if (valid){
                    completeEmployeeProfile();
                }
            }
         });
    }


    public void completeEmployeeProfile(){

        int num = Integer.parseInt(eTnumAddressEmployee.getText().toString().trim());
        String street = eTstreetAddressEmployee.getText().toString().trim();
        String phoneNum = eTPhoneNumber.getText().toString().trim();
        String city = eTCity.getText().toString().trim();
        String state = eTState.getText().toString().trim();
        String country = eTCountry.getText().toString().trim();
        String zip = eTZip.getText().toString().trim();


        String branchid = dbBranch.push().getKey(); // make unique id for branch
        String hoursID = dbWorkingHours.push().getKey(); // get unique service id.

        String services = "";
        String servicesNames= "";

        //checking which hours are filled in.
        mon = monCB.isChecked();
        tue = tuesCB.isChecked();
        wed = wedCB.isChecked();
        thurs = thuCB.isChecked();
        fri = friCB.isChecked();
        // end of checking which work hours are filled in.
        String hoursString = "";
        hoursString = makeHours(mon,tue,wed,thurs,fri);
        String requests = "";
        String acceptedRequests = "";


        BranchProfile pi = new BranchProfile(num,street,phoneNum,branchid,city, state, country, zip,services, servicesNames, hoursString, requests, acceptedRequests);
        dbBranch.child(branchid).setValue(pi); // store

        WorkingHours hours = new WorkingHours(userID, branchid, hoursID, mon,tue,wed,thurs,fri);
        dbWorkingHours.child(hoursID).setValue(hours);

        Intent intent = new Intent(getApplicationContext(), EmployeeProfile.class);
        intent.putExtra("branchID",branchid);
        intent.putExtra("id", userID);
        intent.putExtra("hoursid", hoursID);
        startActivity(intent);
        Toast.makeText(this,"Profile Completed!", Toast.LENGTH_SHORT).show();
    }

    private String makeHours(boolean mon, boolean tue,boolean wed,boolean thu,boolean fri) {
        String hoursResult = "";

        if (mon)
            hoursResult += "Monday, ";

        if (tue)
            hoursResult += "Tuesday, ";

        if (wed)
            hoursResult += "Wednesday, ";

        if (thu)
            hoursResult += "Thursday, ";

        if (fri)
            hoursResult += "Friday, ";

        hoursResult = hoursResult.replaceAll(", $", "");
        return hoursResult;
    }


}