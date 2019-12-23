package com.indocyber.itsmeandroid.model;

import androidx.annotation.NonNull;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */
public class Notification {
    private int id;
    private String title;
    private String body;
    private String date;
    private int status;
    public static final int READ = 0;
    public static final int UNREAD = 1;

    public Notification(int id, String title, String body, String date, int status) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.date = date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Notification {" +
                "id =" + id +
                ", title = '" + title + '\'' +
                ", body = '" + body + '\'' +
                ", date = '" + date + '\'' +
                ", status = " + status +
                '}';
    }
}
