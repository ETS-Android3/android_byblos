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

import java.util.List;

public class EmployeeProfile extends AppCompatActivity {

    String uid;
    String addressName;
    String phoneNumber;
    Button addService;
    Button deleteService;
    ListView branchService;
    DatabaseReference dbref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        dbref = FirebaseDatabase.getInstance().getReference("branch");
        uid = getIntent().getStringExtra("branchID");
        Toast.makeText(getApplicationContext(), "welcome ." + uid , Toast.LENGTH_SHORT).show();

        final TextView addressEBanner = (TextView) findViewById(R.id.addressEmployeeBanner);
        final TextView phoneNumberEBanner = (TextView) findViewById(R.id.phoneNumberEmployeeBanner);
        addService = findViewById(R.id.add);
        deleteService = findViewById(R.id.delete);
        branchService = findViewById(R.id.branchServiceListView);

        dbref.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProfileInfo profile = snapshot.getValue(ProfileInfo.class);

                if(profile != null){

                    int addressNum = profile.streetNum;
                    addressName = " " + profile.streetName;
                    phoneNumber = profile.phoneNum;
                    addressEBanner.setText("Address: " + addressNum + " " + addressName);
                    phoneNumberEBanner.setText("Phone number: " + phoneNumber);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(EmployeeProfile.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }



}