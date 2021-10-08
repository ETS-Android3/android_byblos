package com.example.segproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.Sampler;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private EditText eTUsername, eTEmail, eTPassword;
    private RadioButton radioRoleCustomer;
    private RadioButton radioRoleEmployee;

    private String email, username, password, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        eTUsername = (EditText) findViewById(R.id.signupUsernameField);
        eTEmail = (EditText) findViewById(R.id.signupEmailField);
        eTPassword = (EditText) findViewById(R.id.signupPasswordField);
        radioRoleCustomer = (RadioButton) findViewById(R.id.roleCustomer);
        radioRoleEmployee = (RadioButton) findViewById(R.id.roleEmployee);
    }

    public Boolean validateSignupInput() { // method to validate sign up info
        email = eTEmail.getText().toString().trim();
        username = eTUsername.getText().toString().trim();
        password = eTPassword.getText().toString().trim();


        if (username.isEmpty()) {
            eTUsername.setError("Please enter a username");
            eTUsername.requestFocus();
            return false;
        }

//        if (mAuth.getUid().equals(username)){
//            eTUsername.setError("Username taken");
//            eTUsername.requestFocus();
//            return false;
//        }

        if (email.isEmpty()) {
            eTEmail.setError("Please enter an Email");
            eTEmail.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            eTEmail.setError("Please enter a valid Email");
            eTEmail.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            eTPassword.setError("Please enter a password");
            eTPassword.requestFocus();
            return false;
        }
        if (password.length() < 6){
            eTPassword.setError("Password must be at least 6 characters long");
            eTPassword.requestFocus();
            return false;
        }
        if (!(radioRoleEmployee.isChecked()) && !(radioRoleCustomer.isChecked())){
            Toast.makeText(getApplicationContext(), "Please select a role", Toast.LENGTH_SHORT).show();
            return false;
        }

        // can also check if the email and username already exists in database.
        return true; // if we reach here that means that the input is fine.
    }

    public void registerUser(View view){
        if (validateSignupInput()){
//            Toast.makeText(getApplicationContext(), "VALID", Toast.LENGTH_SHORT).show();

            // now that we verified that a role exists we can set role to customer or employee.
            if (radioRoleCustomer.isChecked()){
                role = "Customer";
            }else if (radioRoleEmployee.isChecked()){
                role = "Employee";
            }


            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                    User user = new User(username, email, password, role);

                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "User has been registered.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "User not registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

//                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//                    mDatabase.child("users").child(username).setValue(user);
//                    FirebaseUser curr = mAuth.getCurrentUser();
                        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class); // if able to sign in send to welcome page.
                        startActivityForResult (intent,0);
                        Toast.makeText(getApplicationContext(), "Authenticated User Created." , Toast.LENGTH_LONG).show();

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "User not created.", Toast.LENGTH_SHORT).show();
                    }
                }
            });





            //
//            User user = new User(email, password, role);
//            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//            mDatabase.child("users").child(username).setValue(user);
            //
//            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
//            startActivityForResult (intent,0);
//            Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(getApplicationContext(), "INVALID", Toast.LENGTH_SHORT).show();
        }
    }

    public void createNewUser(String username, String email, String password, String role){

//        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//                    Toast.makeText(getApplicationContext(), "User Created success.", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//        mAuth.
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
//                    User user = new User(email, password, role);
//                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//                    mDatabase.child("users").child(username).setValue(user);
//                    FirebaseUser curr = mAuth.getCurrentUser();
                    Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class); // if able to sign in send to welcome page.
                    startActivityForResult (intent,0);
                    Toast.makeText(getApplicationContext(), "Authenticated User Created." , Toast.LENGTH_LONG).show();


                }
                else{
                    Toast.makeText(getApplicationContext(), "User not created.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}