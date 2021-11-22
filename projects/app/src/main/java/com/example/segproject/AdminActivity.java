package com.example.segproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminActivity extends AppCompatActivity {
//want admin to be able to delete accounts. so have to make a page that lists all the current employees and customers. should separate the two.
// admin can add edit and delete services.

//mDatabase.child("users").child(userId).child("username").setValue(name);
//to update specific elements of a user. ^ this would change the username.

    // when creating service we have to put it in the db.


    DatabaseReference dbUsers;
    DatabaseReference dbServices;

    int numEmployees;
    int numCustomers;
    int numServices ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        dbUsers = FirebaseDatabase.getInstance().getReference("users");
        dbServices = FirebaseDatabase.getInstance().getReference("GlobalService");

    }

    //when admin signs in be able to see number of employees, customers and services.
    protected void onStart(){
        super.onStart();


        dbUsers.addValueEventListener(new ValueEventListener() { // counts number of employees
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                numCustomers = 0;
                numEmployees = 0;
                for (DataSnapshot info : snapshot.getChildren()){
                    if (info.child("role").getValue().equals("Customer")){
                        numCustomers ++;
                    } if (info.child("role").getValue().equals("Employee")){
                        numEmployees ++;
                    }
                }

                final TextView numEmpTextView = (TextView) findViewById(R.id.numEmployeesTextView);
                numEmpTextView.setText("Number of Employees: " + numEmployees);

                final TextView numCustTextView = (TextView) findViewById(R.id.numCustomersTestView);
                numCustTextView.setText("Number of Customers: " + numCustomers);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        dbServices.addValueEventListener(new ValueEventListener() { // counts number of services.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                numServices = 0;
                for (DataSnapshot serv : snapshot.getChildren()){
                    numServices ++;
                }
                final TextView numServTextView = (TextView) findViewById(R.id.numServicesTextView);
                numServTextView.setText("Number of Services: " + numServices);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void editAccountsOnClick(View view){

        startActivity(new Intent(AdminActivity.this, EditAccounts.class));
        Toast.makeText(getApplicationContext(), "Edit Accounts", Toast.LENGTH_SHORT).show();
    }


    public void logoutAdmin(View view){
//        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(AdminActivity.this, MainActivity.class));
        Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
    }

    public void editServicesOnClick(View view){
        startActivity(new Intent(AdminActivity.this, EditService.class));
    }
}

