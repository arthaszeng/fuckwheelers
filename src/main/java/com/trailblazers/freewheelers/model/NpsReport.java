package com.trailblazers.freewheelers.model;

public class NpsReport {
    private int promoters;
    private int detractors;
    private int passives;
    private int total;

    public NpsReport(int promoters, int detractors, int passives, int total) {
        this.promoters = promoters;
        this.detractors = detractors;
        this.passives = passives;
        this.total = total;
    }

    public Double getPromotersPercentage() {
        return percentageFor(promoters);
    }

    public Double getDetractorsPercentage() {
        return percentageFor(detractors);
    }

    public Double getPassivesPercentage() {
        return percentageFor(passives);
    }

    public Double getNpsScore() {
        return percentageFor(promoters - detractors);
    }

    public int getTotal(){
        return total;
    }

    private Double percentageFor(double value) {
        return total == 0 ? 0.0 : (value / total) * 100;
    }

}
