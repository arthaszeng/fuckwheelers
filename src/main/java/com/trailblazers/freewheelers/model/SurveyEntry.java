package com.trailblazers.freewheelers.model;

public class SurveyEntry {
    private int rating;
    private String comment;

    public SurveyEntry(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
