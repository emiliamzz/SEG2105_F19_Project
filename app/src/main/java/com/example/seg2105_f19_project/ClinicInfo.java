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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClinicInfo extends AppCompatActivity {

    private String username;
    private String employee;
    private String patient;

    private String clinic;

    private String address;

    private String sundayOpen;
    private String sundayClose;
    private String mondayOpen;
    private String mondayClose;
    private String tuesdayOpen;
    private String tuesdayClose;
    private String wednesdayOpen;
    private String wednesdayClose;
    private String thursdayOpen;
    private String thursdayClose;
    private String fridayOpen;
    private String fridayClose;
    private String saturdayOpen;
    private String saturdayClose;

    private String service;

    private int rating = 0;
    private int minutes = 0;
    private Appointment appointment;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reviewsRef = database.getReference("reviews");
    private DatabaseReference appointmentsRef = database.getReference("appointments");
    private DatabaseReference clinicsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_info);

        Intent intent = getIntent();
        username = (String)intent.getSerializableExtra("username");
        employee = (String)intent.getSerializableExtra("employee");
        patient = (String)intent.getSerializableExtra("patient");
        clinic = (String)intent.getSerializableExtra("clinic");
        address = (String)intent.getSerializableExtra("address");
        sundayOpen = (String)intent.getSerializableExtra("sundayOpen");
        sundayClose = (String)intent.getSerializableExtra("sundayClose");
        mondayOpen = (String)intent.getSerializableExtra("mondayOpen");
        mondayClose = (String)intent.getSerializableExtra("mondayClose");
        tuesdayOpen = (String)intent.getSerializableExtra("tuesdayOpen");
        tuesdayClose = (String)intent.getSerializableExtra("tuesdayClose");
        wednesdayOpen = (String)intent.getSerializableExtra("wednesdayOpen");
        wednesdayClose = (String)intent.getSerializableExtra("wednesdayClose");
        thursdayOpen = (String)intent.getSerializableExtra("thursdayOpen");
        thursdayClose = (String)intent.getSerializableExtra("thursdayClose");
        fridayOpen = (String)intent.getSerializableExtra("fridayOpen");
        fridayClose = (String)intent.getSerializableExtra("fridayClose");
        saturdayOpen = (String)intent.getSerializableExtra("saturdayOpen");
        saturdayClose = (String)intent.getSerializableExtra("saturdayClose");
        service = (String)intent.getSerializableExtra("service");

        if (employee != null) {
            clinicsRef = database.getReference("clinics/" + username);
            ((Button)findViewById(R.id.bookAppointmentButton)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.checkInButton)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.rateButton)).setVisibility(View.GONE);
        } else {
            clinicsRef = database.getReference("clinics/" + clinic);
        }
        readReviews();
    }


    private void readClinic () {
        clinicsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String address = dataSnapshot.child("address").getValue().toString();
                String phone = dataSnapshot.child("phone").getValue().toString();
                String insurance = dataSnapshot.child("insurance").getValue().toString();
                String payment = dataSnapshot.child("payment").getValue().toString();
                ((TextView)findViewById(R.id.infoText)).setText("Clinic name: " + name + "\n" +
                        "Address: " + address + "\n" +
                        "Phone number: " + phone + "\n" +
                        "Accepted insurance types: " + insurance + "\n" +
                        "Accepted payment methods: " + payment + "\n" +
                        "Rating: " + rating + " / 5" + "\n" +
                        "Wait time: " + minutes + " minutes");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    private void readReviews () {
        reviewsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int total = 0;
                for (DataSnapshot key : dataSnapshot.getChildren()) {
                    if (key.child("clinic").getValue().toString().equals(clinic)) {
                        total += 1;
                        rating += Integer.parseInt(key.child("num").getValue().toString());
                    }
                }
                if (total > 0) {
                    rating = rating / total;
                }
                readAppointments();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    private void readAppointments () {
        appointmentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String date = java.time.LocalDate.now().toString();
                int year = Integer.parseInt(date.substring(0,4));
                int month = Integer.parseInt(date.substring(5,7));
                int day = Integer.parseInt(date.substring(8));
                String t = java.time.LocalTime.now().toString();
                int time = Integer.parseInt(t.substring(0,2)) * 60 + Integer.parseInt(t.substring(3,5));
                List<String> delete = new ArrayList<>();
                List<Integer> apps = new ArrayList<>();
                for (DataSnapshot key : dataSnapshot.getChildren()) {
                    String da = key.child("date").getValue().toString();
                    int y = Integer.parseInt(da.substring(0,4));
                    int m = Integer.parseInt(da.substring(5,7));
                    int d = Integer.parseInt(da.substring(8));
                    if (da.equals(date)) {
                        t = key.child("time").getValue().toString();
                        int st = Integer.parseInt(t.substring(0,2)) * 60 + Integer.parseInt(t.substring(3,5));
                        if (st + 15 <= time) {
                            delete.add(key.getKey());
                        } else {
                            apps.add(st);
                        }
                    }
                    else if ((y < year) || (y == year && m < month) || (y == year && m == month && d < day)) {
                        delete.add(key.getKey());
                    }
                }
                Collections.sort(apps);
                int next = time;
                for (int app : apps) {
                    if (app - next < 15 || next - app < 15) {
                        next = app + 15;
                    } else {
                        break;
                    }
                }
                minutes = next - time;
                    for (int app : apps) {
                        if (app - time < 15 || time - app < 15) {
                            time = app + 15;
                        } else {
                            break;
                        }
                    }
                    int hours = time / 60;
                    int min = time % 60;
                    String q;
                    if (min < 10) {
                        q = hours + ":0" + min;
                    } else {
                        q = hours + ":" + min;
                    }
                    appointment = new Appointment(date, q, clinic);
                readClinic();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    public void onBookAppointment (View view) {
        Intent intent = new Intent(getApplicationContext(), BookAppointment.class);
        intent.putExtra("username", username);
        intent.putExtra("patient", patient);
        intent.putExtra("clinic", clinic);
        startActivityForResult(intent, 0);
    }


    public void onCheckIn (View view) {
        appointmentsRef.child(username).setValue(appointment);
        Intent intent = new Intent(getApplicationContext(), WelcomeScreen.class);
        intent.putExtra("username", username);
        intent.putExtra("patient", patient);
        intent.putExtra("status", "checked in!");
        startActivityForResult(intent, 0);
    }


    public void onRate (View view) {
        Intent intent = new Intent(getApplicationContext(), Rate.class);
        intent.putExtra("patient", patient);
        intent.putExtra("username", username);
        intent.putExtra("clinic", clinic);
        if (address != null) {
            intent.putExtra("address", address);
        }
        else if (sundayOpen != null) {
            intent.putExtra("sundayOpen", sundayOpen);
            intent.putExtra("sundayClose", sundayClose);
            intent.putExtra("mondayOpen", mondayOpen);
            intent.putExtra("mondayClose", mondayClose);
            intent.putExtra("tuesdayOpen", tuesdayOpen);
            intent.putExtra("tuesdayClose", tuesdayClose);
            intent.putExtra("wednesdayOpen", wednesdayOpen);
            intent.putExtra("wednesdayClose", wednesdayClose);
            intent.putExtra("thursdayOpen", thursdayOpen);
            intent.putExtra("thursdayClose", thursdayClose);
            intent.putExtra("fridayOpen", fridayOpen);
            intent.putExtra("fridayClose", fridayClose);
            intent.putExtra("saturdayOpen", saturdayOpen);
            intent.putExtra("saturdayClose", saturdayClose);
        } else if (service != null) {
            intent.putExtra("service", service);
        }
        startActivityForResult(intent, 0);
    }


    public void onBack(View view) {
        Intent intent;
        if (patient != null) {
            intent = new Intent(getApplicationContext(), ViewClinics.class);
            intent.putExtra("patient", patient);
            if (address != null) {
                intent.putExtra("address", address);
            }
            else if (sundayOpen != null) {
                intent.putExtra("sundayOpen", sundayOpen);
                intent.putExtra("sundayClose", sundayClose);
                intent.putExtra("mondayOpen", mondayOpen);
                intent.putExtra("mondayClose", mondayClose);
                intent.putExtra("tuesdayOpen", tuesdayOpen);
                intent.putExtra("tuesdayClose", tuesdayClose);
                intent.putExtra("wednesdayOpen", wednesdayOpen);
                intent.putExtra("wednesdayClose", wednesdayClose);
                intent.putExtra("thursdayOpen", thursdayOpen);
                intent.putExtra("thursdayClose", thursdayClose);
                intent.putExtra("fridayOpen", fridayOpen);
                intent.putExtra("fridayClose", fridayClose);
                intent.putExtra("saturdayOpen", saturdayOpen);
                intent.putExtra("saturdayClose", saturdayClose);
            } else if (service != null) {
                intent.putExtra("service", service);
            }
        } else {
            intent = new Intent(getApplicationContext(), WelcomeEmployee.class);
            intent.putExtra("employee", employee);
        }
        intent.putExtra("username", username);
        startActivityForResult(intent, 0);
    }

}
