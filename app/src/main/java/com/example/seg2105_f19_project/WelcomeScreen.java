package com.example.seg2105_f19_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WelcomeScreen extends AppCompatActivity {

    private String patient;
    private String username;

    private String status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Intent intent = getIntent();
        patient = (String)intent.getSerializableExtra("patient");
        username = (String)intent.getSerializableExtra("username");
        status = (String)intent.getSerializableExtra("status");

        if (status != null) {
            ((TextView)findViewById(R.id.welcome)).setText("Welcome, " + patient + "! You have " + status);
        } else {
            ((TextView) findViewById(R.id.welcome)).setText("Welcome, " + patient + "! You are logged in as a patient.");
        }
    }


    public void onSearchClinic (View view) {
        Intent intent = new Intent(getApplicationContext(), ClinicSearchType.class);
        intent.putExtra("patient", patient);
        intent.putExtra("username", username);
        startActivityForResult(intent, 0);
    }


    public void onLogout (View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }

}
