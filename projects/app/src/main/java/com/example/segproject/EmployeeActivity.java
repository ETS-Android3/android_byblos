package com.example.segproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;


public class EmployeeActivity extends AppCompatActivity {

    private EditText eTnumAddressEmployee, eTstreetAddressEmployee, eTPhoneNumber, eTCity, eTState, eTCountry, eTZip;
    Button btnComplete;
    String id;
    DatabaseReference dbEmployeeUser;
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
        dbEmployeeUser = FirebaseDatabase.getInstance().getReference().child("branch");

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


        ProfileInfo pi = new ProfileInfo(num,street,phoneNum,branchid,city, state, country, zip,services);
        dbEmployeeUser.child(branchid).setValue(pi);



        Intent intent = new Intent(getApplicationContext(), EmployeeProfile.class);
        intent.putExtra("branchID",branchid);
        intent.putExtra("id",id);
        startActivity(intent);
        Toast.makeText(this,"Profile Completed!", Toast.LENGTH_SHORT).show();

    }


}