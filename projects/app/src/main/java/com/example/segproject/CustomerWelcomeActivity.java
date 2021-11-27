package com.example.segproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class CustomerWelcomeActivity extends AppCompatActivity {

    String userid;
    String username;
    TextView custRole;
    DatabaseReference dbUsers;
    DatabaseReference dbBranches;
    TextView custName;
    Button custLogout;
    Button searchButton;
    EditText searchField;
    RecyclerView searchResult;
    SearchView searchView;
    ArrayList<BranchProfile> branchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_welcome);

        custName = findViewById(R.id.custNameTextView);
        custRole = findViewById(R.id.custRoleTextView);
        searchButton = findViewById(R.id.searchBTN);
        custLogout = findViewById(R.id.custLogoutButton);
        searchField = findViewById(R.id.custSearchField);
        searchResult = findViewById(R.id.searchResult);
        searchView = findViewById(R.id.searchView);

        dbUsers = FirebaseDatabase.getInstance().getReference("users");
        dbBranches = FirebaseDatabase.getInstance().getReference("branch");
        userid = getIntent().getStringExtra("id");
        username = getIntent().getStringExtra("username");

        custName.setText("Welcome, " + username + "!");
        custRole.setText("Your Role is: Customer");



        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        custLogout.setOnClickListener(new View.OnClickListener() { // logout button listener.
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerWelcomeActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (dbBranches != null){

            dbBranches.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        branchList = new ArrayList<>();
                        for(DataSnapshot info : snapshot.getChildren()){
                            branchList.add(info.getValue(BranchProfile.class));

                        }
                        BranchAdapter branchAdapter = new BranchAdapter(branchList);
                        searchResult.setAdapter(branchAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if (searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }
    }

    private void search(String str) {
        ArrayList<BranchProfile> branchlist2 = new ArrayList<>();
        for (BranchProfile obj : branchlist2){
            if (obj.getWholeAddress().toLowerCase().contains(str.toLowerCase())){
                branchlist2.add(obj);
            }
        }
        BranchAdapter branchAdapter = new BranchAdapter(branchlist2);
        searchResult.setAdapter(branchAdapter);
    }
}