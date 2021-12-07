package com.example.segproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditEmpHours extends AppCompatActivity {
    String branchID;
    String userid;
    String hoursID;

    CheckBox monCB, tuesCB, wedCB, thuCB, friCB;
    Button backButton;
    Button updateButton;
    DatabaseReference dbWorkingHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_emp_hours);

        dbWorkingHours = FirebaseDatabase.getInstance().getReference("hours");
        backButton = findViewById(R.id.EditWorkingHoursBackBTN);
        updateButton = findViewById(R.id.UpdateEmpHoursBTN);


        branchID = getIntent().getStringExtra("branchID"); //branch id
        userid = getIntent().getStringExtra("id"); // user id.
        hoursID = getIntent().getStringExtra("hoursid");

        monCB = findViewById(R.id.editMoncheckBox);
        tuesCB = findViewById(R.id.editTuescheckBox);
        wedCB = findViewById(R.id.editWedcheckBox);
        thuCB = findViewById(R.id.editThucheckBox);
        friCB = findViewById(R.id.editFricheckBox);

        dbWorkingHours.child(hoursID).addValueEventListener(new ValueEventListener() { // find what current hours are.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                WorkingHours wh = snapshot.getValue(WorkingHours.class);
                if (wh != null){
                    monCB.setChecked(wh.getMon());
                    tuesCB.setChecked(wh.getTues());
                    wedCB.setChecked(wh.getWed());
                    thuCB.setChecked(wh.getThu());
                    friCB.setChecked(wh.getFri());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        updateButton.setOnClickListener(new View.OnClickListener() { // if user presses update button we update information in firebase.
            @Override
            public void onClick(View v) {
                WorkingHours whUpdate = new WorkingHours(userid, branchID, hoursID, monCB.isChecked(),tuesCB.isChecked(),wedCB.isChecked(),thuCB.isChecked(),friCB.isChecked());
                dbWorkingHours.child(hoursID).setValue(whUpdate);

                Toast.makeText(EditEmpHours.this, "Hours updated", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EditEmpHours.this,EmpWorkingHours.class);
                intent.putExtra("branchID",branchID);
                intent.putExtra("id",userid);
                intent.putExtra("hoursid", hoursID);
                startActivity(intent);

            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditEmpHours.this,EmpWorkingHours.class);
                intent.putExtra("branchID",branchID);
                intent.putExtra("id",userid);
                intent.putExtra("hoursid", hoursID);
                startActivity(intent);
            }
        });


    }

}