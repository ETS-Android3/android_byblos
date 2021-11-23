package com.example.segproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeeProfile extends AppCompatActivity {

    String branchID;
    String addressName;
    String phoneNumber;
    String city;
    String state;
    String country;
    String zip;
    Button addService;
    ListView branchServiceListView;

    List<NewService> branchServiceList; // stores list of global services associated with branch (branch associated with a user)

    List<NewService> globalServiceList; //list of current global services.


    DatabaseReference dbBranchRef;
    DatabaseReference dbGlobServ;
    DatabaseReference dbUser;
    String services;
    String[] branchServices;

    List<NewService> globalServices; //list of global services.
    List<String> branchServices2;

//    List<String> branchServices2;
    String[] individualServicesRefined;
    String userid;
    String temp = "";

//implement a long click listener for delete later.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        //rename db refs
        dbBranchRef = FirebaseDatabase.getInstance().getReference("branch"); // get reference to branches
        dbGlobServ = FirebaseDatabase.getInstance().getReference("GlobalService"); // get reference to Global services
        dbUser = FirebaseDatabase.getInstance().getReference("users"); // get reference to users.

        branchID = getIntent().getStringExtra("branchID"); //branch id
        userid = getIntent().getStringExtra("id"); // user id.

//        final TextView addressEBanner = (TextView) findViewById(R.id.addressEmployeeBanner);
//        final TextView phoneNumberEBanner = (TextView) findViewById(R.id.phoneNumberEmployeeBanner);
// removed final

        TextView addressEBanner = (TextView) findViewById(R.id.addressEmployeeBanner);
        TextView phoneNumberEBanner = (TextView) findViewById(R.id.phoneNumberEmployeeBanner);

        addService = findViewById(R.id.add);
        branchServiceListView = findViewById(R.id.branchServiceListView);
        branchServiceList = new ArrayList<>();

        addService.setOnClickListener(new View.OnClickListener() { // listen for add service button click.
            @Override
            public void onClick(View view) {
                openAddBranchService();
            }
        });

        branchServiceListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { //listen for long press to see if you want to delete a service.
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                NewService service = branchServiceList.get(position);

                showUpdateDeleteDialog(service.getName(), position);
                return true;
            }
        });

// use singleValueEvent since we only set branchID once.
        dbUser.child(userid).addListenerForSingleValueEvent(new ValueEventListener() { // sets user's branch id.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    String role = userProfile.getRole();
                    if(role.equals("Employee")){
                        temp = temp + branchID;
                        dbUser.child(userid).child("branchID").setValue(temp);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dbBranchRef.child(branchID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BranchProfile profile = snapshot.getValue(BranchProfile.class);

                if (profile != null) {
                    int addressNum = profile.streetNum;
                    addressName = profile.streetName;
                    city = profile.city;
                    state = profile.state;
                    country = profile.country;
                    zip = profile.zip;
                    phoneNumber = profile.phoneNum;

                    addressEBanner.setText("Address: " + addressNum + " " + addressName + ", " +
                            city + ", " + state + ", " + country + ", " + zip);
                    phoneNumberEBanner.setText("Phone number: " + phoneNumber);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EmployeeProfile.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }


    protected void onStart() {//have list of all services
        super.onStart();
        
        dbBranchRef.child(branchID).addValueEventListener(new ValueEventListener() { // grabs services offered at branch
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BranchProfile profile = snapshot.getValue(BranchProfile.class);
                if (profile != null) {
                    services = profile.getServices();
                    branchServices = services.split(",");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EmployeeProfile.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        dbGlobServ.addValueEventListener(new ValueEventListener() { // outputs the services offered at the branch in listview.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchServiceList.clear();

                for (DataSnapshot info : snapshot.getChildren()) { // iterate through all global services and check if
                    NewService ns = info.getValue(NewService.class);

                    if (ns != null) {
                        if(branchServices !=null){ // look here
                            for (String s : branchServices) {
                                if (s.equals(ns.getServiceID())) {
                                    branchServiceList.add(ns);
                                }
                            }
                        }
                    }
                }
                NewServiceList branchAdapter = new NewServiceList(EmployeeProfile.this, branchServiceList);
                branchServiceListView.setAdapter(branchAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EmployeeProfile.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showUpdateDeleteDialog( String serviceName, int position){ // delete service offered at branch
//service id = id of global service we want to delete from branch.

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.delete_service_offered_by_branch, null);
        dialogBuilder.setView(dialogView);

        final Button buttonDeleteService = (Button) dialogView.findViewById(R.id.deleteBranchServiceBTN);

        dialogBuilder.setTitle("Delete " + serviceName + " service?");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonDeleteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteService(position);
                b.dismiss();
            }
        });
    }
    private boolean deleteService(int position){ // have to go through

        branchServiceList.remove(position);
        String newServices = "";
        for (NewService serv : branchServiceList) {
            newServices +=serv.getServiceID();
        }
        dbBranchRef.child(branchID).child("services").setValue(newServices);// send services to db.
        onStart();
        Toast.makeText(getApplicationContext(), "Service deleted", Toast.LENGTH_LONG).show();
        return true;
    }

    private void openAddBranchService(){

        Intent intent = new Intent(this,AddBranchService.class);
        intent.putExtra("branchID",branchID);
        intent.putExtra("id",userid);
        startActivity(intent);
    }

}