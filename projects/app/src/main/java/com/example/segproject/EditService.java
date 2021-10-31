package com.example.segproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditService extends AppCompatActivity {
    // here the admin will be able to add edit and delete services

    private Button addservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);

        addservice = (Button) findViewById(R.id.addServiceBTN);
        addservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddGlobalService();
            }
        });
    }

    public void openAddGlobalService(){
        Intent intent = new Intent(this,AddGlobalService.class);
        startActivity(intent);

    }



    private void createTruckRental(){

// send service to db
    }
    private void createCarRental(){
// send service to db
    }
    private void createMovingAssistance(){
//send service to db
    }
}