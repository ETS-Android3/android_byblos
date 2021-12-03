package com.example.segproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewBranchServices extends AppCompatActivity {
    DatabaseReference dbBranchRef;
    DatabaseReference dbGlobServ;
    DatabaseReference dbUser;
    DatabaseReference dbRequests;
    DatabaseReference dbFeedback;
    String branchID;
    String hoursID;
    String userid;
    String[] branchServices;
    String services;
    ListView branchServiceListView;
    List<NewService> branchServiceList;
    ArrayList<String> branchServicesNamesList;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branch_services);

        //rename db refs
        dbBranchRef = FirebaseDatabase.getInstance().getReference("branch"); // get reference to branches
        dbGlobServ = FirebaseDatabase.getInstance().getReference("GlobalService"); // get reference to Global services
        dbUser = FirebaseDatabase.getInstance().getReference("users"); // get reference to users.
        dbRequests = FirebaseDatabase.getInstance().getReference("ServiceRequests"); // get reference to users.
        dbFeedback = FirebaseDatabase.getInstance().getReference("feedback");

        branchID = getIntent().getStringExtra("branchID"); //branch id
        userid = getIntent().getStringExtra("id"); // user id.
        hoursID = getIntent().getStringExtra("hoursid");

        backButton = findViewById(R.id.branchServicesOfferedBackBTN);

        branchServiceListView = findViewById(R.id.branchServiceListView);
        branchServiceList = new ArrayList<>();
        branchServicesNamesList = new ArrayList<>();


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewBranchServices.this,EmployeeProfile.class);
                intent.putExtra("branchID",branchID);
                intent.putExtra("id",userid);
                intent.putExtra("hoursid", hoursID);
                startActivity(intent);
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
                Toast.makeText(ViewBranchServices.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        dbGlobServ.addValueEventListener(new ValueEventListener() { // outputs the services offered at the branch in listview.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchServiceList.clear();
                branchServicesNamesList.clear();

                for (DataSnapshot info : snapshot.getChildren()) { // iterate through all global services and check if
                    NewService ns = info.getValue(NewService.class);

                    if (ns != null) {
                        if (branchServices != null) { // look here
                            for (String s : branchServices) {
                                if (s.equals(ns.getServiceID())) {
                                    branchServiceList.add(ns);
                                    branchServicesNamesList.add(ns.getName());

                                }
                            }
                        }
                    }
                }
                NewServiceList branchAdapter = new NewServiceList(ViewBranchServices.this, branchServiceList);
                branchServiceListView.setAdapter(branchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewBranchServices.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showUpdateDeleteDialog(String serviceName, int position) { // delete service offered at branch
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

    private boolean deleteService(int position) { // delete service by position.
        branchServiceList.remove(position);

        String newServices = "";
        String newServicesNames = "";
        for (NewService serv : branchServiceList) {
            newServices = newServices + "," + serv.getServiceID();
            newServicesNames = newServicesNames + "," + serv.getName();
        }

        dbBranchRef.child(branchID).child("services").setValue(newServices);// send services to db.
        dbBranchRef.child(branchID).child("serviceNames").setValue(newServicesNames); // send new service names to db.
        onStart();
        Toast.makeText(getApplicationContext(), "Service deleted", Toast.LENGTH_LONG).show();
        return true;
    }


}