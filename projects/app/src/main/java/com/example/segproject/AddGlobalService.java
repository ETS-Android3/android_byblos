package com.example.segproject;

import androidx.appcompat.app.AppCompatActivity;

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
            cbmovingendlocation, cbarea, cbkmdriven, cbnumberofmovers, cbnumberofboxes;


    NewService ns;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_global_service);

        etname = findViewById(R.id.newServiceName);
        etrate = findViewById(R.id.newServiceHourlyRate);
        btcreate = findViewById(R.id.submitNewService);
        cbfirstName = findViewById(R.id.firstName);
        cblastName =  findViewById(R.id.lastName);
        cbdob = findViewById(R.id.dob);
        cbaddress  = findViewById(R.id.address);
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
        ns = new NewService();

        btcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etname.getText().toString();
                int rate = Integer.parseInt(etrate.getText().toString());

                ns.setName(name);
                ns.setRate(rate);

                if(cbfirstName.isChecked()){
                    ns.setFirstName("firstname");

                }

                if(cblastName.isChecked()){
                    ns.setLastName("lastname");
                }

                if(cbdob.isChecked()){
                    ns.setDob("dob");
                }

                if(cbaddress.isChecked()){
                    ns.setAddress("address");
                }

                if(cbemail.isChecked()){
                    ns.setEmail("email");
                }

                if(cbG1.isChecked()){
                    ns.setG1("g1");
                }

                if(cbG2.isChecked()){
                    ns.setG2("g2");
                }

                if(cbG3.isChecked()){
                    ns.setG3("g3");
                }

                if(cbcompact.isChecked()){
                    ns.setCompact("compact");
                }

                if(cbintermediate.isChecked()){
                    ns.setIntermediate("intermediate");
                }

                if(cbSUV.isChecked()){
                    ns.setSUV("suv");
                }

                if(cbpickupdate.isChecked()){
                    ns.setPickupdate("pickupdate");
                }

                if(cbpickuptime.isChecked()){
                    ns.setPickuptime("pickuptime");
                }

                if(cbreturndate.isChecked()){
                    ns.setReturndate("returndate");
                }

                if(cbreturntime.isChecked()){
                    ns.setReturntime("returntime");
                }

                if(cbmovingstartlocation.isChecked()){
                    ns.setMovingstartlocation("movingstartlocation");
                }

                if(cbmovingendlocation.isChecked()){
                    ns.setMovingendlocation("movingendlocation");
                }

                if(cbarea.isChecked()){
                    ns.setArea("area");
                }

                if(cbkmdriven.isChecked()){
                    ns.setKmdriven("kmdriven");
                }

                if(cbnumberofmovers.isChecked()){
                    ns.setNumberofmovers("numberofmovers");
                }

                if(cbnumberofboxes.isChecked()){
                    ns.setNumberofboxes("numberofboxes");
                }

            }
        });

        dbServices.push().setValue(ns);
        Toast.makeText(this,"New service created", Toast.LENGTH_SHORT).show();


    }











}
