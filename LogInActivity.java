package com.example.ca2_ella_burke;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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


public class LogInActivity extends AppCompatActivity {

    EditText emailInputET;
    EditText passwordInputET;
    Button loginButton;
    Button backButton;
    private FirebaseAuth mAuth;
    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mAuth = FirebaseAuth.getInstance();

        emailInputET = (EditText) findViewById(R.id.emailInputET);
        passwordInputET = (EditText) findViewById(R.id.passwordLogOnET);
        loginButton = (Button) findViewById(R.id.logOnButton);
        backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogInActivity.this.finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               String email = emailInputET.getText().toString();
               String password = passwordInputET.getText().toString();

               mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                           Log.d(TAG, "signInWithEmail:success");
                           FirebaseUser user = mAuth.getCurrentUser();
                           Intent intent = new Intent(LogInActivity.this, notesActivity.class);
                           startActivity(intent);
                       }
                       else {
                           Log.w(TAG, "signInWithEmail:failure", task.getException());
                           Toast.makeText(LogInActivity.this, "Authentication failed.",
                                   Toast.LENGTH_SHORT).show();
                       }
                   }
               });
           }
        });



    }

}