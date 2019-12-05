package com.example.seg2105_f19_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Rate extends AppCompatActivity {

    private String username;
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

    private boolean needsCleaning = false;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("reviews");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        Intent intent = getIntent();
        username = (String)intent.getSerializableExtra("username");
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
    }


    public void onRate (View view) {
        if (needsCleaning) {
            ((TextView)findViewById(R.id.numText)).setText(" / 5");
            ((TextView)findViewById(R.id.numText)).setTypeface(null, Typeface.BOLD);
            ((TextView)findViewById(R.id.commentText)).setText(" / 5");
            ((TextView)findViewById(R.id.commentText)).setTypeface(null, Typeface.BOLD);
            needsCleaning = false;
        }

        String num = ((EditText)findViewById(R.id.numInput)).getText().toString();
        String comment = ((EditText)findViewById(R.id.commentInput)).getText().toString();

        if (num.equals("")) {
            needsCleaning = true;
            TextView textView = (TextView)findViewById(R.id.numText);
            textView.setText(textView.getText() + " *");
            textView.setTypeface(null, Typeface.BOLD);
        }
        else if (!num.matches("[\\d]")) {
            needsCleaning = true;
            TextView textView = (TextView)findViewById(R.id.numText);
            textView.setText(textView.getText() + " * Must be a single digit number.");
            textView.setTypeface(null, Typeface.BOLD);
        }
        else if (Integer.parseInt(num) > 5) {
            needsCleaning = true;
            TextView textView = (TextView)findViewById(R.id.numText);
            textView.setText(textView.getText() + " * Must be < 5.");
            textView.setTypeface(null, Typeface.BOLD);
        }
        if (comment.equals("")) {
            needsCleaning = true;
            TextView textView = (TextView)findViewById(R.id.commentText);
            textView.setText(textView.getText() + " *");
            textView.setTypeface(null, Typeface.BOLD);
        }

        if (needsCleaning) {
            return;
        }

        Review review = new Review(num, comment, clinic);
        ref.child(username + clinic).setValue(review);

        onCancel(view);
    }


    public void onCancel (View view) {
        Intent intent = new Intent(getApplicationContext(), ClinicInfo.class);
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

}
