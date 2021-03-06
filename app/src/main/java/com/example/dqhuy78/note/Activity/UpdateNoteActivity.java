package com.example.dqhuy78.note.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dqhuy78.note.DataAccessObject.NoteDAO;
import com.example.dqhuy78.note.Model.Note;
import com.example.dqhuy78.note.R;

import java.util.Calendar;

public class UpdateNoteActivity extends AppCompatActivity {

    NoteDAO noteDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        noteDAO = new NoteDAO(this);

        Intent receive = getIntent();

        final EditText noteDate = findViewById(R.id.noteDate);
        noteDate.setFocusable(false); // Turn off keyboard on click
        noteDate.setText(receive.getStringExtra("date"));
        // Trigger date picker on click
        noteDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pickDate(noteDate);
            }
        });

        final EditText noteTime = findViewById(R.id.noteTime);
        noteTime.setFocusable(false); // Turn off keyboard on click
        noteTime.setText(receive.getStringExtra("time"));
        // Trigger time picker on click
        noteTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTime(noteTime);
            }
        });

        final EditText noteContent = findViewById(R.id.noteContent);
        noteContent.setText(receive.getStringExtra("content"));

        final String idNote = receive.getStringExtra("id");
        Button updateNote = findViewById(R.id.updateNote);
        // Store note in database
        updateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = Long.parseLong(idNote);
                String content = noteContent.getText().toString();
                String date = noteDate.getText().toString();
                String time = noteTime.getText().toString();
                if (content.length() < 1 || date.length() < 1 || time.length() < 1) {
                    Toast.makeText(getApplicationContext(), "Please fill all field", Toast.LENGTH_SHORT).show();
                } else {
                    noteDAO.update(new Note(id, content, date, time));
                    Toast.makeText(getApplicationContext(), "Update note success", Toast.LENGTH_SHORT).show();
                    Intent changeView = new Intent(UpdateNoteActivity.this, MainActivity.class);
                    startActivity(changeView);
                }
            }
        });
    }

    // Show date picker
    private void pickDate(final EditText edt) {
        Calendar currentDate = Calendar.getInstance(); // Get current datetime
        // Create date picker dialog
        DatePickerDialog dpg = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dom = String.valueOf(dayOfMonth);
                String m = String.valueOf(month + 1);
                if (dayOfMonth < 10) {
                    dom = "0" + dom;
                }
                if (month < 10) {
                    m = "0" + m;
                }

                edt.setText(dom + "/" + m + "/" + year); // Set edit text content
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));
        dpg.show(); // Display date picker
    }

    // Show time picker
    private void pickTime(final EditText edt) {
        Calendar currentDate = Calendar.getInstance(); // Get current datetime
        // Create time picker dialog
        TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hod = String.valueOf(hourOfDay);
                String m = String.valueOf(minute);
                if (hourOfDay < 10) {
                    hod = "0" + hod;
                }
                if (minute < 10) {
                    m = "0" + m;
                }
                edt.setText(hod + ":" + m); // Set edit text content
            }
        }, currentDate.get(Calendar.HOUR), currentDate.get(Calendar.MINUTE), true);
        tpd.show(); // Display time picker
    }
}
