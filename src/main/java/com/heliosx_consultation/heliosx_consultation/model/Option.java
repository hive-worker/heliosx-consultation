package com.heliosx_consultation.heliosx_consultation.model;

public class Option {
    private String text;
    private int score;

    public Option(String text, int score) {
        this.text = text;
        this.score = score;
    }

    public String getText() {
        return text;
    }

    public int getScore() {
        return score;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setScore(int score) {
        this.score = score;
    }
}