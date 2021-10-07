package com.example.segproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;


    private EditText eTUsername, eTEmail, eTPassword;
    private RadioButton radioRoleCustomer;
    private RadioButton radioRoleEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        eTUsername = (EditText) findViewById(R.id.signupUsernameField);
        eTEmail = (EditText) findViewById(R.id.signupEmailField);
        eTPassword = (EditText) findViewById(R.id.signupPasswordField);
        radioRoleCustomer = (RadioButton) findViewById(R.id.roleCustomer);
        radioRoleEmployee = (RadioButton) findViewById(R.id.roleEmployee);
    }


    public void validateInput(View view) {
        String email = eTEmail.getText().toString().trim();
        String username = eTUsername.getText().toString().trim();
        String password = eTPassword.getText().toString().trim();
        String role = "";

        if (username.isEmpty()) {
            eTUsername.setError("Please enter a username");
            eTUsername.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            eTEmail.setError("Please enter an Email");
            eTEmail.requestFocus();
            return;
        }
        if (!email.contains("@") || !email.contains(".com")){
            eTEmail.setError("Please enter a valid Email");
            eTEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            eTPassword.setError("Please enter a password");
            eTPassword.requestFocus();
            return;
        }
        if (password.length() < 6){
            eTPassword.setError("Password must be at least 6 characters long");
            eTPassword.requestFocus();
            return;
        }
        // fix validation here
        if(radioRoleEmployee.isChecked()){
            role = "employee";
            return;
        }// and here
        if (radioRoleCustomer.isChecked()){
            role = "customer";
            return;
        }
        if(!radioRoleCustomer.isChecked() && !radioRoleEmployee.isChecked()){
            radioRoleCustomer.setError("please select a role");
            return;
        }

    }


}