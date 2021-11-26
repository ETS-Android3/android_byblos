package com.example.segproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.wifi.WpsInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EmployeeActivity extends AppCompatActivity {

    private EditText eTnumAddressEmployee, eTstreetAddressEmployee, eTPhoneNumber, eTCity, eTState, eTCountry, eTZip;

    boolean mon = false;
    boolean tues = false;
    boolean wed = false;
    boolean thu = false;
    boolean fri = false;


    CheckBox monCB, tuesCB, wedCB, thuCB, friCB;
    Button btnComplete;
    String id;
    DatabaseReference dbEmployeeUser;
    DatabaseReference dbWorkingHours;
    DatabaseReference dbUser = FirebaseDatabase.getInstance().getReference("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        id = getIntent().getStringExtra("id");

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

        dbEmployeeUser = FirebaseDatabase.getInstance().getReference().child("branch");
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





//                if (monCB.isChecked())
//                    mon = true;
//                if (tuesCB.isChecked())
//                    tues = true;
//                if (wedCB.isChecked())
//                    wed = true;
//                if (thuCB.isChecked())
//                    thu = true;
//                if (friCB.isChecked())
//                    fri = true;


                if (valid){
                    completeEmployeeProfile();
                }
            }
         });
    }


    public void completeEmployeeProfile(){

        int num = Integer.parseInt(eTnumAddressEmployee.getText().toString());
        String street = eTstreetAddressEmployee.getText().toString();
        String phoneNum = eTPhoneNumber.getText().toString();
        String city = eTCity.getText().toString();
        String state = eTState.getText().toString();
        String country = eTCountry.getText().toString();
        String zip = eTZip.getText().toString();

        String branchid = dbEmployeeUser.push().getKey(); //
        String services = "";


        BranchProfile pi = new BranchProfile(num,street,phoneNum,branchid,city, state, country, zip,services);
        dbEmployeeUser.child(branchid).setValue(pi);

        
        //checking which hours are filled in.
        mon = monCB.isChecked();
        tues = tuesCB.isChecked();
        wed = wedCB.isChecked();
        thu = thuCB.isChecked();
        fri = friCB.isChecked();
// end of checking which work hours are filled in.

        WorkingHours hours = new WorkingHours(id, branchid, mon,tues,wed,thu,fri);
        dbWorkingHours.child(branchid).setValue(hours);


        Intent intent = new Intent(getApplicationContext(), EmployeeProfile.class);
        intent.putExtra("branchID",branchid);
        intent.putExtra("id",id);
        startActivity(intent);
        Toast.makeText(this,"Profile Completed!", Toast.LENGTH_SHORT).show();

    }


}