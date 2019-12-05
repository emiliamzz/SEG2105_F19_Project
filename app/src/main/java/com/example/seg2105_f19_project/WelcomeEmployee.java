package com.example.seg2105_f19_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomeEmployee extends AppCompatActivity {

    private String employee;
    private String username;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("clinics");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_employee);

        Intent intent = getIntent();
        employee = (String)intent.getSerializableExtra("employee");
        username = (String)intent.getSerializableExtra("username");

        ((TextView)findViewById(R.id.welcomeText)).setText("Welcome " + employee + "! You are logged in as an employee.");

        readClinics();
    }


    private void readClinics () {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot key : dataSnapshot.getChildren()) {
                    if (key.getKey().equals(username)) {
                        return;
                    }
                }
                Intent intent = new Intent(getApplicationContext(), CreateClinic.class);
                intent.putExtra("employee", employee);
                intent.putExtra("username", username);
                startActivityForResult(intent, 0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    public void onClinicInfo (View view) {
        Intent intent = new Intent(getApplicationContext(), ClinicInfo.class);
        intent.putExtra("employee", employee);
        intent.putExtra("username", username);
        startActivityForResult(intent, 0);
    }


    public void onViewHours (View view) {
        Intent intent = new Intent(getApplicationContext(), ViewHours.class);
        intent.putExtra("username", username);
        intent.putExtra("employee", employee);
        startActivityForResult(intent, 0);
    }


    public void onViewServices (View view) {
        Intent intent = new Intent(getApplicationContext(), ViewServices.class);
        intent.putExtra("username", username);
        intent.putExtra("employee", employee);
        intent.putExtra("current", true);
        startActivityForResult(intent, 0);
    }


    public void onLogout (View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }

}
