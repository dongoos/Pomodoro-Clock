package com.example.tomato.model;

import android.util.Log;

public class Model {
    private int id, status, timeMinute, timeSec;

    private String task;

    public int getId() {
        return id;
    }

    public int getTimeSec() {
        return timeSec;
    }

    public void setTimeSec(int timeSec) {
        this.timeSec = timeSec;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTimeMinute() {
        return timeMinute;
    }

    public void setTimeMinute(int timeMinute) {
        Log.i("db TEsting", ""+timeMinute);
        this.timeMinute = timeMinute;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", status=" + status +
                ", timeMinute=" + timeMinute +
                ", timeSec=" + timeSec +
                ", task='" + task + '\'' +
                '}';
    }
}
