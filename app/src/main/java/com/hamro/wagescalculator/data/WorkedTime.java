package com.hamro.wagescalculator.data;

public class WorkedTime {
    int hour=0;
    int minute=0;

    public WorkedTime() {
    }

    public WorkedTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
