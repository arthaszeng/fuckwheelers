package com.trailblazers.freewheelers.web.forms;

import com.trailblazers.freewheelers.model.SurveyEntry;

import javax.validation.constraints.NotNull;


public class SurveyEntryForm {

    @NotNull(message = "Please rate us")
    private Integer rating;

    private String comment;

    //Required by spring @ModelAttribute
    public SurveyEntryForm() {
    }

    public SurveyEntryForm(Integer rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) { this.comment = comment; }

    public SurveyEntry surveyEntry() {
        return new SurveyEntry(rating, comment);
    }
}
