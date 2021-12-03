package com.example.segproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        // else if log in credentials = employee
        // then go to employee welcome page
        // else go to customer welcome page.
        String username = eTUsername.getText().toString().trim(); // username or email
        String password = eTPassword.getText().toString().trim();

        if (username.equals("admin") && password.equals("admin")){ //case that admin is logging in.
            Toast.makeText(getApplicationContext(), "Welcome Admin", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), AdminActivity.class); // if successful go to admin page.
            startActivity(intent);
            return;
        }

//        if (username.equals("employee") && password.equals("employee")){ //case that employee is logging in.
//            Toast.makeText(getApplicationContext(), "Welcome Employee", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getApplicationContext(), EmployeeWelcomeActivity.class); // if successful go to admin page.
//            startActivity(intent);
//            return;
//        }
        if (username.equals("customer") && password.equals("customer")){ //case that employee is logging in.
            Toast.makeText(getApplicationContext(), "Welcome Employee", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), CustomerWelcomeActivity.class); // if successful go to admin page.
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

                        if (info.child("role").getValue().equals("Customer")){
                            Intent intent = new Intent(getApplicationContext(), CustomerWelcomeActivity.class); // if successful go to welcome page.
                            intent.putExtra("id", info.child("userID").getValue().toString());
                            intent.putExtra("username", info.child("username").getValue().toString());
                            startActivity(intent);
                            return;

                        }else if (info.child("role").getValue().equals("Employee")){
                            Intent intent = new Intent(getApplicationContext(), EmployeeWelcomeActivity.class); // if successful go to welcome page.
                            intent.putExtra("id", info.child("userID").getValue().toString());

                            startActivity(intent);
                            return;
                        }
                    }
                }
                eTUsername.setError("Username or Password Incorrect");
                eTUsername.requestFocus();
//                Toast.makeText(getApplicationContext(), "Incorrect Credentials", Toast.LENGTH_SHORT).show(); // reached end of db and did not find user
                return;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // method to go to sign up activity.
    public void onSignUpButton(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity (intent);
    }

}