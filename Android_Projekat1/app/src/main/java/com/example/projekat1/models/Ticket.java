package com.example.projekat1.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Ticket implements Serializable {

    private int id;
    private int loggedTime;
    private int estimated;
    private String type;
    private String priority;
    private String title;
    private String description;
    private String progress;//sluzi nam da znamo u kom se tabu nalazi

    public Ticket(String type, String priority, int estimated, String title, String description) {
        this.type = type;
        this.priority = priority;
        this.estimated = estimated;
        this.title = title;
        this.description = description;
        this.progress = "todo";
        this.loggedTime = 0;
    }

    @NonNull
    @Override
    public String toString() {
        return id + "-" + type + "-" + priority + "-" + estimated + "-" + title + "-" + description + "-" + progress + "-" + loggedTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoggedTime() {
        return loggedTime;
    }

    public void setLoggedTime(int loggedTime) {
        this.loggedTime = loggedTime;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getEstimated() {
        return estimated;
    }

    public void setEstimated(int estimated) {
        this.estimated = estimated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
