package com.example.seg2105_f19_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SingleUser extends AppCompatActivity {

    String firstName;
    String lastName;
    String username;
    String role;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_user);

        Intent intent = getIntent();
        firstName = (String)intent.getSerializableExtra("firstName");
        lastName = (String)intent.getSerializableExtra("lastName");
        username = (String)intent.getSerializableExtra("username");
        role = (String)intent.getSerializableExtra("role");

        ((TextView)findViewById(R.id.infoText)).setText("Name: " + firstName + " " + lastName + "\n" + "Username: " + username + "\n" + "Role: " + role);

        if (role.equals("admin")) {
            ((Button)findViewById(R.id.deleteButton)).setEnabled(false);
        }
    }


    public void onDelete (View view) {
        ref.child(username).removeValue();
        onBack(view);
    }


    public void onBack (View view) {
        Intent intent = new Intent(getApplicationContext(), ViewAccounts.class);
        startActivityForResult(intent, 0);
    }

}
