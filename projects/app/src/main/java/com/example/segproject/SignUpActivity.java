package com.example.segproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private EditText eTUsername, eTEmail, eTPassword;
    private RadioButton radioRoleCustomer;
    private RadioButton radioRoleEmployee;

    private String email, username, password, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        eTUsername = (EditText) findViewById(R.id.signupUsernameField);
        eTEmail = (EditText) findViewById(R.id.signupEmailField);
        eTPassword = (EditText) findViewById(R.id.signupPasswordField);
        radioRoleCustomer = (RadioButton) findViewById(R.id.roleCustomer);
        radioRoleEmployee = (RadioButton) findViewById(R.id.roleEmployee);
    }


    public Boolean validateSignupInput(View view) { // method to validate sign up info
        email = eTEmail.getText().toString().trim();
        username = eTUsername.getText().toString().trim();
        password = eTPassword.getText().toString().trim();



        if (username.isEmpty()) {
            eTUsername.setError("Please enter a username");
            eTUsername.requestFocus();
            return false;
        }
//        if (mDatabase.child("users").child(username).get().toString() == username) {
//            eTUsername.setError("Username taken");
//            eTUsername.requestFocus();
//            return false;
//        }
        if (email.isEmpty()) {
            eTEmail.setError("Please enter an Email");
            eTEmail.requestFocus();
            return false;
        }
        if (!email.contains("@") || !email.contains(".com") || !email.contains(".ca")){ // we could be more stringent but should be fine for our usage.
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
        if (validateSignupInput(view)){
            Toast.makeText(getApplicationContext(), "VALID", Toast.LENGTH_SHORT).show();

            // now that we verified that a role exists we can set role to customer or employee.
            if (radioRoleCustomer.isChecked()){
                role = "Customer";
            }else if (radioRoleEmployee.isChecked()){
                role = "Employee";
            }

            User user = new User (username, email, password, role);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference newUserRole = database.getReference("users/"+username+"/role");
            DatabaseReference newUserEmail = database.getReference("users/"+username+"/email");
            DatabaseReference newUserPassword = database.getReference("users/"+username+"/password");

            newUserRole.setValue(role);
            newUserEmail.setValue(email);
            newUserPassword.setValue(password);

            // once registered we can send to welcome page.
        }
        else{
            Toast.makeText(getApplicationContext(), "INVALID", Toast.LENGTH_SHORT).show();
        }
    }
}