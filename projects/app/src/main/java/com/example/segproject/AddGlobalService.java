package com.example.segproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddGlobalService extends AppCompatActivity {
    // here the admin will be able to add new services to the database

    DatabaseReference dbServices;
    EditText etname;
    EditText etrate;
    Button btcreate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_global_service);

        etname = findViewById(R.id.newServiceName);
        etrate = findViewById(R.id.newServiceHourlyRate);
        btcreate = findViewById(R.id.submitNewService);
        dbServices = FirebaseDatabase.getInstance().getReference().child("Service");

        btcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewService();
            }
        });


    }

    private void createNewService(){
        String name = etname.getText().toString();
        int rate = Integer.parseInt(etrate.getText().toString());
        NewService ns = new NewService(name,rate);

        dbServices.push().setValue(ns);
        Toast.makeText(this,"New service created", Toast.LENGTH_SHORT).show();

    }


}
