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

public class ViewHours extends AppCompatActivity {

    private String username;
    private String employee;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hours);

        Intent intent = getIntent();
        username = (String)intent.getSerializableExtra("username");
        employee = (String)intent.getSerializableExtra("employee");
        ref = database.getReference("hours/" + username);
        readHours();
    }


    private void readHours() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot key) {
                String sundayOpen = key.child("sundayOpen").getValue().toString();
                String sundayClose = key.child("sundayClose").getValue().toString();
                String mondayOpen = key.child("mondayOpen").getValue().toString();
                String mondayClose = key.child("mondayClose").getValue().toString();
                String tuesdayOpen = key.child("tuesdayOpen").getValue().toString();
                String tuesdayClose = key.child("tuesdayClose").getValue().toString();
                String wednesdayOpen = key.child("wednesdayOpen").getValue().toString();
                String wednesdayClose = key.child("wednesdayClose").getValue().toString();
                String thursdayOpen = key.child("thursdayOpen").getValue().toString();
                String thursdayClose = key.child("thursdayClose").getValue().toString();
                String fridayOpen = key.child("fridayOpen").getValue().toString();
                String fridayClose = key.child("fridayClose").getValue().toString();
                String saturdayOpen = key.child("saturdayOpen").getValue().toString();
                String saturdayClose = key.child("saturdayClose").getValue().toString();

                sundayOpen += " to ";
                mondayOpen += " to ";
                tuesdayOpen += " to ";
                wednesdayOpen += " to ";
                thursdayOpen += " to ";
                fridayOpen += " to ";
                saturdayOpen += " to ";

                if (sundayOpen.equals(" to ")) {
                    sundayOpen = "CLOSED";
                }
                if (mondayOpen.equals(" to ")) {
                    mondayOpen = "CLOSED";
                }
                if (tuesdayOpen.equals(" to ")) {
                    tuesdayOpen = "CLOSED";
                }
                if (wednesdayOpen.equals(" to ")) {
                    wednesdayOpen = "CLOSED";
                }
                if (thursdayOpen.equals(" to ")) {
                    thursdayOpen = "CLOSED";
                }
                if (fridayOpen.equals(" to ")) {
                    fridayOpen = "CLOSED";
                }
                if (saturdayOpen.equals(" to ")) {
                    saturdayOpen = "CLOSED";
                }

                ((TextView)findViewById(R.id.hoursText)).setText("Sunday: " + sundayOpen + sundayClose + "\n" +
                        "Monday: " + mondayOpen + mondayClose + "\n" +
                        "Tuesday: " + tuesdayOpen + tuesdayClose + "\n" +
                        "Wednesday: " + wednesdayOpen + wednesdayClose + "\n" +
                        "Thursday: " + thursdayOpen + thursdayClose + "\n" +
                        "Friday: " + fridayOpen + fridayClose + "\n" +
                        "Saturday: " + saturdayOpen + saturdayClose);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    public void onEdit (View view) {
        Intent intent = new Intent(getApplicationContext(), AddHours.class);
        intent.putExtra("username", username);
        intent.putExtra("employee", employee);
        startActivityForResult(intent, 0);
    }


    public void onBack (View view) {
        Intent intent = new Intent(getApplicationContext(), WelcomeEmployee.class);
        intent.putExtra("username", username);
        intent.putExtra("employee", employee);
        startActivityForResult(intent, 0);
    }

}
