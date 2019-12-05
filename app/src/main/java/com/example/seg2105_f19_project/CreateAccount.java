package com.example.seg2105_f19_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CreateAccount extends AppCompatActivity {

    private boolean needCleaning = false;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("users");
    private List<User> users = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        readUsers();
    }


    private void readUsers () {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot key : dataSnapshot.getChildren()) {
                    String firstName = key.child("firstName").getValue().toString();
                    String lastName = key.child("lastName").getValue().toString();
                    String username = key.child("username").getValue().toString();
                    String passHash = key.child("passHash").getValue().toString();
                    String role = key.child("role").getValue().toString();
                    User user = new User(firstName, lastName, username, passHash, role);
                    users.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    public void onCreateAccount (View view) {
        if (needCleaning) {
            ((TextView)findViewById(R.id.firstNameText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.firstNameText)).setText("First Name");
            ((TextView)findViewById(R.id.lastNameText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.lastNameText)).setText("Last Name");
            ((TextView)findViewById(R.id.usernameText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.usernameText)).setText("Username");
            ((TextView)findViewById(R.id.passwordText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.passwordText)).setText("Password");
            ((TextView)findViewById(R.id.confirmPasswordText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.confirmPasswordText)).setText("Confirm Password");
            ((TextView)findViewById(R.id.roleText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.roleText)).setText("You are a...");
            needCleaning = false;
        }

        String firstName = ((EditText)findViewById(R.id.firstName)).getText().toString();
        String lastName = ((EditText)findViewById(R.id.lastName)).getText().toString();
        String username = ((EditText)findViewById(R.id.username)).getText().toString();
        String password = ((EditText)findViewById(R.id.password)).getText().toString();
        String confirmPassword = ((EditText)findViewById(R.id.confirmPassword)).getText().toString();

        if (firstName.equals("")) {
            needCleaning = true;
            TextView textView = (TextView) findViewById(R.id.firstNameText);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText("* " + textView.getText());
        }
        if (lastName.equals("")) {
            needCleaning = true;
            TextView textView = (TextView)findViewById(R.id.lastNameText);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText("* " + textView.getText());
        }
        if (username.equals("")) {
            needCleaning = true;
            TextView textView = (TextView)findViewById(R.id.usernameText);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText("* " + textView.getText());
        } else {
            for (User user : users) {
                if (username.equals(user.getUsername())) {
                    needCleaning = true;
                    TextView textView = (TextView)findViewById(R.id.usernameText);
                    textView.setTypeface(null, Typeface.BOLD);
                    textView.setText("* Username already taken.");
                    return;
                }
            }
        }
        if (password.equals("")) {
            needCleaning = true;
            TextView textView = (TextView)findViewById(R.id.passwordText);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText("* " + textView.getText());
        }
        if (confirmPassword.equals("")) {
            needCleaning = true;
            TextView textView = (TextView)findViewById(R.id.confirmPasswordText);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText("* " + textView.getText());
        }
        if (((RadioGroup)findViewById(R.id.role)).getCheckedRadioButtonId() == -1) {
            needCleaning = true;
            TextView textView = (TextView)findViewById(R.id.roleText);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText("* " + textView.getText());
        }

        if (needCleaning) {
            return;
        }

        if (!password.equals(confirmPassword)) {
            needCleaning = true;
            TextView textView = (TextView)findViewById(R.id.confirmPasswordText);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText("* Passwords don't match.");
            return;
        }

        String passwordHash = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(password.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            passwordHash = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Something went wrong: " + e);
            return;
        } catch (UnsupportedEncodingException e) {
            System.out.println("Something went wrong: " + e);
            return;
        }

        String role;
        if (((RadioGroup)findViewById(R.id.role)).getCheckedRadioButtonId() == R.id.employee) {
            role = "employee";
        } else {
            role = "patient";
        }

        User user = new User(firstName, lastName, username, passwordHash, role);
        ref.child(username).setValue(user);
        goToLogin(view);
    }


    public void goToLogin (View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }

}
