package com.example.seg2105_f19_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewServices extends AppCompatActivity {

    private String username;
    private String employee;
    private String patient;

    private boolean current;

    private List<String> services;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference servicesRef = database.getReference("services");
    private DatabaseReference clinicsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_services);

        Intent intent = getIntent();
        username = (String)intent.getSerializableExtra("username");
        employee = (String)intent.getSerializableExtra("employee");
        patient = (String)intent.getSerializableExtra("patient");
        Serializable thing = intent.getSerializableExtra("current");
        if (thing != null) {
            current = (Boolean)thing;
        }

        if (employee != null) {
            if (!current) {
                ((Button)findViewById(R.id.addServiceButton)).setVisibility(View.GONE);
            }
            clinicsRef = database.getReference("clinics/" + username);
            readClinics();
        } else {
            if (patient != null) {
                ((Button) findViewById(R.id.addServiceButton)).setVisibility(View.GONE);
            }
            readServices();
        }
    }


    private void readClinics () {
        clinicsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String[] list = dataSnapshot.child("services").getValue().toString().split("_");
                services = Arrays.asList(list);
                readServices();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    private void readServices () {
        servicesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ((LinearLayout)findViewById(R.id.listOfServices)).removeAllViewsInLayout();
                for (DataSnapshot key : dataSnapshot.getChildren()) {
                    String name = key.child("name").getValue().toString();
                    String role = key.child("role").getValue().toString();
                    Service service = new Service(name, role);
                    if (employee != null) {
                        if (current && services.contains(name)) {
                            makeButton(service);
                        }
                        else if (!(current || services.contains(name))) {
                            makeButton(service);
                        }
                    } else {
                        makeButton(service);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    private void makeButton(final Service service) {
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.listOfServices);
        Button button = new Button(this);
        button.setText(service.getName());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SingleService.class);
                intent.putExtra("name", service.getName());
                intent.putExtra("role", service.getRole());
                if (employee != null) {
                    intent.putExtra("username", username);
                    intent.putExtra("employee", employee);
                    intent.putExtra("current", current);
                } else if (patient != null) {
                    intent.putExtra("username", username);
                    intent.putExtra("patient", patient);
                }
                startActivityForResult(intent, 0);
            }
        });
        linearLayout.addView(button);
    }


    public void onAddService (View view) {
        Intent intent;
        if (current) {
            intent = new Intent(getApplicationContext(), ViewServices.class);
            intent.putExtra("username", username);
            intent.putExtra("employee", employee);
            intent.putExtra("current", false);
        } else {
            intent = new Intent(getApplicationContext(), AddService.class);
        }
        startActivityForResult(intent, 0);
    }


    public void onBack (View view) {
        Intent intent;
        if (employee != null) {
            if (current) {
                intent = new Intent(getApplicationContext(), WelcomeEmployee.class);
            } else {
                intent = new Intent(getApplicationContext(), ViewServices.class);
                intent.putExtra("current", true);
            }
            intent.putExtra("username", username);
            intent.putExtra("employee", employee);
        } else if (patient != null) {
            intent = new Intent(getApplicationContext(), ClinicSearchType.class);
            intent.putExtra("username", username);
            intent.putExtra("patient", patient);
        } else {
            intent = new Intent(getApplicationContext(), WelcomeAdmin.class);
        }
        startActivityForResult(intent, 0);
    }

}
