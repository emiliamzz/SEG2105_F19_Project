package com.example.seg2105_f19_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class ViewClinics extends AppCompatActivity {

    private String patient;
    private String username;

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

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference clinicsRef = database.getReference("clinics");
    private DatabaseReference hoursRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_clinics);

        Intent intent = getIntent();
        patient = (String)intent.getSerializableExtra("patient");
        username = (String)intent.getSerializableExtra("username");
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
        readClinics();
    }


    private void readClinics () {
        clinicsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot key : dataSnapshot.getChildren()) {
                    String cName = key.child("name").getValue().toString();
                    String cAddress = key.child("address").getValue().toString();
                    String phone = key.child("phone").getValue().toString();
                    String insurance = key.child("insurance").getValue().toString();
                    String payment = key.child("payment").getValue().toString();
                    String services = key.child("services").getValue().toString();
                    String c = key.getKey();
                    Clinic clinic = new Clinic(cName, cAddress, phone, insurance, payment, services);
                    if (address != null) {
                        if (address.equals(cAddress)) {
                            makeButton(clinic, c);
                        }
                    } else if (sundayOpen != null) {
                        hoursRef = database.getReference("hours/" + key.getKey());
                        readHours(clinic, c);
                    } else if (service != null) {
                        String[] list = services.split("_");
                        List<String> s = Arrays.asList(list);
                        if (s.contains(service)) {
                            makeButton(clinic, c);
                        }
                    } else {
                        makeButton(clinic, c);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void readHours(final Clinic clinic, final String c) {
        hoursRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot key) {
                String hSundayOpen = key.child("sundayOpen").getValue().toString();
                String hSundayClose = key.child("sundayClose").getValue().toString();
                String hMondayOpen = key.child("mondayOpen").getValue().toString();
                String hMondayClose = key.child("mondayClose").getValue().toString();
                String hTuesdayOpen = key.child("tuesdayOpen").getValue().toString();
                String hTuesdayClose = key.child("tuesdayClose").getValue().toString();
                String hWednesdayOpen = key.child("wednesdayOpen").getValue().toString();
                String hWednesdayClose = key.child("wednesdayClose").getValue().toString();
                String hThursdayOpen = key.child("thursdayOpen").getValue().toString();
                String hThursdayClose = key.child("thursdayClose").getValue().toString();
                String hFridayOpen = key.child("fridayOpen").getValue().toString();
                String hFridayClose = key.child("fridayClose").getValue().toString();
                String hSaturdayOpen = key.child("saturdayOpen").getValue().toString();
                String hSaturdayClose = key.child("saturdayClose").getValue().toString();

                if (!(sundayOpen.equals("") || hSundayOpen.equals(""))) {
                    if (!(Integer.parseInt(sundayOpen.substring(0,2)) > Integer.parseInt(hSundayClose.substring(0,2)) ||
                            Integer.parseInt(sundayClose.substring(0,2)) < Integer.parseInt(hSundayOpen.substring(0,2)))) {
                        if (!(sundayOpen.substring(0,2).equals(hSundayClose.substring(0,2)) ||
                                Integer.parseInt(sundayOpen.substring(3)) >= Integer.parseInt(hSundayClose.substring(3)))) {
                            if (!(sundayClose.substring(0,2).equals(hSundayOpen.substring(0,2)) ||
                                    Integer.parseInt(sundayClose.substring(3)) <= Integer.parseInt(hSundayOpen.substring(3)))) {
                                makeButton(clinic, c);
                                return;
                            }
                        }
                    }
                }
                if (!(mondayOpen.equals("") || hMondayOpen.equals(""))) {
                    if (!(Integer.parseInt(mondayOpen.substring(0,2)) > Integer.parseInt(hMondayClose.substring(0,2)) ||
                            Integer.parseInt(mondayClose.substring(0,2)) < Integer.parseInt(hMondayOpen.substring(0,2)))) {
                        if (!(mondayOpen.substring(0,2).equals(hMondayClose.substring(0,2)) ||
                                Integer.parseInt(mondayOpen.substring(3)) >= Integer.parseInt(hMondayClose.substring(3)))) {
                            if (!(mondayClose.substring(0,2).equals(hMondayOpen.substring(0,2)) ||
                                    Integer.parseInt(mondayClose.substring(3)) <= Integer.parseInt(hMondayOpen.substring(3)))) {
                                makeButton(clinic, c);
                                return;
                            }
                        }
                    }
                }
                if (!(tuesdayOpen.equals("") || hTuesdayOpen.equals(""))) {
                    if (!(Integer.parseInt(tuesdayOpen.substring(0,2)) > Integer.parseInt(hTuesdayClose.substring(0,2)) ||
                            Integer.parseInt(tuesdayClose.substring(0,2)) < Integer.parseInt(hTuesdayOpen.substring(0,2)))) {
                        if (!(tuesdayOpen.substring(0,2).equals(hTuesdayClose.substring(0,2)) ||
                                Integer.parseInt(tuesdayOpen.substring(3)) >= Integer.parseInt(hTuesdayClose.substring(3)))) {
                            if (!(tuesdayClose.substring(0,2).equals(hTuesdayOpen.substring(0,2)) ||
                                    Integer.parseInt(tuesdayClose.substring(3)) <= Integer.parseInt(hTuesdayOpen.substring(3)))) {
                                makeButton(clinic, c);
                                return;
                            }
                        }
                    }
                }
                if (!(wednesdayOpen.equals("") || hWednesdayOpen.equals(""))) {
                    if (!(Integer.parseInt(wednesdayOpen.substring(0,2)) > Integer.parseInt(hWednesdayClose.substring(0,2)) ||
                            Integer.parseInt(wednesdayClose.substring(0,2)) < Integer.parseInt(hWednesdayOpen.substring(0,2)))) {
                        if (!(wednesdayOpen.substring(0,2).equals(hWednesdayClose.substring(0,2)) ||
                                Integer.parseInt(wednesdayOpen.substring(3)) >= Integer.parseInt(hWednesdayClose.substring(3)))) {
                            if (!(wednesdayClose.substring(0,2).equals(hWednesdayOpen.substring(0,2)) ||
                                    Integer.parseInt(wednesdayClose.substring(3)) <= Integer.parseInt(hWednesdayOpen.substring(3)))) {
                                makeButton(clinic, c);
                                return;
                            }
                        }
                    }
                }
                if (!(thursdayOpen.equals("") || hThursdayOpen.equals(""))) {
                    if (!(Integer.parseInt(thursdayOpen.substring(0,2)) > Integer.parseInt(hThursdayClose.substring(0,2)) ||
                            Integer.parseInt(thursdayClose.substring(0,2)) < Integer.parseInt(hThursdayOpen.substring(0,2)))) {
                        if (!(thursdayOpen.substring(0,2).equals(hThursdayClose.substring(0,2)) ||
                                Integer.parseInt(thursdayOpen.substring(3)) >= Integer.parseInt(hThursdayClose.substring(3)))) {
                            if (!(thursdayClose.substring(0,2).equals(hThursdayOpen.substring(0,2)) ||
                                    Integer.parseInt(thursdayClose.substring(3)) <= Integer.parseInt(hThursdayOpen.substring(3)))) {
                                makeButton(clinic, c);
                                return;
                            }
                        }
                    }
                }
                if (!(fridayOpen.equals("") || hFridayOpen.equals(""))) {
                    if (!(Integer.parseInt(fridayOpen.substring(0,2)) > Integer.parseInt(hFridayClose.substring(0,2)) ||
                            Integer.parseInt(fridayClose.substring(0,2)) < Integer.parseInt(hFridayOpen.substring(0,2)))) {
                        if (!(fridayOpen.substring(0,2).equals(hFridayClose.substring(0,2)) ||
                                Integer.parseInt(fridayOpen.substring(3)) >= Integer.parseInt(hFridayClose.substring(3)))) {
                            if (!(fridayClose.substring(0,2).equals(hFridayOpen.substring(0,2)) ||
                                    Integer.parseInt(fridayClose.substring(3)) <= Integer.parseInt(hFridayOpen.substring(3)))) {
                                makeButton(clinic, c);
                                return;
                            }
                        }
                    }
                }
                if (!(saturdayOpen.equals("") || hSaturdayOpen.equals(""))) {
                    if (!(Integer.parseInt(saturdayOpen.substring(0,2)) > Integer.parseInt(hSaturdayClose.substring(0,2)) ||
                            Integer.parseInt(saturdayClose.substring(0,2)) < Integer.parseInt(hSaturdayOpen.substring(0,2)))) {
                        if (!(saturdayOpen.substring(0,2).equals(hSaturdayClose.substring(0,2)) ||
                                Integer.parseInt(saturdayOpen.substring(3)) >= Integer.parseInt(hSaturdayClose.substring(3)))) {
                            if (!(saturdayClose.substring(0,2).equals(hSaturdayOpen.substring(0,2)) ||
                                    Integer.parseInt(saturdayClose.substring(3)) <= Integer.parseInt(hSaturdayOpen.substring(3)))) {
                                makeButton(clinic, c);
                                return;
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    private void makeButton(final Clinic clinic, final String c) {
        ((TextView)findViewById(R.id.noResults)).setVisibility(View.GONE);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.listOfClinics);
        Button button = new Button(this);
        button.setText(clinic.getName());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClinicInfo.class);
                intent.putExtra("clinic", c);
                intent.putExtra("patient", patient);
                intent.putExtra("username", username);
                if (address != null) {
                    intent.putExtra("address", address);
                } else if (sundayOpen != null) {
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
        });
        linearLayout.addView(button);
    }


    public void onBack (View view) {
        Intent intent;
        if (address != null) {
            intent = new Intent(getApplicationContext(), AddressSearch.class);
        } else if (sundayOpen != null) {
            intent = new Intent(getApplicationContext(), AddHours.class);
        } else if (service != null) {
            intent = new Intent(getApplicationContext(), ViewServices.class);
        } else {
            intent = new Intent(getApplicationContext(), ClinicSearchType.class);
        }
        intent.putExtra("patient", patient);
        intent.putExtra("username", username);
        startActivityForResult(intent, 0);
    }

}
