package com.example.dqhuy78.note.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dqhuy78.note.Model.Note;
import com.example.dqhuy78.note.R;

import java.util.ArrayList;

public class NoteAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Note> notes;

    public NoteAdapter(Context context, ArrayList<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return notes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.note_item, null);
        TextView content = v.findViewById(R.id.content);
        content.setText(notes.get(position).getContent());
        TextView dateTime = v.findViewById(R.id.dateTime);
        dateTime.setText("Time: " + notes.get(position).getTime() + " - " + notes.get(position).getDate());

        return v;
    }
}
