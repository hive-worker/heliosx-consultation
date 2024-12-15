package com.heliosx_consultation.heliosx_consultation.service;

import com.heliosx_consultation.heliosx_consultation.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConsultationServiceTest {

    private ConsultationService consultationService;

    @BeforeEach
    void setUp() {
        consultationService = new ConsultationService();
    }

    @Test
    void evaluateAnswersPage1ReturnsPass() {
        YesNoQuestion question1 = new YesNoQuestion("1", Section.ABOUT_YOU, "Are you over 18?", TreatmentType.ALLERGY, "yes", "Sorry, you must be at least 18 years old to proceed");
        question1.setUserSubmission("yes");
        YesNoQuestion question2 = new YesNoQuestion("2", Section.SYMPTOMS, "Do you have any allergies?", TreatmentType.ALLERGY, "yes", "Sorry, you must have allergies to proceed");
        question2.setUserSubmission("yes");

        List<YesNoQuestion> answers = List.of(question1, question2);
        PrescriptionResponse response = consultationService.evaluateAnswers(Page.PAGE_1, answers);

        assertTrue(response.isCanPrescribe());
        assertEquals("Pass", response.getMessage());
    }

    @Test
    void evaluateAnswersPage1ReturnsFail() {
        YesNoQuestion question1 = new YesNoQuestion("1", Section.ABOUT_YOU, "What is your age?", TreatmentType.ALLERGY, "yes", "Sorry, you must be at least 18 years old to proceed");
        question1.setUserSubmission("no");

        List<YesNoQuestion> answers = List.of(question1);
        PrescriptionResponse response = consultationService.evaluateAnswers(Page.PAGE_1, answers);

        assertFalse(response.isCanPrescribe());
        assertEquals("Sorry, you must be at least 18 years old to proceed", response.getMessage());
    }

    @Test
    void evaluateAnswersPage2ReturnsPass() {
        Option option1 = new Option("All of the time", 10);
        Option option2 = new Option("Most of the time", 9);
        MultipleSelectionQuestion question = new MultipleSelectionQuestion("3", Section.HEALTH, "Does your allergy prevent you from sleeping?", TreatmentType.ALLERGY, List.of(option1, option2));
        question.setUserSubmission("All of the time");

        List<MultipleSelectionQuestion> answers = List.of(question);
        PrescriptionResponse response = consultationService.evaluateAnswers(Page.PAGE_2, answers);

        assertTrue(response.isCanPrescribe());
        assertEquals("Pass", response.getMessage());
    }

    @Test
    void evaluateAnswersPage2ReturnsFail() {
        Option option1 = new Option("None of the time", 1);
        MultipleSelectionQuestion question = new MultipleSelectionQuestion("3", Section.HEALTH, "Does your allergy prevent you from sleeping?", TreatmentType.ALLERGY, List.of(option1));
        question.setUserSubmission("None of the time");

        List<MultipleSelectionQuestion> answers = List.of(question);
        PrescriptionResponse response = consultationService.evaluateAnswers(Page.PAGE_2, answers);

        assertFalse(response.isCanPrescribe());
        assertEquals("Fail: Insufficient score on page 2", response.getMessage());
    }

    @Test
    void evaluateAnswersPage1ThrowsExceptionForInvalidQuestionType() {
        MultipleSelectionQuestion question = new MultipleSelectionQuestion("1", Section.SYMPTOMS, "Do you eat breakfast?", TreatmentType.ALLERGY, List.of(new Option("Yes", 1)));
        question.setUserSubmission("Yes");

        List<MultipleSelectionQuestion> answers = List.of(question);

        assertThrows(ResponseStatusException.class, () -> consultationService.evaluateAnswers(Page.PAGE_1, answers));
    }

    @Test
    void evaluateAnswersPage2ThrowsExceptionForInvalidQuestionType() {
        YesNoQuestion question = new YesNoQuestion("1", Section.SYMPTOMS, "Do you eat breakfast?", TreatmentType.ALLERGY, "Yes", "You must eat breakfast to purchase this product!");
        question.setUserSubmission("Yes");

        List<YesNoQuestion> answers = List.of(question);

        assertThrows(ResponseStatusException.class, () -> consultationService.evaluateAnswers(Page.PAGE_2, answers));
    }
}