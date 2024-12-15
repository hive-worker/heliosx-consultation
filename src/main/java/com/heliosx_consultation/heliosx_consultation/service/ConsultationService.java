package com.heliosx_consultation.heliosx_consultation.service;

import com.heliosx_consultation.heliosx_consultation.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static com.heliosx_consultation.heliosx_consultation.model.Section.*;
import static com.heliosx_consultation.heliosx_consultation.model.TreatmentType.ALLERGY;

@Service
public class ConsultationService implements IConsultationService {

    private static final int PAGE_2_PASS_SCORE = 10;

    @Override
    public List<IQuestion> getQuestions(TreatmentType treatmentType, Page page) {
        List<IQuestion> questions = new ArrayList<>();
        if (treatmentType == ALLERGY) {
            if (page == Page.PAGE_1) {
                questions.add(new YesNoQuestion("1", ABOUT_YOU, "What is your age?", ALLERGY, "yes", "Sorry, you must be at least 18 years old to proceed"));
                questions.add(new YesNoQuestion("2", SYMPTOMS, "Do you have any allergies?", ALLERGY, "yes", "Sorry, you must have allergies to proceed"));
            } else if (page == Page.PAGE_2) {
                List<Option> options = List.of(
                        new Option("All of the time", 5),
                        new Option("Most of the time", 4),
                        new Option("Some of the time", 3),
                        new Option("A little of the time", 2),
                        new Option("None of the time", 1)
                );
                questions.add(new MultipleSelectionQuestion("3", HEALTH, "Does your allergy prevent you from sleeping?", ALLERGY, options));
                questions.add(new MultipleSelectionQuestion("4", AGREEMENT, "Does your allergy prevent you from working?", ALLERGY, options));
            }
        }
        return questions;
    }

    @Override
    public PrescriptionResponse evaluateAnswers(Page page, List<? extends IQuestion> answers) {
        int totalScore = 0;
        boolean matchFound = false;

        if (page == Page.PAGE_1) {
            for (IQuestion answer : answers) {
                if (answer instanceof YesNoQuestion yesNoQuestion) {
                    if (!yesNoQuestion.getUserSubmission().equalsIgnoreCase(yesNoQuestion.getCorrectAnswer())) {
                        return new PrescriptionResponse(false, yesNoQuestion.getSorryMessage());
                    }
                    totalScore += 1;
                    matchFound = true;
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Evaluation not implemented for this question type on page 1");
                }
            }

            if (!matchFound) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No matching question found for the response on page 1");
            }

            return new PrescriptionResponse(true, "Pass");

        } else if (page == Page.PAGE_2) {
            for (IQuestion answer : answers) {
                if (answer instanceof MultipleSelectionQuestion multipleSelectionQuestion) {
                    for (Option option : multipleSelectionQuestion.getOptions()) {
                        if (option.getText().equals(multipleSelectionQuestion.getUserSubmission())) {
                            totalScore += option.getScore();
                            matchFound = true;
                        }
                    }
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Evaluation not implemented for this question type on page 2");
                }
            }

            if (!matchFound) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No matching question found for the response on page 2");
            }

            boolean canPrescribe = totalScore >= PAGE_2_PASS_SCORE;
            String message = canPrescribe ? "Pass" : "Fail: Insufficient score on page 2";
            return new PrescriptionResponse(canPrescribe, message);

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Evaluation not implemented for this page");
        }
    }
}