package com.example.segproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
    CheckBox cbfirstName, cblastName, cbdob, cbaddress, cbemail, cbG1, cbG2, cbG3, cbcompact,
    cbintermediate, cbSUV, cbpickupdate, cbpickuptime, cbreturndate, cbreturntime, cbmovingstartlocation,
            cbmovingendlocation, cbarea, cbkmdriven, cbnumberofmovers, cbnumberofboxes, cbisoffered;

    boolean isOffered;
    boolean firstName;
    boolean lastName;
    boolean dob;
    boolean address;
    boolean email;
    boolean G1;
    boolean G2;
    boolean G3;
    boolean compact;
    boolean intermediate;
    boolean SUV;
    boolean pickupdate;
    boolean pickuptime;
    boolean returndate;
    boolean returntime;
    boolean movingstartlocation;
    boolean movingendlocation;
    boolean area;
    boolean kmdriven;
    boolean numberofmovers;
    boolean numberofboxes;


    NewService ns;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_global_service);

        cbisoffered = findViewById(R.id.isOffered);
        etname = findViewById(R.id.newServiceName);
        etrate = findViewById(R.id.newServiceHourlyRate);
        btcreate = findViewById(R.id.submitNewService);
        cbfirstName = findViewById(R.id.firstName);
        cblastName = findViewById(R.id.lastName);
        cbdob = findViewById(R.id.dob);
        cbaddress = findViewById(R.id.address);
        cbemail = findViewById(R.id.email);
        cbG1 = findViewById(R.id.G1);
        cbG2 = findViewById(R.id.G2);
        cbG3 = findViewById(R.id.G3);
        cbcompact = findViewById(R.id.compact);
        cbintermediate = findViewById(R.id.intermediate);
        cbSUV = findViewById(R.id.SUV);
        cbpickupdate = findViewById(R.id.pickupdate);
        cbpickuptime = findViewById(R.id.pickuptime);
        cbreturndate = findViewById(R.id.returndate);
        cbreturntime = findViewById(R.id.returntime);
        cbmovingstartlocation = findViewById(R.id.movingstartlocation);
        cbmovingendlocation = findViewById(R.id.movingendlocation);
        cbarea = findViewById(R.id.area);
        cbkmdriven = findViewById(R.id.kmdriven);
        cbnumberofmovers = findViewById(R.id.numberofmovers);
        cbnumberofboxes = findViewById(R.id.numberofboxes);

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
            double rate = Double.parseDouble(etrate.getText().toString());

            if (name.isEmpty() ){
                etname.setError("Please enter a name!");
                etname.requestFocus();
                return;
            }
            if (etrate.getText().toString().isEmpty()){
                etrate.setError("Please enter a rate!");
                etrate.requestFocus();
                return;
            }


            if(cbfirstName.isChecked()){
                firstName = true;
            }else{
                firstName = false;
            }

            if(cblastName.isChecked()){
                lastName = true;
            }else{
                lastName = false;
            }

            if(cbdob.isChecked()){
                dob = true;
            }else{
                dob = false;
            }

            if(cbaddress.isChecked()){
                address = true;
            }else{
                address = false;
            }

            if(cbemail.isChecked()){
                email = true;
            }else{
                email = false;
            }

            if(cbG1.isChecked()){
                G1 = true;
            }else{
                G1 = false;
            }

            if(cbG2.isChecked()){
                G2 = true;
            }else{
                G2 = false;
            }

            if(cbG3.isChecked()){
                G3 = true;
            }else{
                G3 = false;
            }

            if(cbcompact.isChecked()){
                compact = true;
            }else{
                compact = false;
            }

            if(cbintermediate.isChecked()){
                intermediate = true;
            }else{
                intermediate = false;
            }

            if(cbSUV.isChecked()){
                SUV = true;
            }else{
                SUV = false;
            }

            if(cbpickupdate.isChecked()){
                pickupdate = true;
            }else{
                pickupdate = false;
            }

            if(cbpickuptime.isChecked()){
                pickuptime = true;
            }else{
                pickuptime = false;
            }

            if(cbreturndate.isChecked()){
                returndate = true;
            }else{
                returndate = false;
            }

            if(cbreturntime.isChecked()){
                returntime = true;
            }else{
                returntime = false;
            }

            if(cbmovingstartlocation.isChecked()){
                movingstartlocation = true;
            }else{
                movingstartlocation = false;
            }

            if(cbmovingendlocation.isChecked()){
                movingendlocation = true;
            }else{
                movingendlocation = false;
            }

            if(cbarea.isChecked()){
                area = true;
            }else{
                area = false;
            }

            if(cbkmdriven.isChecked()){
                kmdriven = true;
            }else{
                kmdriven = false;
            }

            if(cbnumberofmovers.isChecked()){
                numberofmovers = true;
            }else{
                numberofmovers = false;
            }

            if(cbnumberofboxes.isChecked()){
                numberofboxes = true;
            }else{
                numberofboxes = false;
            }
            if (cbisoffered.isChecked()){
                isOffered = true;
            }else{
                isOffered = false;
            }



            String servID = dbServices.push().getKey(); // get unique service id.

            NewService ns = new NewService(address, area,compact,dob, email, firstName,
                    G1, G2, G3, intermediate, kmdriven, lastName, movingendlocation,
                    movingstartlocation, name,  numberofboxes,numberofmovers,
                    pickupdate, pickuptime,rate,returndate, returntime,
                    SUV, isOffered, servID);

            dbServices.child(servID).setValue(ns); // create service sorted by id.

            startActivity(new Intent(AddGlobalService.this, EditService.class));
            Toast.makeText(this,"New service created", Toast.LENGTH_SHORT).show();
        }



    }












