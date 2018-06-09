package com.example.dqhuy78.note.Model;

public class Note {
    private long id;
    private String content;
    private String date;
    private String time;

    public Note(long id, String content, String date, String time) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.time = time;
    }

    public Note(String content, String date, String time) {
        this.content = content;
        this.date = date;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
