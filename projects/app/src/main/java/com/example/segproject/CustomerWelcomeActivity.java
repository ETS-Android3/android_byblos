package com.example.segproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    Button testing;
    String branchID;
    String id;
    String workingHours;

    RecyclerView searchResultRV;
    SearchView searchView;
    ArrayList<BranchProfile> branchList;
    BranchAdapter bAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_welcome);

        custName = findViewById(R.id.custNameTextView);
        custRole = findViewById(R.id.custRoleTextView);

        custLogout = findViewById(R.id.custLogoutButton);
        testing = findViewById(R.id.testing);

        bAdapter = new BranchAdapter(null);

        searchResultRV = findViewById(R.id.searchResult);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        searchResultRV.setLayoutManager(llm);
        searchResultRV.setAdapter(bAdapter);


        searchView = findViewById(R.id.searchView);

        dbUsers = FirebaseDatabase.getInstance().getReference("users");
        dbBranches = FirebaseDatabase.getInstance().getReference("branch");
        userid = getIntent().getStringExtra("id");
        username = getIntent().getStringExtra("username");

        custName.setText("Welcome, " + username + "!");
        custRole.setText("Your Role is: Customer");



        if (dbBranches != null){
            dbBranches.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        branchList = new ArrayList<>();
                        for(DataSnapshot info : snapshot.getChildren()){
                            branchList.add(info.getValue(BranchProfile.class));
                        }

                        bAdapter.updateData(branchList);
                        bAdapter.notifyDataSetChanged();
//                        BranchAdapter branchAdapter = new BranchAdapter(branchList);
//                        searchResultRV.setAdapter(branchAdapter);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(CustomerWelcomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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




        custLogout.setOnClickListener(new View.OnClickListener() { // logout button listener.
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerWelcomeActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
            }
        });

        testing.setOnClickListener(new View.OnClickListener() { // logout button listener.
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerWelcomeActivity.this, BranchDisplay.class));
                Toast.makeText(getApplicationContext(), "testing", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//
//        if (dbBranches != null){
//            dbBranches.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.exists()){
//                        branchList = new ArrayList<>();
//                        for(DataSnapshot info : snapshot.getChildren()){
//                            branchList.add(info.getValue(BranchProfile.class));
//                        }
//
//                        BranchAdapter branchAdapter = new BranchAdapter(branchList);
//                        searchResultRV.setAdapter(branchAdapter);
//                    }
//                }
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(CustomerWelcomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//        if (searchView != null){
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    search(newText);
//                    return true;
//                }
//            });
//        }
    }

    private void search(String str) {
        ArrayList<BranchProfile> branchlist2 = new ArrayList<>();
        for (BranchProfile obj : branchList){
            if (obj.getWholeAddress().toLowerCase().contains(str.toLowerCase())){
                branchlist2.add(obj);
            }
        }

        bAdapter.updateData(branchlist2);
        bAdapter.notifyDataSetChanged();
//        BranchAdapter branchAdapter = new BranchAdapter(branchlist2);
//        searchResultRV.setAdapter(branchAdapter);

    }
}