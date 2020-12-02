package com.example.ca2_ella_burke;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ExampleViewHolder>  {
    private ArrayList<note> adapterNoteList;


    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView noteTagTV;
        public TextView noteDateTV;
        public TextView noteTV;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            noteTagTV = itemView.findViewById(R.id.noteTagTV);
            noteDateTV = itemView.findViewById(R.id.noteDateTV);
            noteTV = itemView.findViewById(R.id.noteTV);

        }
    }

    public Adapter(ArrayList<note> noteList) {
        adapterNoteList = noteList;
    }

    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_rcv, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }


    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        note currentItem = adapterNoteList.get(position);
        holder.noteDateTV.setText(currentItem.getDateText());
        holder.noteTagTV.setText(currentItem.getTagText());
        holder.noteTV.setText(currentItem.getNoteText());
    }

    public int getItemCount() {
        return adapterNoteList.size();
    }

}
