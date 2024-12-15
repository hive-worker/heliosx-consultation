package com.heliosx_consultation.heliosx_consultation.model;

public class PrescriptionResponse {
    private boolean canPrescribe;
    private String message;

    public PrescriptionResponse(boolean canPrescribe, String message) {
        this.canPrescribe = canPrescribe;
        this.message = message;
    }

    public boolean isCanPrescribe() {
        return canPrescribe;
    }

    public void setCanPrescribe(boolean canPrescribe) {
        this.canPrescribe = canPrescribe;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}