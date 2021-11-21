package com.example.segproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch_services);

        dbRef = FirebaseDatabase.getInstance().getReference("GlobalService");
        dbBranch = FirebaseDatabase.getInstance().getReference("branch");
        uid = getIntent().getStringExtra("branchID");
        //Toast.makeText(getApplicationContext(), "welcome ." + uid , Toast.LENGTH_SHORT).show();

        branchServiceListView = findViewById(R.id.addServiceOptionsListView);
        branchArrayList = new ArrayList<>();

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
                ProfileInfo profile = snapshot.getValue(ProfileInfo.class);
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

//    CheckBox cbTruck, cbCar, cbMovingAssistance, cbBike, cbBoat;
//    boolean truck, car, movingAssistance, bike, boat;
//    Button btnAdd;
//    String branchID;
//    String branchServiceListUID;
//    DatabaseReference dbbranchServiceList;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_branch_services);
//
//        branchID = getIntent().getStringExtra("branchID");
////        //Toast.makeText(this,branchID, Toast.LENGTH_SHORT).show();
//        cbTruck = findViewById(R.id.checkBoxTruck);
//        cbCar = findViewById(R.id.checkBoxCar);
//        cbMovingAssistance = findViewById(R.id.checkBoxMovingAssistance);
//        cbBoat = findViewById(R.id.checkBoxBoat);
//        cbBike = findViewById(R.id.checkBoxBike);
//        btnAdd = findViewById(R.id.addButton);
//
//        dbbranchServiceList = FirebaseDatabase.getInstance().getReference().child("bslist");
//
//
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                addServices();
//            }
//        });
//    }
//
//    public void addServices() {
//        if(cbTruck.isChecked()){
//            truck = true;
//        }else{
//            truck = false;
//        }
//
//        if(cbCar.isChecked()){
//            car = true;
//        }else{
//            car = false;
//        }
//
//        if(cbBike.isChecked()){
//            bike = true;
//        }else{
//            bike = false;
//        }
//
//        if(cbBoat.isChecked()){
//            boat = true;
//        }else{
//            boat = false;
//        }
//
//        if(cbMovingAssistance.isChecked()){
//            movingAssistance = true;
//        }else{
//            movingAssistance = false;
//        }
//
//        branchServiceListUID = dbbranchServiceList.push().getKey();
//        //Toast.makeText(this,branchServiceListUID, Toast.LENGTH_SHORT).show();
//        BranchServiceList bsl = new BranchServiceList(truck, car, movingAssistance,bike, boat, branchServiceListUID, branchID);
//        dbbranchServiceList.child(branchServiceListUID).setValue(bsl);
//        Intent intent = new Intent(this,EmployeeProfile.class);
//        intent.putExtra("branchID",branchID);
//        startActivity(intent);
//        Toast.makeText(this,"Branch Services Added", Toast.LENGTH_SHORT).show();
//    }




        //Toast.makeText(getApplicationContext(), "welcome ." + uid , Toast.LENGTH_SHORT).show();


//        branchServiceListView = findViewById(R.id.addServiceOptionsListView);
//        branchArrayList = new ArrayList<>();
//
//
//        //listen for service long press.
//        branchServiceListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                NewService serv = branchArrayList.get(position);
//                addServiceDialog(serv.getName(),serv.getServiceID(),serv.getRate());
//                return false;
//
//            }
//        });

//        dbBranch.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                ProfileInfo profile = snapshot.getValue(ProfileInfo.class);
//                if(profile != null){
//                    addressNum = profile.streetNum;
//                    streetName = profile.streetName;
//                    phoneNum = profile.phoneNum;
//                    employeeID = profile.employeeID;
//                    services = profile.services;
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(AddBranchService.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

//    private void addServiceDialog(String name,  String servID, double rate){
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = getLayoutInflater();
//        final View dialogView = inflater.inflate(R.layout.add_branch_service_button, null);
//        dialogBuilder.setView(dialogView);
//
//        final Button buttonAdd = (Button) dialogView.findViewById(R.id.addButton);
//
//
//        dialogBuilder.setTitle(name);
//        final AlertDialog b = dialogBuilder.create();
//        b.show();
//
//        buttonAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addBranchServiceMethod(servID);
//                //Toast.makeText(getApplicationContext(), "welcome ." + servID , Toast.LENGTH_SHORT).show();
//                b.dismiss();
//
//            }
//        });
//    }

//    public void addBranchServiceMethod(String servID){
//        String branchService = newBranchService.push().getKey();
//
////        services = services + "," + servID;
////        dbBranch.child(uid).child("services").setValue(services);
//    }



//    protected void onStart(){//have list of all services
//        super.onStart();
//        dbRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                branchArrayList.clear();
//
//                for(DataSnapshot info : snapshot.getChildren()) {
//                    NewService ns = info.getValue(NewService.class);
//                    branchArrayList.add(ns);
//                }
//                NewServiceList branchServiceAdapter = new NewServiceList(AddBranchService.this, branchArrayList);
//                branchServiceListView.setAdapter(branchServiceAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//    }
}
