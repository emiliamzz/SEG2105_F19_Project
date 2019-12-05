package com.example.seg2105_f19_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {

    boolean needCleanup = false;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("users");
    private List<User> users = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void login (View view) {
        if (needCleanup) {
            ((TextView)findViewById(R.id.usernameText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.usernameText)).setText("Username");
            ((TextView)findViewById(R.id.passwordText)).setTypeface(null, Typeface.NORMAL);
            ((TextView)findViewById(R.id.passwordText)).setText("Password");
            needCleanup = false;
        }

        String username = ((EditText)findViewById(R.id.username)).getText().toString();
        String password = ((EditText)findViewById(R.id.password)).getText().toString();

        if (username.equals("")) {
            needCleanup = true;
            TextView textView = (TextView)findViewById(R.id.usernameText);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText("* " + textView.getText());
        }
        if (password.equals("")) {
            needCleanup = true;
            TextView textView = (TextView)findViewById(R.id.passwordText);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText("* " + textView.getText());
        }

        if (needCleanup) {
            return;
        }

        for (User user : users) {
            if (username.equals(user.getUsername())) {
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
                if (!passwordHash.equals(user.getPassHash())) {
                    needCleanup = true;
                    TextView textView = (TextView)findViewById(R.id.passwordText);
                    textView.setText(textView.getText() + "\n\n" + "** Either username or password is incorrect. **");
                    return;
                }
                Intent intent;
                if (user.getRole().equals("admin")) {
                    intent = new Intent(getApplicationContext(), WelcomeAdmin.class);
                } else if (user.getRole().equals("employee")) {
                    intent = new Intent(getApplicationContext(), WelcomeEmployee.class);
                    intent.putExtra("username", user.getUsername());
                    intent.putExtra("employee", user.getFirstName());
                } else {
                    intent = new Intent(getApplicationContext(), WelcomeScreen.class);
                    intent.putExtra("patient", user.getFirstName());
                    intent.putExtra("username", user.getUsername());
                }
                startActivityForResult(intent, 0);
                return;
            }
        }

        needCleanup = true;
        TextView textView = (TextView)findViewById(R.id.passwordText);
        textView.setText(textView.getText() + "\n\n" + "** Either username or password is incorrect. **");
        return;
    }


    public void makeAccount (View view) {
        Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
        startActivityForResult(intent, 0);
    }

}
