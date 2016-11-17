package com.trailblazers.freewheelers.model;

import java.util.List;

public class SurveyComments {
    private List<String> promoterComments;
    private List<String> detractorComments;
    private List<String> passiveComments;

    public SurveyComments(List<String> promoterComments, List<String> detractorComments, List<String> passiveComments) {
        this.promoterComments = promoterComments;
        this.detractorComments = detractorComments;
        this.passiveComments = passiveComments;
    }

    public List<String> getPromoterComments() {
        return promoterComments;
    }

    public List<String> getDetractorComments() {
        return detractorComments;
    }

    public List<String> getPassiveComments() {
        return passiveComments;
    }
}
