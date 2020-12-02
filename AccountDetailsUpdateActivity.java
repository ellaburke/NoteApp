package com.example.ca2_ella_burke;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class AccountDetailsUpdateActivity extends AppCompatActivity {

    ImageView firstNameEditImage, secondNameEditImage, phoneEditImage;
    EditText profileFName;
    EditText profileLName;
    EditText profilePhone;
    EditText profileEmail;
    Button updateBtn;
    Button goBackBtn;
    String email;
    String fName, lName, pNumber;
    String NEW_FIRST_NAME;
    DatabaseReference updateRef;
    private Map<String, String> userMap;
    FirebaseUser firebaseUser;
    String userId;
    private static final String USER = "user";

    private static final String TAG = "UpdateProfileActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details_update);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        updateRef = FirebaseDatabase.getInstance().getReference("user");
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child(USER);
        Log.v("USERID", userRef.getKey());

        profileFName = (EditText) findViewById(R.id.profileFName);
        profileLName = (EditText) findViewById(R.id.profileLName);
        profilePhone = (EditText) findViewById(R.id.profilePhone);
        profileEmail = (EditText) findViewById(R.id.profileEmail);
        updateBtn = (Button) findViewById(R.id.updateDetailsButton);
        goBackBtn = (Button) findViewById(R.id.goBackButton2);
        firstNameEditImage = (ImageView) findViewById(R.id.firstNameEditImage);
        secondNameEditImage = (ImageView) findViewById(R.id.secondNameEditImage);
        phoneEditImage = (ImageView) findViewById(R.id.phoneEditImage);

        profileEmail.setEnabled(false);
        profileFName.setEnabled(false);
        profileLName.setEnabled(false);
        profilePhone.setEnabled(false);

        userRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot keyId : snapshot.getChildren()) {
                    if (keyId.child("email").getValue().equals(email)) {
                        fName = keyId.child("firstName").getValue(String.class);
                        lName = keyId.child("lastName").getValue(String.class);
                        pNumber = keyId.child("phoneNumber").getValue(String.class);
                        break;
                    }
                }
                profileFName.setText(fName);
                profileLName.setText(lName);
                profilePhone.setText(pNumber);
                profileEmail.setText(email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountDetailsUpdateActivity.this, notesActivity.class);
                startActivity(intent);
            }
        });

        firstNameEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileFName.setEnabled(true);
            }
        });
        secondNameEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileLName.setEnabled(true);
            }
        });
        phoneEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilePhone.setEnabled(true);
            }
        });

    }


    public void update(View view) {
        if (isFNameChanged() || isLNameChanged() || isPhoneNumberChanged()) {
            Toast.makeText(AccountDetailsUpdateActivity.this, "Account Updated", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AccountDetailsUpdateActivity.this, notesActivity.class);
            startActivity(intent);

        } else
            Toast.makeText(AccountDetailsUpdateActivity.this, "Details are the same & Can not be updated!", Toast.LENGTH_LONG).show();
    }

    private boolean isPhoneNumberChanged() {
        if (!pNumber.equals(profilePhone.getText().toString())) {

            updateRef.child(userId).child("phoneNumber").setValue(profilePhone.getText().toString());
            return true;
        } else {
            return false;
        }
    }

    private boolean isLNameChanged() {
        if (!lName.equals(profileLName.getText().toString())) {

            updateRef.child(userId).child("lastName").setValue(profileLName.getText().toString());
            return true;
        } else {
            return false;
        }
    }

    private boolean isFNameChanged() {
        if (!fName.equals(profileFName.getText().toString())) {

            updateRef.child(userId).child("firstName").setValue(profileFName.getText().toString());
            return true;
        } else {
            return false;
        }
    }



}
