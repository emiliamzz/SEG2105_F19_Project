package com.example.seg2105_f19_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddressSearch extends AppCompatActivity {

    private String patient;
    private String username;

    private boolean needsCleaning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_search);

        Intent intent = getIntent();
        patient = (String)intent.getSerializableExtra("patient");
        username = (String)intent.getSerializableExtra("username");
    }


    public void onSearch (View view) {
        if (needsCleaning) {
            ((TextView)findViewById(R.id.postalCodeText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.postalCodeText)).setText("Postal Code");
            needsCleaning = false;
        }

        String address = ((EditText)findViewById(R.id.postalCode)).getText().toString();
        if (address.equals("")) {
            needsCleaning = true;
            TextView textView = (TextView)findViewById(R.id.postalCodeText);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText("* " + textView.getText());
        }
        else if (!address.matches("\\p{L}\\d\\p{L}\\d\\p{L}\\d")) {
            needsCleaning = true;
            TextView textView = (TextView)findViewById(R.id.postalCodeText);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText("* Not a valid postal code.");
        }

        if (needsCleaning) {
            return;
        }

        Intent intent = new Intent(getApplicationContext(), ViewClinics.class);
        intent.putExtra("patient", patient);
        intent.putExtra("username", username);
        intent.putExtra("address", address.toUpperCase());
        startActivityForResult(intent, 0);
    }


    public void onBack (View view) {
        Intent intent = new Intent(getApplicationContext(), ClinicSearchType.class);
        intent.putExtra("patient", patient);
        intent.putExtra("username", username);
        startActivityForResult(intent, 0);
    }

}
