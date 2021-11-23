package com.example.segproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddBranchService extends AppCompatActivity {


    DatabaseReference dbRef;
    DatabaseReference dbBranch;
    ListView branchServiceListView;
    List<NewService> branchArrayList;
    String uid;
    FirebaseUser user;
    int addressNum;
    String streetName;
    String phoneNum;
    String employeeID;
    String services;
    String id;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch_services);

        dbRef = FirebaseDatabase.getInstance().getReference("GlobalService");
        dbBranch = FirebaseDatabase.getInstance().getReference("branch");
        uid = getIntent().getStringExtra("branchID");
        id = getIntent().getStringExtra("id");
        //Toast.makeText(getApplicationContext(), "welcome ." + uid , Toast.LENGTH_SHORT).show();

        branchServiceListView = findViewById(R.id.addServiceOptionsListView);
        branchArrayList = new ArrayList<>();

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EmployeeProfile.class);
                intent.putExtra("branchID",uid);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });


        //listen for service long press.
        branchServiceListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                NewService serv = branchArrayList.get(position);
                addServiceDialog(serv.getName(),serv.getServiceID(),serv.getRate());
                return false;

            }
        });

        dbBranch.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BranchProfile profile = snapshot.getValue(BranchProfile.class);
                if(profile != null){
                    addressNum = profile.streetNum;
                    streetName = profile.streetName;
                    phoneNum = profile.phoneNum;
                    employeeID = profile.employeeID;
                    services = profile.services;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddBranchService.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addServiceDialog(String name,  String servID, double rate){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_branch_service_button, null);
        dialogBuilder.setView(dialogView);

        final Button buttonAdd = (Button) dialogView.findViewById(R.id.addButton);


        dialogBuilder.setTitle(name);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBranchServiceMethod(servID);
                //Toast.makeText(getApplicationContext(), "welcome ." + servID , Toast.LENGTH_SHORT).show();
                b.dismiss();

            }
        });
    }

    public void addBranchServiceMethod(String servID){
        services = services + "," + servID;
        dbBranch.child(uid).child("services").setValue(services);
    }

    protected void onStart(){//have list of all services
        super.onStart();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchArrayList.clear();

                for(DataSnapshot info : snapshot.getChildren()) {
                    NewService ns = info.getValue(NewService.class);
                    branchArrayList.add(ns);
                }
                NewServiceList branchServiceAdapter = new NewServiceList(AddBranchService.this, branchArrayList);
                branchServiceListView.setAdapter(branchServiceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    protected void onResume() {

        super.onResume();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchArrayList.clear();

                for(DataSnapshot info : snapshot.getChildren()) {
                    NewService ns = info.getValue(NewService.class);
                    branchArrayList.add(ns);
                }
                NewServiceList branchServiceAdapter = new NewServiceList(AddBranchService.this, branchArrayList);
                branchServiceListView.setAdapter(branchServiceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
