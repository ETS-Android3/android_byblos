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

        dbServices = FirebaseDatabase.getInstance().getReference().child("GlobalService");

        btcreate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                boolean valid = true;
                if (etname.getText().toString().isEmpty()){
                    etname.setError("Please enter a name!");
                    etname.requestFocus();
                    valid = false;
                }if (etrate.getText().toString().isEmpty()){
                    etrate.setError("Please enter a rate");
                    etname.requestFocus();
                    valid = false;
                }
                if ( !etrate.getText().toString().trim().matches("^[0-9]*$") && !etrate.getText().toString().trim().contains(".")){
                    etrate.setError("Rate must be a number!");
                    etrate.requestFocus();
                    valid = false;
                }

                if (valid){
                    createNewService();
                }
            }
        });
    }

        private void createNewService(){
            String name = etname.getText().toString();
            double rate = Double.parseDouble(etrate.getText().toString());

            firstName = cbfirstName.isChecked();

            lastName = cblastName.isChecked();

            dob = cbdob.isChecked();

            address = cbaddress.isChecked();

            email = cbemail.isChecked();

            G1 = cbG1.isChecked();

            G2 = cbG2.isChecked();

            G3 = cbG3.isChecked();

            compact = cbcompact.isChecked();

            intermediate = cbintermediate.isChecked();

            SUV = cbSUV.isChecked();

            pickupdate = cbpickupdate.isChecked();

            pickuptime = cbpickuptime.isChecked();

            returndate = cbreturndate.isChecked();

            returntime = cbreturntime.isChecked();

            movingstartlocation = cbmovingstartlocation.isChecked();

            movingendlocation = cbmovingendlocation.isChecked();

            area = cbarea.isChecked();

            kmdriven = cbkmdriven.isChecked();

            numberofmovers = cbnumberofmovers.isChecked();

            numberofboxes = cbnumberofboxes.isChecked();

            isOffered = cbisoffered.isChecked();

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












