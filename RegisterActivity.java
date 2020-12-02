package com.example.ca2_ella_burke;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {


        private FirebaseAuth mAuth;
        private FirebaseDatabase database;
        private DatabaseReference mDatabase;
        private static final String USER = "user";
        private static final String TAG = "RegisterActivity";
        private User user;
        EditText firstNameET;
        EditText lastNameET;
        EditText emailET;
        EditText phoneNumberET;
        EditText passwordET;
        EditText confirmPasswordET;
        Button registerButton;
        Button goBackButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);
            mAuth = FirebaseAuth.getInstance();

            firstNameET = (EditText)findViewById(R.id.firstNameET);
            lastNameET = (EditText)findViewById(R.id.lastNameET);
            emailET = (EditText)findViewById(R.id.emailET);
            phoneNumberET = (EditText) findViewById(R.id.phoneET);
            passwordET = (EditText)findViewById(R.id.passwordET);
            confirmPasswordET = (EditText)findViewById(R.id.confirmPasswordET);
            registerButton = (Button)findViewById(R.id.registerButton);
            goBackButton = (Button)findViewById(R.id.goBackButton);

            database = FirebaseDatabase.getInstance();
            mDatabase = database.getReference(USER);
            mAuth = FirebaseAuth.getInstance();

            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = emailET.getText().toString();
                    String password = passwordET.getText().toString();

                    if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                        Toast.makeText(getApplicationContext(),"Enter email and password", Toast.LENGTH_LONG).show();
                        return;
                    }
                    String firstName = firstNameET.getText().toString();
                    String lastName = lastNameET.getText().toString();
                    String phoneNumber = phoneNumberET.getText().toString();
                    String pw1 = passwordET.getText().toString();
                    String pw2 = confirmPasswordET.getText().toString();
                    user = new User(email,password,firstName,lastName,phoneNumber);
                    if(pw1.length() <6 ){
                        Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters, try again!", Toast.LENGTH_LONG).show();
                    }
                    else if(pw1.contains(" ")){
                        Toast.makeText(RegisterActivity.this, "Password must contain no spaces, try again!", Toast.LENGTH_LONG).show();
                    }
                    else if (pw1 != pw2){
                        Toast.makeText(RegisterActivity.this, "Passwords don't match, try again!", Toast.LENGTH_LONG).show();
                    }
                    if(pw1.equals(pw2)) {
                        registerUser(email, password);
                    }
                }
            });

            goBackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RegisterActivity.this.finish();
                }
            });
        }

        public void registerUser(String email, String password){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                String userId = user.getUid();
                                updateUI(user,userId);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }

        public void updateUI(FirebaseUser currentUser, String userId){
            //String keyId = mDatabase.push().getKey();
            mDatabase.child(userId).setValue(user);
            Intent loginIntent = new Intent(this, MainActivity.class);
            startActivity(loginIntent);
        }
    }
