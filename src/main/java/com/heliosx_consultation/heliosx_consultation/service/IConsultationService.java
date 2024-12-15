package com.heliosx_consultation.heliosx_consultation.service;

import com.heliosx_consultation.heliosx_consultation.model.*;

import java.util.List;

public interface IConsultationService {
    List<IQuestion> getQuestions(TreatmentType treatmentType, Page page);
    PrescriptionResponse evaluateAnswers(Page page, List<? extends IQuestion> answers);
}