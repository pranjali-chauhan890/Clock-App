package com.v2v.clockapp1;

public class TimerModel {
    private int hours;
    private int minutes;
    private int seconds;

    public TimerModel(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }
}
