package com.example.dqhuy78.note.DataAccessObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dqhuy78.note.Model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteDAO extends SQLiteOpenHelper{

    // Database name & version
    private static final int VERSION = 1;
    private static final String DATABASE = "notes_db";

    // Table struct
    private static final String TABLE = "notes";
    private static final String NOTE_ID = "id";
    private static final String NOTE_CONTENT = "content";
    private static final String NOTE_DATE = "date";
    private static final String NOTE_TIME = "time";

    // Create table string
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
            NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            NOTE_CONTENT + " TEXT NOT NULL," +
            NOTE_DATE + " TEXT NOT NULL," +
            NOTE_TIME + " TEXT NOT NULL"+
            ")";

    // Drop table string
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE;

    public NoteDAO(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public long store(Note note) {
        // Open database connection
        SQLiteDatabase db = this.getWritableDatabase();

        // Get note value to insert into database
        ContentValues values = new ContentValues();
        values.put(NOTE_CONTENT, note.getContent());
        values.put(NOTE_DATE, note.getDate());
        values.put(NOTE_TIME, note.getTime());

        // Store note in database and return id
        long id = db.insert(TABLE, null, values);

        // Close connection
        db.close();

        return id;
//        return new Note(id, note.getContent(), note.getDate(), note.getTime());
    }

    public void update(Note note) {
        // Open database connection
        SQLiteDatabase db = this.getWritableDatabase();

        // Get note value to insert into database
        ContentValues values = new ContentValues();
        values.put(NOTE_CONTENT, note.getContent());
        values.put(NOTE_DATE, note.getDate());
        values.put(NOTE_TIME, note.getTime());

        // Update condition
        String whereClause = "id = ?";
        String whereArgs[] = {String.valueOf(note.getId())};

        // Update record
        db.update(TABLE, values, whereClause, whereArgs);

        // Close connection
        db.close();
    }

    public void delete(long id) {
        // Open database connection
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete condition
        String whereClause = "id = ?";
        String whereArgs[] = {String.valueOf(id)};

        // Delete record
        db.delete(TABLE, whereClause, whereArgs);

        // Close connection
        db.close();
    }

    public ArrayList<Note> all() {
        ArrayList<Note> notes = new ArrayList<>();

        // Open database connection
        SQLiteDatabase db = this.getReadableDatabase();

        // Get record
        Cursor cursor = db.query(TABLE, null, null, null, null, null, "id DESC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndex(NOTE_ID));
                String content = cursor.getString(cursor.getColumnIndex(NOTE_CONTENT));
                String date = cursor.getString(cursor.getColumnIndex(NOTE_DATE));
                String time = cursor.getString(cursor.getColumnIndex(NOTE_TIME));
                notes.add(new Note(id, content, date, time));
            }
        }

        db.close();

        return notes;
    }

    public ArrayList<Note> find(String search) {
        ArrayList<Note> notes = new ArrayList<>();

        // Open database connection
        SQLiteDatabase db = this.getReadableDatabase();

        String whereClause = "content LIKE ?";
        String whereArgs[] = {"%" + search + "%"};

        Cursor cursor = db.query(TABLE, null, whereClause, whereArgs, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndex(NOTE_ID));
                String content = cursor.getString(cursor.getColumnIndex(NOTE_CONTENT));
                String date = cursor.getString(cursor.getColumnIndex(NOTE_DATE));
                String time = cursor.getString(cursor.getColumnIndex(NOTE_TIME));
                notes.add(new Note(id, content, date, time));
            }
        }

        db.close();

        return notes;
    }
}
