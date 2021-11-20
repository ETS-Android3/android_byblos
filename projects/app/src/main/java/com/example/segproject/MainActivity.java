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

    DatabaseReference dbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eTUsername = (EditText) findViewById(R.id.loginUsernameField);
        eTPassword = (EditText) findViewById(R.id.loginPasswordField);

        dbUser = FirebaseDatabase.getInstance().getReference("users");
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

        if (username.equals("admin") && password.equals("admin")){ //case that admin is logging in.
            Toast.makeText(getApplicationContext(), "Welcome Admin", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), AdminActivity.class); // if successful go to admin page.
            startActivity(intent);
            return;
        }

        if (username.equals("employee") && password.equals("employee")){ //case that employee is logging in.
            Toast.makeText(getApplicationContext(), "Welcome Employee", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), EmployeeActivity.class); // if successful go to admin page.
            startActivity(intent);
            return;
        }

        if (username.isEmpty()) {
            eTUsername.setError("Please enter a username");
            eTUsername.requestFocus();
            return;
        }
        if (password.isEmpty() ) {
            eTPassword.setError("Please enter a password");
            eTPassword.requestFocus();
            return;
        }
        if (password.length() < 6 ){
            eTPassword.setError("Password must be at least 6 characters long");
            eTPassword.requestFocus();
            return ;
        }
        login (username,password); //if we get here then user is not an admin and fields are valid. we can attempt to log in the user.
    }

    private void login(String usernameOrEmail, String password){
        //check username or email matches one in db
        dbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot info : snapshot.getChildren()) { //index through all the children
                    if ( (info.child("email").getValue().equals(usernameOrEmail) && info.child("password").getValue().equals(password) )
                            || (info.child("username").getValue().equals(usernameOrEmail) && info.child("password").getValue().equals(password)) ) {
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class); // if successful go to welcome page.
                        intent.putExtra("id", info.child("userID").getValue().toString());
                        startActivity(intent);
                        return;
                    }
                }
                Toast.makeText(getApplicationContext(), "Incorrect Credentials", Toast.LENGTH_SHORT).show(); // reached end of db and did not find user
                return;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        if (Patterns.EMAIL_ADDRESS.matcher(usernameOrEmail).matches()){//logging in with email.
//            dbUser.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    for (DataSnapshot info : snapshot.getChildren()) { //index through all the children
//                        if (info.child("email").getValue().equals(usernameOrEmail) && info.child("password").getValue().equals(password)) {// username matches a password in db
//
//                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
//
//                            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class); // if successful go to welcome page.
//                            intent.putExtra("id", info.child("userID").getValue().toString());
//                            startActivity(intent);
//                            return;
//                        }
//                    }
//                    Toast.makeText(getApplicationContext(), "Incorrect Credentials", Toast.LENGTH_SHORT).show(); // reached end of db and did not find user
//                    return;
//                }
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//
//        }else{ //logging in with username
//            dbUser.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    for (DataSnapshot info : snapshot.getChildren()){ //index through all the children
//                        if(info.child("username").getValue().equals(usernameOrEmail) && info.child("password").getValue().equals(password)) {// username matches a password in db
//
//                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class); // if successful go to welcome page.
//                            intent.putExtra("id", info.child("userID").getValue().toString());
//                            startActivity(intent);
//                            return;
//                        }
//                    }
//                    Toast.makeText(getApplicationContext(), "Incorrect Credentials", Toast.LENGTH_SHORT).show(); // reached end of db and did not find user
//                    return;
//                }
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });

//        }


//
//                dbUser.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                canSignUp = true;
//                for (DataSnapshot info : snapshot.getChildren()){ //index through all the children
//                    if(info.child("username").getValue().equals(username)) {  //if username entered matches one in db can't sign up.
//                        eTUsername.setError("Username Taken");
//                        eTUsername.requestFocus();
//                        canSignUp = false;
//                        return;
//                    }if(info.child("email").getValue().equals(email)) {  //if username entered matches one in db can't sign up.
//                        eTEmail.setError("Email Taken");
//                        eTEmail.requestFocus();
//                        canSignUp = false;
//                        return;
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//
//
//
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        if (Patterns.EMAIL_ADDRESS.matcher(usernameOrEmail).matches()) { // case where its an email.
//            mAuth.signInWithEmailAndPassword(usernameOrEmail, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class); // if successful go to welcome page.
//                        startActivityForResult(intent, 0);
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }else{
//            // case that its a username.
//            // first check all users and see if a username and password matches the credentials give. then grab their email and call login again.
//            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
//            userRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    for (DataSnapshot info : snapshot.getChildren()){ //index through all the children
//                        if((info.child("username").getValue().equals(usernameOrEmail) && info.child("password").getValue().equals(password))){  // if both username and pass match try to sign in
//                            login(info.child("email").getValue(String.class), password);
//                            return;
//                        }
//                    }
//                    Toast.makeText(getApplicationContext(), "Login Failed.", Toast.LENGTH_SHORT).show(); // could not find username.
//                    return;
//                }
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                }
//            });
//        }
    }

    // method to go to sign up activity.
    public void onSignUpButton(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity (intent);
    }

}