package com.example.ca2_ella_burke;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class newNoteActivity extends AppCompatActivity {

    private String userId;
    private DatabaseReference dbRef;
    private static final String note = "note";
    private FirebaseUser user;
    private note mNote;


    Button addNewNoteBtn;
    EditText titleToTagET;
    EditText textToNoteET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);

        dbRef = FirebaseDatabase.getInstance().getReference(note);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        addNewNoteBtn = (Button) findViewById(R.id.addNewNoteBtn);
        titleToTagET = (EditText) findViewById(R.id.tagET);
        textToNoteET = (EditText) findViewById(R.id.noteET);

        addNewNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTag = titleToTagET.getText().toString();
                String newNote = textToNoteET.getText().toString();
                Calendar calender = Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance().format(calender.getTime());
                mNote = new note(newTag, currentDate, newNote, userId);
                Toast.makeText(newNoteActivity.this, "Note Created!", Toast.LENGTH_LONG).show();

                String keyId = dbRef.push().getKey();
                dbRef.child(keyId).setValue(mNote);

                Intent intent = new Intent(newNoteActivity.this, notesActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.go_back_to_notes_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.go_back_icon) {
            Intent intent = new Intent(newNoteActivity.this, notesActivity.class);
            startActivity(intent);
            return true;
        }

        return true;
    }

}