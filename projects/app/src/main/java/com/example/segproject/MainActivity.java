package com.example.segproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private EditText eTUsername, eTPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eTUsername = (EditText) findViewById(R.id.loginUsernameField);
        eTPassword = (EditText) findViewById(R.id.loginPasswordField);

    }

// method to log in (go to welcome page activity).
    public void onLoginButton(View view) {
        // if log in credentials = admin
        // then go to admin page
        // else if log in credentials = employer
        // then go to employer welcome page
        // else go to customer welcome page.
        String username = eTUsername.getText().toString().trim(); // username or email
        String password = eTPassword.getText().toString().trim();



        if (username.isEmpty()) {
            eTUsername.setError("Please enter a username");
            eTUsername.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            eTPassword.setError("Please enter a password");
            eTPassword.requestFocus();
            return;
        }

        login (username,password);
    }

    private void login(String usernameOrEmail, String password){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if (Patterns.EMAIL_ADDRESS.matcher(usernameOrEmail).matches()){ // login if email

            mAuth.signInWithEmailAndPassword(usernameOrEmail,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class); // if successful go to welcome page.
                        startActivityForResult (intent,0);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{ // login if username by getting email associated with the username.
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("users").child(usernameOrEmail).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) { // get email associated with user
                    String userEmail = (String) snapshot.getValue();
                    login(userEmail, password); // recurse back to method now that we have email.
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }


    // method to go to sign up activity.
    public void onSignUpButton(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivityForResult (intent,0);
    }

}