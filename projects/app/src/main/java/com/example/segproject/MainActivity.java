package com.example.segproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

// method to log in (go to welcome page activity).
    public void onLoginButton(View view) {
        // if log in credentials = admin
        // then go to admin page
        // else if log in credentials = employer
        // then go to employer welcome page
        // else go to customer welcome page.

        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivityForResult (intent,0);
    }

    // method to go to sign up activity.
    public void onSignUpButton(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivityForResult (intent,0);
    }

}