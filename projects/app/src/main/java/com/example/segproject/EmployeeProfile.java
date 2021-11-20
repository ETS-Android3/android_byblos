package com.example.segproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class EmployeeProfile extends AppCompatActivity {


    Button addService;
    Button deleteService;
    ListView branchService;
    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("branch");;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);
        String id = getIntent().getStringExtra("branchID");
        //Toast.makeText(getApplicationContext(), "welcome ." + id , Toast.LENGTH_SHORT).show();

        final TextView addressEmployeeBanner = (TextView) findViewById(R.id.addressEmployeeBanner);
        final TextView phoneNumberEmployeeBanner = (TextView) findViewById(R.id.phoneNumberEmployeeBanner);
//        addService = findViewById(R.id.add);
//        deleteService = findViewById(R.id.delete);
//        branchService = findViewById(R.id.branchServiceListView);

        dbref.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProfileInfo profile = snapshot.getValue(ProfileInfo.class);

                if(profile != null){
                    //int addressNum = profile.locationNum;
                    String addressName = " " + profile.locationStreet;
                    String phoneNumber = profile.locationPhoneNum;
                    addressEmployeeBanner.setText("Address: " + addressName);
                    phoneNumberEmployeeBanner.setText("Phone number: " + phoneNumber);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
               // Toast.makeText(EmployeeProfile.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }



}