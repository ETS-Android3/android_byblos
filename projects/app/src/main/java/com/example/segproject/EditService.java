package com.example.segproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.os.Bundle;
import android.annotation.*;
import android.content.Intent;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditService extends AppCompatActivity {
    // here the admin will be able to add edit and delete services

    DatabaseReference dbRef;
    Button addservice;
    ListView listView;
    List<NewService> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);

        dbRef = FirebaseDatabase.getInstance().getReference("Service");
        listView = (ListView) findViewById(R.id.newServiceListView);
        arrayList = new ArrayList<>();


        addservice = (Button) findViewById(R.id.addServiceBTN);
        addservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddGlobalService();
            }
        });



    }

    protected void onStart(){//have list of all services
        super.onStart();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();


                for(DataSnapshot info : snapshot.getChildren()){
                    NewService ns = info.getValue(NewService.class);
                    arrayList.add(ns);
                    //NewService ns = info.getValue(NewService.class);
                   // String name = (String) info.child("name").getValue();
//                    if (info.child("rate").getValue() != null) {
//                        int rate = Integer.parseInt((String) Objects.requireNonNull(info.child("rate").getValue()));
//                        boolean address = (boolean) Objects.requireNonNull(info.child("address").getValue());
//                        boolean area = (boolean) Objects.requireNonNull(info.child("area").getValue());
//                        boolean compact = (boolean) Objects.requireNonNull(info.child("compact").getValue());
//                        boolean dob = (Boolean) Objects.requireNonNull(info.child("dob").getValue());
//                        boolean email = (boolean) Objects.requireNonNull(info.child("email").getValue());
//                        boolean firstName = (boolean) Objects.requireNonNull(info.child("firstName").getValue());
//                        boolean G1 = (boolean) Objects.requireNonNull(info.child("G1").getValue());
//                        boolean G2 = (boolean) Objects.requireNonNull(info.child("G2").getValue());
//                        boolean G3 = (boolean) Objects.requireNonNull(info.child("G3").getValue());
//                        boolean intermediate = (boolean) Objects.requireNonNull(info.child("intermediate").getValue());
//                        boolean kmdriven = (boolean) Objects.requireNonNull(info.child("kmdriven").getValue());
//                        boolean lastName = (boolean) Objects.requireNonNull(info.child("intermediate").getValue());
//                        boolean movingendlocation = (boolean) Objects.requireNonNull(info.child("movingendlocation").getValue());
//                        boolean movingstartlocation = (boolean) Objects.requireNonNull(info.child("movingstartlocation").getValue());
//                        boolean numberofboxes = (boolean) Objects.requireNonNull(info.child("numberofboxes").getValue());
//                        boolean numberofmovers = (boolean) Objects.requireNonNull(info.child("numberofmovers").getValue());
//                        boolean pickupdate = (boolean) Objects.requireNonNull(info.child("pickupdate").getValue());
//                        boolean pickuptime = (boolean) Objects.requireNonNull(info.child("pickuptime").getValue());
//                        boolean returndate = (boolean) Objects.requireNonNull(info.child("returndate").getValue());
//                        boolean returntime = (boolean) Objects.requireNonNull(info.child("returntime").getValue());
//                        boolean SUV = (boolean) Objects.requireNonNull(info.child("suv").getValue());
//
//                        arrayList.add(new NewService(address, area,compact,dob, email, firstName,
//                                G1, G2, G3, intermediate, kmdriven, lastName, movingendlocation,
//                                movingstartlocation, name,  numberofboxes,numberofmovers,
//                                pickupdate, pickuptime,rate,returndate, returntime,
//                                SUV));
//
//
//                    }




                }

                NewServiceList serviceAdapter = new NewServiceList(EditService.this, arrayList);


                listView.setAdapter(serviceAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void openAddGlobalService(){
        Intent intent = new Intent(this,AddGlobalService.class);
        startActivity(intent);

    }


}