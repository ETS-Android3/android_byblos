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
    Button empLogout;
    Button viewHours;
    ListView branchServiceListView;

    List<NewService> branchServiceList; // stores list of global services associated with branch (branch associated with a user)

    ListView branchRequestsListView;
    List<ServiceRequest> branchRequestsServiceList;
    String requests;
    String[] branchRequests;

    ListView branchAcceptedRequestsListView;
    List<ServiceRequest> branchAcceptedRequestsServiceList;
    String[] branchAcceptedRequests;

    DatabaseReference dbBranchRef;
    DatabaseReference dbGlobServ;
    DatabaseReference dbUser;
    DatabaseReference dbRequests;
    String services;
    String servicesNames;
    String[] branchServices;
    ArrayList<String> branchServicesNamesList;


    String userid;
    String hoursID;
    String temp = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        //rename db refs
        dbBranchRef = FirebaseDatabase.getInstance().getReference("branch"); // get reference to branches
        dbGlobServ = FirebaseDatabase.getInstance().getReference("GlobalService"); // get reference to Global services
        dbUser = FirebaseDatabase.getInstance().getReference("users"); // get reference to users.
        dbRequests= FirebaseDatabase.getInstance().getReference("ServiceRequests"); // get reference to users.

        branchID = getIntent().getStringExtra("branchID"); //branch id
        userid = getIntent().getStringExtra("id"); // user id.
        hoursID = getIntent().getStringExtra("hoursid");


        TextView addressEBanner = (TextView) findViewById(R.id.addressEmployeeBanner);
        TextView phoneNumberEBanner = (TextView) findViewById(R.id.phoneNumberEmployeeBanner);

        empLogout = findViewById(R.id.empLogOutBTN);
        viewHours = findViewById(R.id.empHoursBTN);

        addService = findViewById(R.id.add);
        branchServiceListView = findViewById(R.id.branchServiceListView);
        branchServiceList = new ArrayList<>();

        branchServicesNamesList = new ArrayList<>();


        // requests

        branchRequestsListView = findViewById(R.id.branchRequestsListView);
        branchRequestsServiceList = new ArrayList<>();

        // accepted requests
        branchAcceptedRequestsListView = findViewById(R.id.branchAcceptedRequestsListView);
        branchAcceptedRequestsServiceList= new ArrayList<>();


        viewHours.setOnClickListener(new View.OnClickListener() { // go to hours page.
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeProfile.this,EmpWorkingHours.class);
                intent.putExtra("branchID",branchID);
                intent.putExtra("id",userid);
                intent.putExtra("hoursid",hoursID);
                startActivity(intent);
            }
        });


        empLogout.setOnClickListener(new View.OnClickListener() { // listen for logout.
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeProfile.this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
            }
        });

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


        // request long click
        branchRequestsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { //listen for long press to see if you want to delete a service.
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ServiceRequest request = branchRequestsServiceList.get(position);

                acceptRequestDialog(request.getServiceName(), request.getRequestID(),position);
                return true;
            }
        });

        // accepted long click
        branchAcceptedRequestsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { //listen for long press to see if you want to delete a service.
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ServiceRequest request = branchAcceptedRequestsServiceList.get(position);

               // acceptRequestDialog(request.getServiceName(), request.getRequestID(),position);
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
                    requests = profile.getRequests();
                    branchRequests = requests.split(",");
                    acceptedRequests = profile.getRequests();
                    branchAcceptedRequests = requests.split(",");

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
                branchServicesNamesList.clear();

                for (DataSnapshot info : snapshot.getChildren()) { // iterate through all global services and check if
                    NewService ns = info.getValue(NewService.class);

                    if (ns != null) {
                        if(branchServices !=null){ // look here
                            for (String s : branchServices) {
                                if (s.equals(ns.getServiceID())) {
                                    branchServiceList.add(ns);
                                    branchServicesNamesList.add(ns.getName());

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



        dbRequests.addValueEventListener(new ValueEventListener() { // outputs the services offered at the branch in listview.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchRequestsServiceList.clear();

                for (DataSnapshot info : snapshot.getChildren()) { // iterate through all global services and check if
                    ServiceRequest sr = info.getValue(ServiceRequest.class);

                    if (sr != null) {
                        if(branchRequests !=null){ // look here
                            for (String s : branchRequests) {
                                if (s.equals(sr.getRequestID())) {
                                    branchRequestsServiceList.add(sr);
                                }
                            }

                            for (String s : branchAcceptedRequests) {
                                if (s.equals(sr.getRequestID())) {
                                    branchAcceptedRequestsServiceList.add(sr);
                                }
                            }
                        }

                    }
                }
                ServiceRequestList branchRequestAdapter = new ServiceRequestList(EmployeeProfile.this, branchRequestsServiceList);
                branchRequestsListView.setAdapter(branchRequestAdapter);

                ServiceRequestList branchAcceptedRequestAdapter = new ServiceRequestList(EmployeeProfile.this, branchAcceptedRequestsServiceList);
                branchAcceptedRequestsListView.setAdapter(branchAcceptedRequestAdapter);
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

    private void acceptRequestDialog(String serviceName, String requestID, int position){ // delete service offered at branch
//service id = id of global service we want to delete from branch.

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.accept_request, null);
        dialogBuilder.setView(dialogView);

        final Button buttonAcceptRequest = (Button) dialogView.findViewById(R.id.acceptButton);

        dialogBuilder.setTitle("Accept " + serviceName + " service request?");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonAcceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                acceptRequest(requestID, position);
                b.dismiss();
            }
        });
    }

    private boolean acceptRequest(String requestID, int position){ // delete service by position.
        branchRequestsServiceList.remove(position);

        String requests = "";

        for (ServiceRequest r: branchRequestsServiceList) {
            requests = requests + "," + r.getRequestID();
        }
        addAcceptedBranchRequestMethod(requestID);

        dbBranchRef.child(branchID).child("requests").setValue(requests);// send services to db.

        onStart();
        Toast.makeText(getApplicationContext(), "Request added", Toast.LENGTH_LONG).show();
        return true;
    }
    String acceptedRequests = "";
    public void addAcceptedBranchRequestMethod(String serviceRequestID){
        acceptedRequests = acceptedRequests + serviceRequestID + ", ";
        dbBranchRef.child(branchID).child("acceptedRequests").setValue(acceptedRequests);

    }

    private boolean deleteService(int position){ // delete service by position.
        branchServiceList.remove(position);

        String newServices = "";
        String newServicesNames = "";
        for (NewService serv : branchServiceList) {
            newServices = newServices + ","+serv.getServiceID();
            newServicesNames = newServicesNames + "," + serv.getName();
        }

        dbBranchRef.child(branchID).child("services").setValue(newServices);// send services to db.
        dbBranchRef.child(branchID).child("serviceNames").setValue(newServicesNames); // send new service names to db.
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