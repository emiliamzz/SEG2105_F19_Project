package com.example.seg2105_f19_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WelcomeAdmin extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_admin);

        ((TextView)findViewById(R.id.welcomeText)).setText("Welcome, Admin! You are logged in as an admin.");
    }


    public void onViewAccounts (View view) {
        Intent intent = new Intent(getApplicationContext(), ViewAccounts.class);
        startActivityForResult(intent, 0);
    }


    public void onViewServices (View view) {
        Intent intent = new Intent(getApplicationContext(), ViewServices.class);
        startActivityForResult(intent, 0);
    }


    public void onLogout (View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }

}
