package com.example.seg2105_f19_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CreateClinic extends AppCompatActivity {

    private boolean needCleenup = false;

    private String username;
    private String employee;

    private String name;
    private String address;
    private String phone;
    private String insurance;
    private String payment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_clinic);

        Intent intent = getIntent();
        username = (String)intent.getSerializableExtra("username");
        employee = (String)intent.getSerializableExtra("employee");
        name = (String)intent.getSerializableExtra("name");
        address = (String)intent.getSerializableExtra("address");
        phone = (String)intent.getSerializableExtra("phone");
        insurance = (String)intent.getSerializableExtra("insurance");
        payment = (String)intent.getSerializableExtra("payment");

        if (name != null) {
            ((EditText)findViewById(R.id.nameInput)).setText(name);
            ((EditText)findViewById(R.id.addressInput)).setText(address);
            ((EditText)findViewById(R.id.phoneInput)).setText(phone);
            ((EditText)findViewById(R.id.insuraceInput)).setText(insurance);
            ((EditText)findViewById(R.id.paymentInput)).setText(payment);
        }
    }


    public void onNext (View view) {
        if (needCleenup) {
            ((TextView)findViewById(R.id.nameText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.nameText)).setText("Clinic Name");
            ((TextView)findViewById(R.id.addressText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.addressText)).setText("Postal Code");
            ((TextView)findViewById(R.id.phoneText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.phoneText)).setText("Phone Number");
            ((TextView)findViewById(R.id.insuranceText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.insuranceText)).setText("Accepted Insurance Types");
            ((TextView)findViewById(R.id.paymentText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.paymentText)).setText("Accepted Payment Methods");
            needCleenup = false;
        }

        name = ((EditText)findViewById(R.id.nameInput)).getText().toString();
        address = ((EditText)findViewById(R.id.addressInput)).getText().toString();
        phone = ((EditText)findViewById(R.id.phoneInput)).getText().toString();
        insurance = ((EditText)findViewById(R.id.insuraceInput)).getText().toString();
        payment = ((EditText)findViewById(R.id.paymentInput)).getText().toString();

        if (name.equals("")) {
            needCleenup = true;
            TextView textView = (TextView)findViewById(R.id.nameText);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText("* " + textView.getText());
        }
        if (address.equals("")) {
            needCleenup = true;
            TextView textView = (TextView)findViewById(R.id.addressText);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText("* " + textView.getText());
        }
        else if (!address.matches("\\p{L}\\d\\p{L}\\d\\p{L}\\d")) {
            needCleenup = true;
            TextView textView = (TextView)findViewById(R.id.addressText);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText("* Not a valid postal code.");
        }
        if (phone.equals("")) {
            needCleenup = true;
            TextView textView = (TextView)findViewById(R.id.phoneText);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText("* " + textView.getText());
        }
        else if (!phone.matches("\\d{10}")) {
            needCleenup = true;
            TextView textView = (TextView)findViewById(R.id.phoneText);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText("* Not a valid phone number.");
        }
        if (insurance.equals("")) {
            needCleenup = true;
            TextView textView = (TextView)findViewById(R.id.insuranceText);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText("* " + textView.getText());
        }
        if (payment.equals("")) {
            needCleenup = true;
            TextView textView = (TextView)findViewById(R.id.paymentText);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText("* " + textView.getText());
        }

        if (needCleenup) {
            return;
        }

        Intent intent = new Intent(getApplicationContext(), AddHours.class);
        intent.putExtra("name", name);
        intent.putExtra("address", address.toUpperCase());
        intent.putExtra("phone", phone);
        intent.putExtra("insurance", insurance);
        intent.putExtra("payment", payment);
        intent.putExtra("username", username);
        intent.putExtra("employee", employee);
        startActivityForResult(intent, 0);
    }

}
