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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;


public class EmployeeActivity extends AppCompatActivity {

    private EditText eTnumAddressEmployee, eTstreetAddressEmployee, eTPhoneNumber;
    Button btnComplete;
    DatabaseReference dbEmployeeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        eTnumAddressEmployee = findViewById(R.id.numAddressEmployee);
        eTstreetAddressEmployee = findViewById(R.id.streetAddressEmployee);
        eTPhoneNumber = findViewById(R.id.phoneEmployee);
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
                 }if (eTPhoneNumber.getText().toString().isEmpty()){
                    eTPhoneNumber.setError("Please enter a phone number!");
                    eTPhoneNumber.requestFocus();
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

        String id = dbEmployeeUser.push().getKey(); //

        ProfileInfo pi = new ProfileInfo(num,street,phoneNum, id);
        dbEmployeeUser.push().setValue(pi);



        Intent intent = new Intent(getApplicationContext(), EmployeeProfile.class);
        intent.putExtra("branchID",id);
        startActivity(intent);
        Toast.makeText(this,"Profile Completed!", Toast.LENGTH_SHORT).show();
    }


}