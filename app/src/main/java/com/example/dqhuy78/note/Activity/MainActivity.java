package com.example.dqhuy78.note.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.VolumeShaper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dqhuy78.note.Adapter.NoteAdapter;
import com.example.dqhuy78.note.DataAccessObject.NoteDAO;
import com.example.dqhuy78.note.I18N.I18N;
import com.example.dqhuy78.note.Model.Note;
import com.example.dqhuy78.note.R;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ArrayList<Note> notes;
    NoteAdapter adapter;
    ListView lv;
    NoteDAO noteDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get language
        I18N.changeLocalization(this, "DEFAULT");
        setContentView(R.layout.activity_main);

        // Init and display list view
        notes = new ArrayList<>();
        noteDAO = new NoteDAO(this);
        notes = noteDAO.find("to");
        adapter = new NoteAdapter(this, notes);
        lv = findViewById(R.id.listView);
        lv.setAdapter(adapter);

        // List view item click
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Option");
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setItems(new CharSequence[]{"Edit", "Delete"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent changeView = new Intent(MainActivity.this, UpdateNoteActivity.class);
                            changeView.putExtra("id", String.valueOf(notes.get(pos).getId()));
                            changeView.putExtra("content", String.valueOf(notes.get(pos).getContent()));
                            changeView.putExtra("date", String.valueOf(notes.get(pos).getDate()));
                            changeView.putExtra("time", String.valueOf(notes.get(pos).getTime()));
                            startActivity(changeView);
                        } else {
                            noteDAO.delete(notes.get(pos).getId());
                            notes.remove(pos);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(), "Delete note success", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });

        // Jump to create note activity
        FloatingActionButton createNote = (FloatingActionButton) findViewById(R.id.createNote);
        createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeView = new Intent(MainActivity.this, CreateNoteActivity.class);
                startActivity(changeView);
            }
        });

        // Spinner
        Spinner selector = findViewById(R.id.selectList);
        selector.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // parent.getItemAtPosition(position).toString();
            }
        });
    }
}
