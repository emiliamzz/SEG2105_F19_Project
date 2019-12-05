package com.example.seg2105_f19_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddService extends AppCompatActivity {

    private boolean needCleanup = false;
    private boolean editing = false;

    private String name;
    private String role;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("services");
    private List<Service> services = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        Intent intent = getIntent();
        name = (String)intent.getSerializableExtra("name");
        role = (String)intent.getSerializableExtra("role");

        if (name == null) {
            readServices();
        } else {
            editing = true;
            ((EditText)findViewById(R.id.nameInput)).setText(name);
            ((EditText)findViewById(R.id.nameInput)).setEnabled(false);
            if (role.equals("Doctor")) {
                ((RadioGroup)findViewById(R.id.role)).check(R.id.doctorButton);
            }
            else if (role.equals("Nurse")) {
                ((RadioGroup)findViewById(R.id.role)).check(R.id.nurseButton);
            } else {
                ((RadioGroup)findViewById(R.id.role)).check(R.id.staffButton);
            }
            ((Button)findViewById(R.id.addButton)).setText("Edit Service");
        }
    }


    private void readServices () {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                services.clear();
                for (DataSnapshot key : dataSnapshot.getChildren()) {
                    String name = key.child("name").getValue().toString();
                    String role = key.child("role").getValue().toString();
                    Service service = new Service(name, role);
                    services.add(service);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    public void onAddService(View view) {
        if (!editing) {
            if (needCleanup) {
                ((TextView) findViewById(R.id.nameText)).setTypeface(null, Typeface.NORMAL);
                ((TextView) findViewById(R.id.nameText)).setText("Name");
                ((TextView) findViewById(R.id.roleText)).setTypeface(null, Typeface.NORMAL);
                ((TextView) findViewById(R.id.roleText)).setText("This is performed by a...");
                needCleanup = false;
            }

            name = ((EditText) findViewById(R.id.nameInput)).getText().toString();

            if (name.equals("")) {
                needCleanup = true;
                TextView textView = (TextView) findViewById(R.id.nameText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* " + textView.getText());
            } else if (name.contains("_")) {
                needCleanup = true;
                TextView textView = (TextView) findViewById(R.id.nameText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* Name cannot contain a '_'.");
            } else {
                for (Service service : services) {
                    if (name.equals(service.getName())) {
                        needCleanup = true;
                        TextView textView = (TextView) findViewById(R.id.nameText);
                        textView.setTypeface(null, Typeface.BOLD);
                        textView.setText("* This service already exists.");
                    }
                }
            }
            if (((RadioGroup) findViewById(R.id.role)).getCheckedRadioButtonId() == -1) {
                needCleanup = true;
                TextView textView = (TextView) findViewById(R.id.roleText);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText("* " + textView.getText());
            }

            if (needCleanup) {
                return;
            }
        }

        if (((RadioGroup)findViewById(R.id.role)).getCheckedRadioButtonId() == R.id.nurseButton) {
            role = "Nurse";
        }
        else if (((RadioGroup)findViewById(R.id.role)).getCheckedRadioButtonId() == R.id.staffButton) {
            role = "Staff";
        } else {
            role = "Doctor";
        }

        Service service = new Service(name, role);
        ref.child(name).setValue(service);

        onBack(view);
    }


    public void onBack (View view) {
        Intent intent;
        if (editing) {
            intent = new Intent(getApplicationContext(), SingleService.class);
            intent.putExtra("name", name);
            intent.putExtra("role", role);
        } else {
            intent = new Intent(getApplicationContext(), ViewServices.class);
        }
        startActivityForResult(intent, 0);
    }

}
