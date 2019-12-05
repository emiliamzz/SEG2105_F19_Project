package com.example.seg2105_f19_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddHours extends AppCompatActivity {

    private boolean needsCleaning = false;

    private String name;
    private String address;
    private String phone;
    private String insurance;
    private String payment;

    private String username;
    private String employee;
    private String patient;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference hoursRef = database.getReference("hours");
    private DatabaseReference clinicsRef = database.getReference("clinics");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hours);

        Intent intent = getIntent();
        name = (String)intent.getSerializableExtra("name");
        address = (String)intent.getSerializableExtra("address");
        phone = (String)intent.getSerializableExtra("phone");
        insurance = (String)intent.getSerializableExtra("insurance");
        payment = (String)intent.getSerializableExtra("payment");
        username = (String)intent.getSerializableExtra("username");
        employee = (String)intent.getSerializableExtra("employee");
        patient = (String)intent.getSerializableExtra("patient");

        if (patient != null) {
            ((Button)findViewById(R.id.saveButton)).setText("Search");
            ((TextView)findViewById(R.id.titleText)).setText("Search By Hours");
        }
        else if (name == null) {
            readHours();
        }
    }

    private void readHours() {
        hoursRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot key) {
                String sundayOpen = key.child(username + "/sundayOpen").getValue().toString();
                String sundayClose = key.child(username + "/sundayClose").getValue().toString();
                String mondayOpen = key.child(username + "/mondayOpen").getValue().toString();
                String mondayClose = key.child(username + "/mondayClose").getValue().toString();
                String tuesdayOpen = key.child(username + "/tuesdayOpen").getValue().toString();
                String tuesdayClose = key.child(username + "/tuesdayClose").getValue().toString();
                String wednesdayOpen = key.child(username + "/wednesdayOpen").getValue().toString();
                String wednesdayClose = key.child(username + "/wednesdayClose").getValue().toString();
                String thursdayOpen = key.child(username + "/thursdayOpen").getValue().toString();
                String thursdayClose = key.child(username + "/thursdayClose").getValue().toString();
                String fridayOpen = key.child(username + "/fridayOpen").getValue().toString();
                String fridayClose = key.child(username + "/fridayClose").getValue().toString();
                String saturdayOpen = key.child(username + "/saturdayOpen").getValue().toString();
                String saturdayClose = key.child(username + "/saturdayClose").getValue().toString();

                if (!sundayOpen.equals("")) {
                    ((EditText)findViewById(R.id.sundayOpen)).setText(sundayOpen);
                    ((EditText)findViewById(R.id.sundayClose)).setText(sundayClose);
                    ((CheckBox)findViewById(R.id.sundayCheck)).setChecked(true);
                }
                if (!mondayOpen.equals("")) {
                    ((EditText)findViewById(R.id.mondayOpen)).setText(mondayOpen);
                    ((EditText)findViewById(R.id.mondayClose)).setText(mondayClose);
                    ((CheckBox)findViewById(R.id.mondayCheck)).setChecked(true);
                }
                if (!tuesdayOpen.equals("")) {
                    ((EditText)findViewById(R.id.tuesdayOpen)).setText(tuesdayOpen);
                    ((EditText)findViewById(R.id.tuesdayClose)).setText(tuesdayClose);
                    ((CheckBox)findViewById(R.id.tuesdayCheck)).setChecked(true);
                }
                if (!wednesdayOpen.equals("")) {
                    ((EditText)findViewById(R.id.wednesdayOpen)).setText(wednesdayOpen);
                    ((EditText)findViewById(R.id.wednesdayClose)).setText(wednesdayClose);
                    ((CheckBox)findViewById(R.id.wednesdayCheck)).setChecked(true);
                }
                if (!thursdayOpen.equals("")) {
                    ((EditText)findViewById(R.id.thursdayOpen)).setText(thursdayOpen);
                    ((EditText)findViewById(R.id.thursdayClose)).setText(thursdayClose);
                    ((CheckBox)findViewById(R.id.thursdayCheck)).setChecked(true);
                }
                if (!fridayOpen.equals("")) {
                    ((EditText)findViewById(R.id.fridayOpen)).setText(fridayOpen);
                    ((EditText)findViewById(R.id.fridayClose)).setText(fridayClose);
                    ((CheckBox)findViewById(R.id.fridayCheck)).setChecked(true);
                }
                if (!saturdayOpen.equals("")) {
                    ((EditText)findViewById(R.id.saturdayOpen)).setText(saturdayOpen);
                    ((EditText)findViewById(R.id.saturdayClose)).setText(saturdayClose);
                    ((CheckBox)findViewById(R.id.saturdayCheck)).setChecked(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    public void onSave (View view) {
        if (needsCleaning) {
            ((TextView)findViewById(R.id.sundayText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.sundayText)).setText("Sunday:");
            ((TextView)findViewById(R.id.mondayText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.mondayText)).setText("Monday:");
            ((TextView)findViewById(R.id.tuesdayText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.tuesdayText)).setText("Tuesday:");
            ((TextView)findViewById(R.id.wednesdayText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.wednesdayText)).setText("Wednesday:");
            ((TextView)findViewById(R.id.thursdayText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.thursdayText)).setText("Thursday:");
            ((TextView)findViewById(R.id.fridayText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.fridayText)).setText("Friday:");
            ((TextView)findViewById(R.id.saturdayText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.saturdayText)).setText("Saturday:");
            ((TextView)findViewById(R.id.checkText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.checkText)).setText("Check if open");
            needsCleaning = false;
        }

        String sundayOpen = ((EditText)findViewById(R.id.sundayOpen)).getText().toString();
        String sundayClose = ((EditText)findViewById(R.id.sundayClose)).getText().toString();
        String mondayOpen = ((EditText)findViewById(R.id.mondayOpen)).getText().toString();
        String mondayClose = ((EditText)findViewById(R.id.mondayClose)).getText().toString();
        String tuesdayOpen = ((EditText)findViewById(R.id.tuesdayOpen)).getText().toString();
        String tuesdayClose = ((EditText)findViewById(R.id.tuesdayClose)).getText().toString();
        String wednesdayOpen = ((EditText)findViewById(R.id.wednesdayOpen)).getText().toString();
        String wednesdayClose = ((EditText)findViewById(R.id.wednesdayClose)).getText().toString();
        String thursdayOpen = ((EditText)findViewById(R.id.thursdayOpen)).getText().toString();
        String thursdayClose = ((EditText)findViewById(R.id.thursdayClose)).getText().toString();
        String fridayOpen = ((EditText)findViewById(R.id.fridayOpen)).getText().toString();
        String fridayClose = ((EditText)findViewById(R.id.fridayClose)).getText().toString();
        String saturdayOpen = ((EditText)findViewById(R.id.saturdayOpen)).getText().toString();
        String saturdayClose = ((EditText)findViewById(R.id.saturdayClose)).getText().toString();

        boolean isOpen = false;
        if (((CheckBox)findViewById(R.id.sundayCheck)).isChecked()) {
            isOpen = true;
            if (sundayOpen.equals("") || sundayClose.equals("")) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.sundayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* " + textView.getText());
            }
            else if (!(sundayOpen.matches("\\d{2}:\\d{2}") && sundayClose.matches("\\d{2}:\\d{2}"))){
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.sundayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* Must be in the format hh:mm");
            }
            else if (Integer.parseInt(sundayOpen.substring(0,2)) > 23 || Integer.parseInt(sundayClose.substring(0,2)) > 23) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.sundayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* Max amount of hours is 23.");
            }
            else if (Integer.parseInt(sundayOpen.substring(3)) > 59 || Integer.parseInt(sundayClose.substring(3)) > 59) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.sundayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* Max amount of minutes is 59.");
            }
            else if (Integer.parseInt(sundayClose.substring(0,2)) < Integer.parseInt(sundayOpen.substring(0,2))) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.sundayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* The closing time must be after the opening time.");
            }
            else if (sundayClose.substring(0,2).equals(sundayOpen.substring(0,2)) &&
                    Integer.parseInt(sundayClose.substring(3)) <= Integer.parseInt(sundayOpen.substring(3))) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.sundayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* The closing time must be after the opening time.");
            }
        } else {
            sundayOpen = "";
            sundayClose = "";
        }

        if (((CheckBox)findViewById(R.id.mondayCheck)).isChecked()) {
            isOpen = true;
            if (mondayOpen.equals("") || mondayClose.equals("")) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.mondayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* " + textView.getText());
            }
            else if (!(mondayOpen.matches("\\d{2}:\\d{2}") && mondayClose.matches("\\d{2}:\\d{2}"))){
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.mondayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* Must be in the format hh:mm");
            }
            else if (Integer.parseInt(mondayOpen.substring(0,2)) > 23 || Integer.parseInt(mondayClose.substring(0,2)) > 23) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.mondayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* Max amount of hours is 23.");
            }
            else if (Integer.parseInt(mondayOpen.substring(3)) > 59 || Integer.parseInt(mondayClose.substring(3)) > 59) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.mondayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* Max amount of minutes is 59.");
            }
            else if (Integer.parseInt(mondayClose.substring(0,2)) < Integer.parseInt(mondayOpen.substring(0,2))) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.mondayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* The closing time must be after the opening time.");
            }
            else if (mondayClose.substring(0,2).equals(mondayOpen.substring(0,2)) &&
                    Integer.parseInt(mondayClose.substring(3)) <= Integer.parseInt(mondayOpen.substring(3))) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.mondayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* The closing time must be after the opening time.");
            }
        } else {
            mondayOpen = "";
            mondayClose = "";
        }

        if (((CheckBox)findViewById(R.id.tuesdayCheck)).isChecked()) {
            isOpen = true;
            if (tuesdayOpen.equals("") || tuesdayClose.equals("")) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.tuesdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* " + textView.getText());
            }
            else if (!(tuesdayOpen.matches("\\d{2}:\\d{2}") && tuesdayClose.matches("\\d{2}:\\d{2}"))){
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.tuesdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* Must be in the format hh:mm");
            }
            else if (Integer.parseInt(tuesdayOpen.substring(0,2)) > 23 || Integer.parseInt(tuesdayClose.substring(0,2)) > 23) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.tuesdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* Max amount of hours is 23.");
            }
            else if (Integer.parseInt(tuesdayOpen.substring(3)) > 59 || Integer.parseInt(tuesdayClose.substring(3)) > 59) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.tuesdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* Max amount of minutes is 59.");
            }
            else if (Integer.parseInt(tuesdayClose.substring(0,2)) < Integer.parseInt(tuesdayOpen.substring(0,2))) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.tuesdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* The closing time must be after the opening time.");
            }
            else if (tuesdayClose.substring(0,2).equals(tuesdayOpen.substring(0,2)) &&
                    Integer.parseInt(tuesdayClose.substring(3)) <= Integer.parseInt(tuesdayOpen.substring(3))) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.tuesdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* The closing time must be after the opening time.");
            }
        } else {
            tuesdayOpen = "";
            tuesdayClose = "";
        }

        if (((CheckBox)findViewById(R.id.wednesdayCheck)).isChecked()) {
            isOpen = true;
            if (wednesdayOpen.equals("") || wednesdayClose.equals("")) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.wednesdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* " + textView.getText());
            }
            else if (!(wednesdayOpen.matches("\\d{2}:\\d{2}") && wednesdayClose.matches("\\d{2}:\\d{2}"))){
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.wednesdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* Must be in the format hh:mm");
            }
            else if (Integer.parseInt(wednesdayOpen.substring(0,2)) > 23 || Integer.parseInt(wednesdayClose.substring(0,2)) > 23) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.wednesdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* Max amount of hours is 23.");
            }
            else if (Integer.parseInt(wednesdayOpen.substring(3)) > 59 || Integer.parseInt(wednesdayClose.substring(3)) > 59) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.wednesdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* Max amount of minutes is 59.");
            }
            else if (Integer.parseInt(wednesdayClose.substring(0,2)) < Integer.parseInt(wednesdayOpen.substring(0,2))) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.wednesdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* The closing time must be after the opening time.");
            }
            else if (wednesdayClose.substring(0,2).equals(wednesdayOpen.substring(0,2)) &&
                    Integer.parseInt(wednesdayClose.substring(3)) <= Integer.parseInt(wednesdayOpen.substring(3))) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.wednesdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* The closing time must be after the opening time.");
            }
        } else {
            wednesdayOpen = "";
            wednesdayClose = "";
        }

        if (((CheckBox)findViewById(R.id.thursdayCheck)).isChecked()) {
            isOpen = true;
            if (thursdayOpen.equals("") || thursdayClose.equals("")) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.thursdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* " + textView.getText());
            }
            else if (!(thursdayOpen.matches("\\d{2}:\\d{2}") && thursdayClose.matches("\\d{2}:\\d{2}"))){
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.thursdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* Must be in the format hh:mm");
            }
            else if (Integer.parseInt(thursdayOpen.substring(0,2)) > 23 || Integer.parseInt(thursdayClose.substring(0,2)) > 23) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.thursdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* Max amount of hours is 23.");
            }
            else if (Integer.parseInt(thursdayOpen.substring(3)) > 59 || Integer.parseInt(thursdayClose.substring(3)) > 59) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.thursdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* Max amount of minutes is 59.");
            }
            else if (Integer.parseInt(thursdayClose.substring(0,2)) < Integer.parseInt(thursdayOpen.substring(0,2))) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.thursdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* The closing time must be after the opening time.");
            }
            else if (thursdayClose.substring(0,2).equals(thursdayOpen.substring(0,2)) &&
                    Integer.parseInt(thursdayClose.substring(3)) <= Integer.parseInt(thursdayOpen.substring(3))) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.thursdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* The closing time must be after the opening time.");
            }
        } else {
            thursdayOpen = "";
            thursdayClose = "";
        }

        if (((CheckBox)findViewById(R.id.fridayCheck)).isChecked()) {
            isOpen = true;
            if (fridayOpen.equals("") || fridayClose.equals("")) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.fridayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* " + textView.getText());
            }
            else if (!(fridayOpen.matches("\\d{2}:\\d{2}") && fridayClose.matches("\\d{2}:\\d{2}"))){
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.fridayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* Must be in the format hh:mm");
            }
            else if (Integer.parseInt(fridayOpen.substring(0,2)) > 23 || Integer.parseInt(fridayClose.substring(0,2)) > 23) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.fridayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* Max amount of hours is 23.");
            }
            else if (Integer.parseInt(fridayOpen.substring(3)) > 59 || Integer.parseInt(fridayClose.substring(3)) > 59) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.fridayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* Max amount of minutes is 59.");
            }
            else if (Integer.parseInt(fridayClose.substring(0,2)) < Integer.parseInt(fridayOpen.substring(0,2))) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.fridayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* The closing time must be after the opening time.");
            }
            else if (fridayClose.substring(0,2).equals(fridayOpen.substring(0,2)) &&
                    Integer.parseInt(fridayClose.substring(3)) <= Integer.parseInt(fridayOpen.substring(3))) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.fridayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* The closing time must be after the opening time.");
            }
        } else {
            fridayOpen = "";
            fridayClose = "";
        }

        if (((CheckBox)findViewById(R.id.saturdayCheck)).isChecked()) {
            isOpen = true;
            if (saturdayOpen.equals("") || saturdayClose.equals("")) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.saturdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* " + textView.getText());
            }
            else if (!(saturdayOpen.matches("\\d{2}:\\d{2}") && saturdayClose.matches("\\d{2}:\\d{2}"))){
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.saturdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* Must be in the format hh:mm");
            }
            else if (Integer.parseInt(saturdayOpen.substring(0,2)) > 23 || Integer.parseInt(saturdayClose.substring(0,2)) > 23) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.saturdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* Max amount of hours is 23.");
            }
            else if (Integer.parseInt(saturdayOpen.substring(3)) > 59 || Integer.parseInt(saturdayClose.substring(3)) > 59) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.saturdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* Max amount of minutes is 59.");
            }
            else if (Integer.parseInt(saturdayClose.substring(0,2)) < Integer.parseInt(saturdayOpen.substring(0,2))) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.saturdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* The closing time must be after the opening time.");
            }
            else if (saturdayClose.substring(0,2).equals(saturdayOpen.substring(0,2)) &&
                    Integer.parseInt(saturdayClose.substring(3)) <= Integer.parseInt(saturdayOpen.substring(3))) {
                needsCleaning = true;
                TextView textView = (TextView)findViewById(R.id.saturdayText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* The closing time must be after the opening time.");
            }
        } else {
            saturdayOpen = "";
            saturdayClose = "";
        }

        if (!isOpen) {
            needsCleaning = true;
            TextView textView = (TextView)findViewById(R.id.checkText);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText("* " + textView.getText());
            return;
        }

        if (needsCleaning) {
            return;
        }

        Hours hours = new Hours(sundayOpen, sundayClose, mondayOpen, mondayClose, tuesdayOpen,
                tuesdayClose, wednesdayOpen, wednesdayClose, thursdayOpen, thursdayClose,
                fridayOpen, fridayClose, saturdayOpen, saturdayClose);

        if (patient != null) {
            Intent intent = new Intent(getApplicationContext(), ViewClinics.class);
            intent.putExtra("username", username);
            intent.putExtra("patient", patient);
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
            startActivityForResult(intent, 0);
        }
        else if (name == null) {
            hoursRef.child(username).setValue(hours);
            onBack(view);
        } else {
            Clinic clinic = new Clinic(name, address,phone, insurance, payment, "");
            clinicsRef.child(username).setValue(clinic);
            hoursRef.child(username).setValue(hours);

            Intent intent = new Intent(getApplicationContext(), WelcomeEmployee.class);
            intent.putExtra("username", username);
            intent.putExtra("employee", employee);
            startActivityForResult(intent, 0);
        }
    }


    public void onBack (View view) {
        Intent intent;
        if (patient != null) {
            intent = new Intent(getApplicationContext(), ClinicSearchType.class);
            intent.putExtra("patient", patient);
        }
        else if (name == null) {
            intent = new Intent(getApplicationContext(), ViewHours.class);
            intent.putExtra("employee", employee);
        } else {
            intent = new Intent(getApplicationContext(), CreateClinic.class);
            intent.putExtra("employee", employee);
            intent.putExtra("name", name);
            intent.putExtra("address", address);
            intent.putExtra("phone", phone);
            intent.putExtra("insurance", insurance);
            intent.putExtra("payment", payment);
        }
        intent.putExtra("username", username);
        startActivityForResult(intent, 0);
    }

}
