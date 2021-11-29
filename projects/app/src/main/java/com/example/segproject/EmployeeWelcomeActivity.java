package com.example.segproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeWelcomeActivity extends AppCompatActivity {


    String id;
    String role;
    DatabaseReference dbUser = FirebaseDatabase.getInstance().getReference("users");
    DatabaseReference dbWorkingHours = FirebaseDatabase.getInstance().getReference("hours");
    Button btnProfile;
    Button empLogout;
    String branchID;
    String hoursid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_welcome);
        btnProfile = findViewById(R.id.profileButton);
        empLogout = findViewById(R.id.empLogoutButton);
        id = getIntent().getStringExtra("id");

        final TextView roleTextView = (TextView) findViewById(R.id.roleTextView);
        final TextView nameTextView = (TextView) findViewById(R.id.nameTextView);

        dbWorkingHours.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot info : snapshot.getChildren()){
                    if (info.child("employeeID").getValue().equals(id)){
                        hoursid = info.child("hoursID").getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        Toast.makeText(getApplicationContext(), "welcome ." + id , Toast.LENGTH_SHORT).show();

        empLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeWelcomeActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
            }
        });

        dbUser.child(id).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null){
                    String name = userProfile.username;
                    nameTextView.setText("Welcome, " + name + "!");
                    role = userProfile.role;
                    roleTextView.setText("Your role is " + role);
                    branchID = userProfile.branchID;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(!branchID.equals("")){
                    profile();
                }
                else if(role.equals("Employee")) {
                    completeProfile();
                }
            }
        });
    }


    public void completeProfile(){
        Intent intent = new Intent(getApplicationContext(), EmployeeActivity.class); // if able to sign in send to welcome page.
        intent.putExtra("id", id);
        intent.putExtra("hoursid",hoursid);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Complete profile", Toast.LENGTH_SHORT).show();
    }

    public void profile(){
        Intent intent = new Intent(getApplicationContext(), EmployeeProfile.class); // if able to sign in send to welcome page.
        intent.putExtra("id", id);
        intent.putExtra("branchID", branchID);
        intent.putExtra("hoursid",hoursid);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Redirecting to profile", Toast.LENGTH_SHORT).show();
    }
}