package com.example.segproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomeActivity extends AppCompatActivity {


    String id;
    String role;
    DatabaseReference dbUser = FirebaseDatabase.getInstance().getReference("users");
    Button btnProfile;

//    private FirebaseUser user; // current user.
//    private DatabaseReference ref;


//    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btnProfile = findViewById(R.id.profileButton);
        id = getIntent().getStringExtra("id");

        final TextView roleTextView = (TextView) findViewById(R.id.roleTextView);
        final TextView nameTextView = (TextView) findViewById(R.id.nameTextView);

//        Toast.makeText(getApplicationContext(), "welcome ." + id , Toast.LENGTH_SHORT).show();


        dbUser.child(id).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null){
                    String name = userProfile.username;
                    nameTextView.setText("Welcome, " + name + "!");
                    role = userProfile.role;
                    roleTextView.setText("Your role is " + role);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(role.equals("Employee")) {
                    profile();
                }
            }
        });


//
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        ref = FirebaseDatabase.getInstance().getReference("users");
//        userId = user.getUid();
//
//        final TextView roleTextView = (TextView) findViewById(R.id.roleTextView);
//        final TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
//
//        ref.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User userProfile = snapshot.getValue(User.class);
//
//                if (userProfile != null){
//                    String name = userProfile.username;
//                    nameTextView.setText("Welcome, " + name + "!");
//                    String role = userProfile.role;
//                    roleTextView.setText("Your role is " + role);
//                }
//                else{
////                    Toast.makeText(getApplicationContext(), "test: "  , Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });

    }
    public void logout(View view){
//        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
    }

    public void profile(){
        startActivity(new Intent(WelcomeActivity.this, EmployeeActivity.class));
        Toast.makeText(getApplicationContext(), "Redirecting to profile", Toast.LENGTH_SHORT).show();
    }
}