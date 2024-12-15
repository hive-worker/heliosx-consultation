package com.heliosx_consultation.heliosx_consultation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class YesNoQuestion implements IQuestion {

    private String id;
    private Section section;
    private String text;
    private TreatmentType treatmentType;
    private String correctAnswer;
    private String sorryMessage;
    private String userSubmission = "";

    public YesNoQuestion(String id,
                         Section section,
                         String text,
                         TreatmentType treatmentType,
                         String correctAnswer,
                         String sorryMessage) {
        this.id = id;
        this.section = section;
        this.text = text;
        this.treatmentType = treatmentType;
        this.correctAnswer = correctAnswer;
        this.sorryMessage = sorryMessage;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Section getSection() {
        return section;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public TreatmentType getTreatmentType() {
        return treatmentType;
    }

    @Override
    public String getUserSubmission() {
        return "";
    }

    @Override
    public void setUserSubmission(String userSubmission) {
        this.userSubmission = userSubmission;
    }

}