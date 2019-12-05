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

import java.util.ArrayList;
import java.util.List;

public class ViewAccounts extends AppCompatActivity {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_accounts);

        readUsers();
    }


    private void readUsers () {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ((LinearLayout)findViewById(R.id.listOfUsers)).removeAllViewsInLayout();
                for (DataSnapshot key : dataSnapshot.getChildren()) {
                    String firstName = key.child("firstName").getValue().toString();
                    String lastName = key.child("lastName").getValue().toString();
                    String username = key.child("username").getValue().toString();
                    String passHash = key.child("passHash").getValue().toString();
                    String role = key.child("role").getValue().toString();
                    User user = new User(firstName, lastName, username, passHash, role);
                    makeButton(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    private void makeButton(final User user) {
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.listOfUsers);
        Button button = new Button(this);
        button.setText(user.getUsername());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SingleUser.class);
                intent.putExtra("firstName", user.getFirstName());
                intent.putExtra("lastName", user.getLastName());
                intent.putExtra("username", user.getUsername());
                intent.putExtra("role", user.getRole());
                startActivityForResult(intent, 0);
            }
        });
        linearLayout.addView(button);
    }


    public void onBack (View view) {
        Intent intent = new Intent(getApplicationContext(), WelcomeAdmin.class);
        startActivityForResult(intent, 0);
    }

}
