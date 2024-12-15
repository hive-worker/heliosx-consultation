package com.heliosx_consultation.heliosx_consultation.model;

public interface IQuestion {
    String getId();
    Section getSection();
    String getText();
    TreatmentType getTreatmentType();
    String getUserSubmission();
    void setUserSubmission(String userSubmission);
}