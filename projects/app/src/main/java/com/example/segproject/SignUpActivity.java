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

    //private DatabaseReference mDatabase;

    private EditText eTUsername, eTEmail, eTPassword;
    private RadioButton radioRoleCustomer;
    private RadioButton radioRoleEmployee;

    private String email, username, password, role;
    boolean canSignUp = false;

    DatabaseReference dbUser = FirebaseDatabase.getInstance().getReference("users");


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

    public void registerUser(View view){
        if (validateSignupInput()){

            // set role to customer or employee.
            if (radioRoleCustomer.isChecked()){
                role = "Customer";
            }else if (radioRoleEmployee.isChecked()){
                role = "Employee";
            }


            String id = dbUser.push().getKey(); //
            User newUser = new User(username, email, password, role, id);
            dbUser.child(id).setValue(newUser);
            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class); // if able to sign in send to welcome page.
            intent.putExtra("id", id);
            startActivity(intent);
//            startActivityForResult (intent,0);
            Toast.makeText(getApplicationContext(), "Signed Up!. ", Toast.LENGTH_SHORT).show();

//
//            FirebaseAuth mAuth = FirebaseAuth.getInstance();
//            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()){
//                        DatabaseReference dbUser = FirebaseDatabase.getInstance().getReference("users");
//
////                    User user = new User(id, username, email, password, role);
//                        User user = new User(username, email, password, role, id );
//                        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                        user.setUserID(userID);
//
//                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() { // add user by uIDto database.
////
////                        FirebaseDatabase.getInstance().getReference("users").(username).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>(){ // add user to database sorted by username.
////                    FirebaseDatabase.getInstance().getReference("users").child(username).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>(){ // add user to database sorted by username.
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()){
////                                FirebaseDatabase.getInstance().getReference("users").child(username).
//
//
//                                Toast.makeText(getApplicationContext(), "User has been registered.", Toast.LENGTH_SHORT).show();
//
//                                Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class); // if able to sign in send to welcome page.
//                                startActivityForResult (intent,0);
//                                Toast.makeText(getApplicationContext(), "Authenticated User Created." , Toast.LENGTH_SHORT).show();
//                            }
//                            else{
//                                Toast.makeText(getApplicationContext(), "User not registered", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//
//                    }
//                    else{
//                        Toast.makeText(getApplicationContext(), "Unable to create user ", Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
//
//        }
//        else{
//            Toast.makeText(getApplicationContext(), "INVALID", Toast.LENGTH_SHORT).show();
        }
    }


    public Boolean validateSignupInput() { // method to validate sign up info
        email = eTEmail.getText().toString().trim();
        username = eTUsername.getText().toString().trim();
        password = eTPassword.getText().toString().trim();
        canSignUp = true;

        if (username.isEmpty()) {
            eTUsername.setError("Please enter a username");
            eTUsername.requestFocus();
            canSignUp = false;
            return canSignUp;
        }

        if (email.isEmpty()) {
            eTEmail.setError("Please enter an Email");
            eTEmail.requestFocus();
            canSignUp = false;
            return canSignUp;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            eTEmail.setError("Please enter a valid Email");
            eTEmail.requestFocus();
            canSignUp = false;
            return canSignUp;
        }
        if (password.isEmpty()) {
            eTPassword.setError("Please enter a password");
            eTPassword.requestFocus();
            canSignUp = false;
            return canSignUp;
        }
        if (password.length() < 6){
            eTPassword.setError("Password must be at least 6 characters long");
            eTPassword.requestFocus();
            canSignUp = false;
            return canSignUp;
        }
        if (!(radioRoleEmployee.isChecked()) && !(radioRoleCustomer.isChecked())){
            Toast.makeText(getApplicationContext(), "Please select a role", Toast.LENGTH_SHORT).show();
            canSignUp = false;
            return canSignUp;
        }

        dbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                canSignUp = true; // set to true. before. if either username or email
                for (DataSnapshot info : snapshot.getChildren()){ //index through all the children
                    if(info.child("username").getValue().equals(username) ) {  //if username entered matches one in db can't sign up.
                        eTUsername.setError("Username Taken");
                        eTUsername.requestFocus();
                        canSignUp = false;
                    }
                    if(info.child("email").getValue().equals(email)) {  //if username entered matches one in db can't sign up.
                        eTEmail.setError("Email Taken");
                        eTEmail.requestFocus();
                        canSignUp = false;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // can also check if the email and username already exists in database.
        return canSignUp; // if we reach here that means that the input is fine.
    }
}