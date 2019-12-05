package com.example.seg2105_f19_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SingleService extends AppCompatActivity {

    private String name;
    private String role;

    private String username;
    private String employee;
    private String patient;

    private boolean current;
    private Clinic clinic;
    private List<String> services;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference servicesRef = database.getReference("services");
    private DatabaseReference clinicsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_service);

        Intent intent = getIntent();
        name = (String)intent.getSerializableExtra("name");
        role = (String)intent.getSerializableExtra("role");
        username = (String)intent.getSerializableExtra("username");
        employee = (String)intent.getSerializableExtra("employee");
        patient = (String)intent.getSerializableExtra("patient");
        Serializable thing = intent.getSerializableExtra("current");
        if (thing != null) {
            current = (Boolean)thing;
        }

        ((TextView)findViewById(R.id.infoText)).setText("Name: " + name + "\n" + "Performed by: " + role);

        if (employee != null) {
            clinicsRef = database.getReference("clinics/" + username);
            ((Button)findViewById(R.id.editButton)).setVisibility(View.GONE);
            if (!current) {
                ((Button)findViewById(R.id.deleteButton)).setText("Add");
            }
            readClinics();
        } else if (patient != null) {
            ((Button)findViewById(R.id.editButton)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.deleteButton)).setText("Search");
        }
    }


    private void readClinics () {
        clinicsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String address = dataSnapshot.child("address").getValue().toString();
                String phone = dataSnapshot.child("phone").getValue().toString();
                String insurance = dataSnapshot.child("insurance").getValue().toString();
                String payment = dataSnapshot.child("payment").getValue().toString();
                clinic = new Clinic(name, address, phone, insurance, payment, "");

                String[] list = dataSnapshot.child("services").getValue().toString().split("_");
                services = Arrays.asList(list);
                services = new ArrayList<>(services);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    public void onEdit (View view) {
        Intent intent = new Intent(getApplicationContext(), AddService.class);
        intent.putExtra("name", name);
        intent.putExtra("role", role);
        startActivityForResult(intent, 0);
    }


    public void onDelete (View view) {
        if (employee != null) {
            if (current) {
                services.remove(name);
            } else {
                services.add(name);
            }
            String s = "";
            for (String service : services) {
                s += service + "_";
            }
            clinic.setServices(s);
            clinicsRef.setValue(clinic);
        } else if (patient != null) {
            Intent intent = new Intent(getApplicationContext(), ViewClinics.class);
            intent.putExtra("patient", patient);
            intent.putExtra("username", username);
            intent.putExtra("service", name);
            startActivityForResult(intent, 0);
            return;
        } else {
            servicesRef.child(name).removeValue();
        }
        onBack(view);
    }


    public void onBack (View view) {
        Intent intent = new Intent(getApplicationContext(), ViewServices.class);
        if (employee != null) {
            intent.putExtra("username", username);
            intent.putExtra("employee", employee);
            intent.putExtra("current", current);
        }
        else if (patient != null) {
            intent.putExtra("username", username);
            intent.putExtra("patient", patient);
        }
        startActivityForResult(intent, 0);
    }

}
