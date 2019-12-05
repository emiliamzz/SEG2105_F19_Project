package com.example.seg2105_f19_project;

public class Hours {

    private String sundayOpen;
    private String sundayClose;
    private String mondayOpen;
    private String mondayClose;
    private String tuesdayOpen;
    private String tuesdayClose;
    private String wednesdayOpen;
    private String wednesdayClose;
    private String thursdayOpen;
    private String thursdayClose;
    private String fridayOpen;
    private String fridayClose;
    private String saturdayOpen;
    private String saturdayClose;

    public Hours (String sundayOpen, String sundayClose,
                  String mondayOpen, String mondayClose,
                  String tuesdayOpen, String tuesdayClose,
                  String wednesdayOpen, String wednesdayClose,
                  String thursdayOpen, String thursdayClose,
                  String fridayOpen, String fridayClose,
                  String saturdayOpen, String saturdayClose) {
        this.sundayOpen = sundayOpen;
        this.sundayClose = sundayClose;
        this.mondayOpen = mondayOpen;
        this.mondayClose = mondayClose;
        this.tuesdayOpen = tuesdayOpen;
        this.tuesdayClose = tuesdayClose;
        this.wednesdayOpen = wednesdayOpen;
        this.wednesdayClose = wednesdayClose;
        this.thursdayOpen = thursdayOpen;
        this.thursdayClose = thursdayClose;
        this.fridayOpen = fridayOpen;
        this.fridayClose = fridayClose;
        this.saturdayOpen = saturdayOpen;
        this.saturdayClose = saturdayClose;
    }

    public String getSundayOpen() {
        return sundayOpen;
    }

    public String getSundayClose() {
        return sundayClose;
    }

    public String getMondayOpen() {
        return mondayOpen;
    }

    public String getMondayClose() {
        return mondayClose;
    }

    public String getTuesdayOpen() {
        return tuesdayOpen;
    }

    public String getTuesdayClose() {
        return tuesdayClose;
    }

    public String getWednesdayOpen() {
        return wednesdayOpen;
    }

    public String getWednesdayClose() {
        return wednesdayClose;
    }

    public String getThursdayOpen() {
        return thursdayOpen;
    }

    public String getThursdayClose() {
        return thursdayClose;
    }

    public String getFridayOpen() {
        return fridayOpen;
    }

    public String getFridayClose() {
        return fridayClose;
    }

    public String getSaturdayOpen() {
        return saturdayOpen;
    }

    public String getSaturdayClose() {
        return saturdayClose;
    }

}
