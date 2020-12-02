package com.example.ca2_ella_burke;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class notesActivity extends AppCompatActivity {

    ArrayList<note> noteList = new ArrayList<>();
    RecyclerView mRecyclerView;
    Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private FirebaseUser user;
    private String userId;
    SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        mAuth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        mSearchView = findViewById(R.id.searchView);

//        Calendar calender = Calendar.getInstance();
//       String currentDate = DateFormat.getDateInstance().format(calender.getTime());
//
//        noteList.add(new note("To Do",currentDate, "Collect Robert from training at 6pm", userId));
//        noteList.add(new note("Call", currentDate, "Call Mary back"));
//        noteList.add(new note("Shopping List", currentDate, "Milk, Cheese, Eggs, Ham, Chicken, Pasta Sauce, Meatballs"));
//        noteList.add(new note("Passwords",currentDate, "Amazon: Username - Ella Burke, Password - Secret"));
//        noteList.add(new note("Call", "Sunday 8th October", "Call Stephen at 4pm"));
//        noteList.add(new note("Recipe", "Tuesday 4th November", "2 eggs, 100grams sugar, 1/4 cup milk...."));

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Adapter( noteList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        ref.child("note").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    note n = child.getValue(note.class);
                    if (n.getUserId().equals(userId)) {
                        noteList.add(n);
                        mAdapter.notifyItemInserted(noteList.size() - 1);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Log.m("DBE Error","Cancel Access");
            }
        });

        if(mSearchView !=null) {
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }

    }

    public void search(String str){
        ArrayList<note> list = new ArrayList<>();
        for(note obj : noteList){
            if(obj.getTagText().toLowerCase().contains(str.toLowerCase())){
                list.add(obj);
            }
        }
        Adapter adapterClass = new Adapter(list);
        mRecyclerView.setAdapter(adapterClass);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        getMenuInflater().inflate(R.menu.create_new_note, menu);
        //getMenuInflater().inflate(R.menu.search_menu, menu);


        MenuItem mi = menu.findItem(R.id.search_icon);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        FirebaseUser user = mAuth.getCurrentUser();

        if (id == R.id.update_profile) {
            Intent intent = new Intent(notesActivity.this, AccountDetailsUpdateActivity.class);
            intent.putExtra("email", user.getEmail());
            startActivity(intent);
            return true;
        } else if (id == R.id.logout) {
            //FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(notesActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.create_new_note_icon) {
            Intent intent = new Intent(notesActivity.this, newNoteActivity.class);
            startActivity(intent);
            return true;

        }

        return true;
    }

}