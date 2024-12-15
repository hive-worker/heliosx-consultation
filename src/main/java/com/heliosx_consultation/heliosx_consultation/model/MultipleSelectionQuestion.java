package com.heliosx_consultation.heliosx_consultation.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
public class MultipleSelectionQuestion implements IQuestion {

    private String id;
    private Section section;
    private String text;
    private TreatmentType treatmentType;
    @Getter
    private List<Option> options;
    private String userSubmission = "";

    public MultipleSelectionQuestion(String id,
                                     Section section,
                                     String text,
                                     TreatmentType treatmentType,
                                     List<Option> options) {
        this.id = id;
        this.section = section;
        this.text = text;
        this.treatmentType = treatmentType;
        this.options = options;
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
        return userSubmission;
    }

    @Override
    public void setUserSubmission(String userSubmission) {
        this.userSubmission = userSubmission;
    }
}