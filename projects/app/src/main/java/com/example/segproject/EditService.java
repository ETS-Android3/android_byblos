package com.example.segproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.os.Bundle;
import android.content.Intent;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditService extends AppCompatActivity {
    // here the admin will be able to add edit and delete services

    DatabaseReference dbRef;
    Button addservice;
    ListView listView;
    List<NewService> arrayList;

    EditText editTextName;
    EditText editTextRate;

    CheckBox cbfirstName, cblastName, cbdob, cbaddress, cbemail, cbG1, cbG2, cbG3, cbcompact,
            cbintermediate, cbSUV, cbpickupdate, cbpickuptime, cbreturndate, cbreturntime, cbmovingstartlocation,
            cbmovingendlocation, cbarea, cbkmdriven, cbnumberofmovers, cbnumberofboxes, cbisoffered;

    String newName;
    double newRate;
    double rate;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);

        dbRef = FirebaseDatabase.getInstance().getReference("GlobalService");
        listView = (ListView) findViewById(R.id.newServiceListView);
        arrayList = new ArrayList<>();


        addservice = (Button) findViewById(R.id.addServiceBTN);
        addservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddGlobalService();
            }
        });

        //listen for service long press.
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                NewService serv = arrayList.get(position);

                deleteServiceDialog(serv.getName(),serv.getServiceID(),serv.getRate());
                return false;
            }
        });

    }

    private void deleteServiceDialog(String name,  String servID, double rate){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_delete_service, null);
        dialogBuilder.setView(dialogView);

        // convert rate to string
        String rateText = String.valueOf(rate);

        // service name and service rate are shown and can be edited
        editTextName = (EditText) dialogView.findViewById(R.id.nameEditText);
        editTextName.setText(name, TextView.BufferType.EDITABLE);
        editTextRate  = (EditText) dialogView.findViewById(R.id.rateEditText);
        editTextRate.setText(rateText, TextView.BufferType.EDITABLE);

        // appropriate checkboxes should be checked
        // loop through the service's attributes and set appropriate checkboxes to checked

        cbisoffered = dialogView.findViewById(R.id.isOfferedUpdate);
        cbfirstName = dialogView.findViewById(R.id.firstName);
        cblastName = dialogView.findViewById(R.id.lastName);
        cbdob = dialogView.findViewById(R.id.dob);
        cbaddress = dialogView.findViewById(R.id.address);
        cbemail = dialogView.findViewById(R.id.email);
        cbG1 = dialogView.findViewById(R.id.G1);
        cbG2 = dialogView.findViewById(R.id.G2);
        cbG3 = dialogView.findViewById(R.id.G3);
        cbcompact = dialogView.findViewById(R.id.compact);
        cbintermediate = dialogView.findViewById(R.id.intermediate);
        cbSUV = dialogView.findViewById(R.id.SUV);
        cbpickupdate = dialogView.findViewById(R.id.pickupdate);
        cbpickuptime = dialogView.findViewById(R.id.pickuptime);
        cbreturndate = dialogView.findViewById(R.id.returndate);
        cbreturntime = dialogView.findViewById(R.id.returntime);
        cbmovingstartlocation = dialogView.findViewById(R.id.movingstartlocation);
        cbmovingendlocation = dialogView.findViewById(R.id.movingendlocation);
        cbarea = dialogView.findViewById(R.id.area);
        cbkmdriven = dialogView.findViewById(R.id.kmdriven);
        cbnumberofmovers = dialogView.findViewById(R.id.numberofmovers);
        cbnumberofboxes = dialogView.findViewById(R.id.numberofboxes);


        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                cbisoffered.setChecked(false);
                cbfirstName.setChecked(false);
                cblastName.setChecked(false);
                cbdob.setChecked(false);
                cbaddress.setChecked(false);
                cbemail.setChecked(false);
                cbG1.setChecked(false);
                cbG2.setChecked(false);
                cbG3.setChecked(false);
                cbcompact.setChecked(false);
                cbintermediate.setChecked(false);
                cbSUV.setChecked(false);
                cbpickupdate.setChecked(false);
                cbpickuptime.setChecked(false);
                cbreturndate.setChecked(false);
                cbreturntime.setChecked(false);
                cbmovingstartlocation.setChecked(false);
                cbmovingendlocation.setChecked(false);
                cbarea.setChecked(false);
                cbkmdriven.setChecked(false);
                cbnumberofmovers.setChecked(false);
                cbnumberofboxes.setChecked(false);

                for(DataSnapshot info : snapshot.getChildren()){
                    NewService serv = info.getValue(NewService.class);
                    if (serv.isOffered()){
                        cbisoffered.setChecked(true);
                    }if (serv.isFirstName()){
                        cbfirstName.setChecked(true);
                    }if (serv.isLastName()){
                        cblastName.setChecked(true);
                    }if (serv.isDob()){
                        cbdob.setChecked(true);
                    }if (serv.isAddress()){
                        cbaddress.setChecked(true);
                    }if (serv.isEmail()){
                        cbemail.setChecked(true);
                    }if (serv.isG1()){
                        cbG1.setChecked(true);
                    }if (serv.isG2()){
                        cbG2.setChecked(true);
                    }if (serv.isG3()){
                        cbG3.setChecked(true);
                    }if (serv.isCompact()){
                        cbcompact.setChecked(true);
                    }if (serv.isIntermediate()){
                        cbintermediate.setChecked(true);
                    }if (serv.isSUV()){
                        cbSUV.setChecked(true);
                    }if (serv.isPickupdate()){
                        cbpickupdate.setChecked(true);
                    }if (serv.isPickuptime()){
                        cbpickuptime.setChecked(true);
                    }if (serv.isReturndate()){
                        cbreturndate.setChecked(true);
                    }if (serv.isReturntime()){
                        cbreturntime.setChecked(true);
                    }if (serv.isMovingstartlocation()){
                        cbmovingstartlocation.setChecked(true);
                    }if (serv.isMovingendlocation()){
                        cbmovingendlocation.setChecked(true);
                    }if (serv.isArea()){
                        cbarea.setChecked(true);
                    }if (serv.isKmdriven()){
                        cbkmdriven.setChecked(true);
                    }if (serv.isNumberofmovers()){
                        cbnumberofmovers.setChecked(true);
                    }if (serv.isNumberofboxes()){
                        cbnumberofboxes.setChecked(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.updateServiceButton);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.deleteServiceButton);

        dialogBuilder.setTitle(name);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean valid = true;
                if (editTextName.getText().toString().trim().isEmpty() ) {
                    editTextName.setError("Please enter a name!");
                    editTextName.requestFocus();
                    valid = false;
                }
                if (editTextRate.getText().toString().trim().isEmpty()){
                    editTextRate.setError("Please enter a rate!");
                    editTextRate.requestFocus();
                    valid = false;
                }
                if (!editTextRate.getText().toString().trim().matches("[0-9]+(\\.){0,1}[0-9]*")){
                    editTextRate.setError("Rate must be a number!");
                    editTextRate.requestFocus();
                    valid = false;
                }

                if (valid){
                    updateService(servID);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteService(servID);
                b.dismiss();
            }
        });
    }


    public void deleteService(String servID){ //this does not delete service from authenticated database. but does delete from realtime.
        dbRef.child(servID).removeValue();
        Toast.makeText(getApplicationContext(), "Service deleted", Toast.LENGTH_LONG).show();

    }

    public void updateService(String servID){

        if (cbisoffered.isChecked()){
            isOffered = true;
        }else{
            isOffered = false;
        }if (cbfirstName.isChecked()){
            firstName = true;
        }else{
            firstName = false;
        }if (cblastName.isChecked()){
            lastName = true;
        }else{
            lastName = false;
        }if (cbdob.isChecked()){
            dob = true;
        }else{
            dob = false;
        }if (cbaddress.isChecked()){
            address = true;
        }else{
            address = false;
        }if (cbemail.isChecked()){
            email = true;
        }else{
            email = false;
        }if (cbG1.isChecked()){
            G1 = true;
        }else{
            G1 = false;
        }if (cbG2.isChecked()){
            G2 = true;
        }else{
            G2 = false;
        }if (cbG3.isChecked()){
            G3 = true;
        }else{
            G3 = false;
        }if (cbcompact.isChecked()){
            compact = true;
        }else{
            compact = false;
        }if (cbintermediate.isChecked()){
            intermediate = true;
        }else{
            intermediate = false;
        }if (cbSUV.isChecked()){
            SUV = true;
        }else{
            SUV = false;
        }if (cbpickupdate.isChecked()){
            pickupdate = true;
        }else{
            pickupdate = false;
        }if (cbpickuptime.isChecked()){
            pickuptime = true;
        }else{
            pickuptime = false;
        }if (cbreturndate.isChecked()){
            returndate = true;
        }else{
            returndate = false;
        }if (cbreturntime.isChecked()){
            returntime = true;
        }else{
            returntime = false;
        }if (cbmovingstartlocation.isChecked()){
            movingstartlocation = true;
        }else{
            movingstartlocation = false;
        }if (cbmovingendlocation.isChecked()){
            movingendlocation = true;
        }else{
            movingendlocation = false;
        }if (cbarea.isChecked()){
            area = true;
        }else{
            area = false;
        }if (cbkmdriven.isChecked()){
            kmdriven = true;
        }else{
            kmdriven = false;
        }if (cbnumberofmovers.isChecked()){
            numberofmovers = true;
        }else{
            numberofmovers = false;
        }if (cbnumberofboxes.isChecked()){
            numberofboxes = true;
        }else {
            numberofboxes = false;
        }

        newRate = Double.parseDouble(editTextRate.getText().toString().trim());
        newName = editTextName.getText().toString().trim();

        NewService res = new NewService(address,area,compact,dob,email,firstName,G1,G2,G3,
         intermediate,kmdriven,lastName,movingendlocation,
         movingstartlocation,newName,numberofboxes,numberofmovers,
         pickupdate,pickuptime,newRate,returndate,returntime,
         SUV,isOffered,servID);

        dbRef.child(servID).setValue(res);
        Toast.makeText(getApplicationContext(), "Service Updated", Toast.LENGTH_LONG).show();
    }

    protected void onStart(){//have list of all services
        super.onStart();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();

                for(DataSnapshot info : snapshot.getChildren()) {
                    NewService ns = info.getValue(NewService.class);
                    arrayList.add(ns);
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