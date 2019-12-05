package com.example.seg2105_f19_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ClinicSearchType extends AppCompatActivity {

    private String patient;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_search_type);

        Intent intent = getIntent();
        patient = (String)intent.getSerializableExtra("patient");
        username = (String)intent.getSerializableExtra("username");
    }


    public void onAddressSearch (View view) {
        Intent intent = new Intent(getApplicationContext(), AddressSearch.class);
        intent.putExtra("patient", patient);
        intent.putExtra("username", username);
        startActivityForResult(intent, 0);
    }


    public void onHoursSearch (View view) {
        Intent intent = new Intent(getApplicationContext(), AddHours.class);
        intent.putExtra("patient", patient);
        intent.putExtra("username", username);
        startActivityForResult(intent, 0);
    }


    public void onServicesSearch (View view) {
        Intent intent = new Intent(getApplicationContext(), ViewServices.class);
        intent.putExtra("patient", patient);
        intent.putExtra("username", username);
        startActivityForResult(intent, 0);
    }


    public void onViewAll (View view) {
        Intent intent = new Intent(getApplicationContext(), ViewClinics.class);
        intent.putExtra("patient", patient);
        intent.putExtra("username", username);
        startActivityForResult(intent, 0);
    }


    public void onBack (View view) {
        Intent intent = new Intent(getApplicationContext(), WelcomeScreen.class);
        intent.putExtra("patient", patient);
        intent.putExtra("username", username);
        startActivityForResult(intent, 0);
    }

}
