package com.heliosx_consultation.heliosx_consultation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Option {
    private String text;
    private int score;

    public Option(String text, int score) {
        this.text = text;
        this.score = score;
    }

}