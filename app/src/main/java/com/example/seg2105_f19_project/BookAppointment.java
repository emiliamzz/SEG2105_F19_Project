package com.example.seg2105_f19_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BookAppointment extends AppCompatActivity {

    private boolean needsCleaning = false;

    private String patient;
    private String username;

    private String clinic;

    private String dayo;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("appointments");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        Intent intent = getIntent();
        patient = (String)intent.getSerializableExtra("patient");
        username = (String)intent.getSerializableExtra("username");
        clinic = (String)intent.getSerializableExtra("clinic");

        CalendarView view = ((CalendarView)findViewById(R.id.calendarView));
        view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view1, int year, int month,
                                            int date) {
                month += 1;
                dayo = year + "-" + month + "-" + date;
            }
        });


    }


    public void onBook (View view) {
        if (needsCleaning) {
            ((TextView)findViewById(R.id.timeText)).setText("Choose a time:");
            ((TextView)findViewById(R.id.timeText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.calendarText)).setText("Choose a date:");
            ((TextView)findViewById(R.id.calendarText)).setTypeface(null, Typeface.NORMAL);
            needsCleaning = false;
        }

        String time = ((EditText)findViewById(R.id.timeInput)).getText().toString();

        int year = Integer.parseInt(dayo.substring(0,4));
        int month = Integer.parseInt(dayo.substring(5,7));
        int day = Integer.parseInt(dayo.substring(8));

        String da = java.time.LocalDate.now().toString();
        int y = Integer.parseInt(da.substring(0,4));
        int m = Integer.parseInt(da.substring(5,7));
        int d = Integer.parseInt(da.substring(8));

        if (time.equals("")) {
            needsCleaning = true;
            TextView textView = (TextView)findViewById(R.id.timeText);
            textView.setText("* " + textView.getText());
            textView.setTypeface(null, Typeface.BOLD);
        }
        else if (!(time.matches("\\d{2}:\\d{2}"))) {
            needsCleaning = true;
            TextView textView = (TextView)findViewById(R.id.timeText);
            textView.setText("* Must be in the format hh:mm.");
            textView.setTypeface(null, Typeface.BOLD);
        }
        if ((dayo.equals(da)) || (year < y) || (year == y && month < m) || (year == y && month == m && day < d)) {
            needsCleaning = true;
            TextView textView = (TextView)findViewById(R.id.calendarText);
            textView.setText("* Must be at a later date. No same-day appointments.");
            textView.setTypeface(null, Typeface.BOLD);
        }

        if (needsCleaning) {
            return;
        }

        Appointment appointment = new Appointment(dayo, time, clinic);
        ref.child(username).setValue(appointment);

        Intent intent = new Intent(getApplicationContext(), WelcomeScreen.class);
        intent.putExtra("patient", patient);
        intent.putExtra("username", username);
        intent.putExtra("status", "booked your appointment!");
        startActivityForResult(intent, 0);
    }


    public void onCancel (View view) {
        Intent intent = new Intent(getApplicationContext(), WelcomeScreen.class);
        intent.putExtra("patient", patient);
        intent.putExtra("username", username);
        startActivityForResult(intent, 0);
    }

}
