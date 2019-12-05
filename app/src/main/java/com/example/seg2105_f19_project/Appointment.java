package com.example.seg2105_f19_project;

public class Appointment {

    private String date;
    private String time;
    private String clinic;

    public Appointment (String date, String time, String clinic) {
        this.date = date;
        this.time = time;
        this.clinic = clinic;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getClinic() {
        return clinic;
    }
}
