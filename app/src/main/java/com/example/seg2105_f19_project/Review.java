package com.example.seg2105_f19_project;

public class Review {

    private String num;
    private String comment;
    private String clinic;

    public Review (String num, String comment, String clinic) {
        this.num = num;
        this.comment = comment;
        this.clinic = clinic;
    }

    public String getNum() {
        return num;
    }

    public String getComment() {
        return comment;
    }

    public String getClinic() {
        return clinic;
    }

}
