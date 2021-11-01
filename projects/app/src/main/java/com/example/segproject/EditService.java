package com.example.segproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.os.Bundle;
import android.annotation.*;
import android.content.Intent;
import android.widget.Toast;

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

                //int i = 0;
                for(DataSnapshot info : snapshot.getChildren()) {
                    NewService ns = info.getValue(NewService.class);
                    arrayList.add(ns);
                    //arrayList.get(0);
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