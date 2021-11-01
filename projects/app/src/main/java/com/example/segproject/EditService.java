package com.example.segproject;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class EditService extends AppCompatActivity {
    // here the admin will be able to add edit and delete services

    DatabaseReference dbRef;
    private Button addservice;
    private ListView listView;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);

        dbRef = FirebaseDatabase.getInstance().getReference("Service");
        listView = (ListView) findViewById(R.id.listviewtext);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        dbRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = DataSnapshot.getValue(Service.class).toString();
                arrayList.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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