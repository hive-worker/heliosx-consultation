package com.heliosx_consultation.heliosx_consultation.controller;

import com.heliosx_consultation.heliosx_consultation.model.*;
import com.heliosx_consultation.heliosx_consultation.service.IConsultationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ConsultationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IConsultationService consultationService;

    private YesNoQuestion yesNoQuestion;
    private MultipleSelectionQuestion multipleSelectionQuestion;

    @BeforeEach
    void setUp() {
        yesNoQuestion = new YesNoQuestion(
                "1",
                Section.SYMPTOMS,
                "Have you been itching more than usual?",
                TreatmentType.ALLERGY,
                "Yes",
                "You don't need treatment because you have not been itching."
        );
        Option option1 = new Option("Everyday", 5);
        Option option2 = new Option("Sometimes", 3);
        Option option3 = new Option("Rarely", 1);

        multipleSelectionQuestion = new MultipleSelectionQuestion(
                "2",
                Section.SYMPTOMS,
                "Have you been sneezing more than usual?",
                TreatmentType.ALLERGY,
                List.of(option1, option2, option3)
        );
    }

    @Test
    void getPage1QuestionsReturnsQuestions() throws Exception {
        List<IQuestion> questions = Collections.singletonList(yesNoQuestion);
        when(consultationService.getQuestions(any(TreatmentType.class), any(Page.class))).thenReturn(questions);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/consultation/questions/page1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("treatmentType", "ALLERGY"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{"id":"1","section":"SYMPTOMS","text":"Have you been itching more than usual?","treatmentType":"ALLERGY","correctAnswer":"Yes","sorryMessage":"You don't need treatment because you have not been itching.","userSubmission":""}]
                        """));

    }

    @Test
    void getPage2QuestionsReturnsQuestions() throws Exception {
        List<IQuestion> questions = Collections.singletonList(multipleSelectionQuestion);
        when(consultationService.getQuestions(any(TreatmentType.class), any(Page.class))).thenReturn(questions);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/consultation/questions/page2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("treatmentType", "ALLERGY"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{"id":"2","section":"SYMPTOMS","text":"Have you been sneezing more than usual?","treatmentType":"ALLERGY","options":[{"text":"Everyday","score":5},{"text":"Sometimes","score":3},{"text":"Rarely","score":1}],"userSubmission":""}]
                        """));
    }

    @Test
    void submitAnswersReturnsPrescriptionResponse() throws Exception {
        PrescriptionResponse response = new PrescriptionResponse(true, "You can prescribe this treatment.");
        when(consultationService.evaluateAnswers(any(Page.class), any(List.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/consultation/answers/page1")
                        .param("page", "PAGE_1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [
                                  {
                                    "id": "1",
                                    "section": "SYMPTOMS",
                                    "text": "Do you eat breakfast?",
                                    "treatmentType": "ALLERGY",
                                "correctAnswer": "Yes",
                                "sorryMessage":"You must eat breakfast to purchase this product!",
                                    "userSubmission": "Yes"
                                  }
                                ]
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"canPrescribe":true,"message":"You can prescribe this treatment."}"""));
    }

    @Test
    void submitPage2AnswersReturnsPrescriptionResponse() throws Exception {
        PrescriptionResponse response = new PrescriptionResponse(true, "You can prescribe this treatment.");
        when(consultationService.evaluateAnswers(any(Page.class), any(List.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/consultation/answers/page2")
                        .param("page", "PAGE_2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [
                                  {
                                    "id": "2",
                                    "section": "SYMPTOMS",
                                    "text": "Have you been sneezing more than usual?",
                                    "treatmentType": "ALLERGY",
                                    "options": [
                                      {"text": "Everyday", "score": 5},
                                      {"text": "Sometimes", "score": 3},
                                      {"text": "Rarely", "score": 1}
                                    ],
                                    "userSubmission": "Everyday"
                                  }
                                ]
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"canPrescribe":true,"message":"You can prescribe this treatment."}"""));
    }

    @Test
    void submitPage2AnswersReturnsFailResponse() throws Exception {
        PrescriptionResponse response = new PrescriptionResponse(false, "Fail: Insufficient score on page 2");
        when(consultationService.evaluateAnswers(any(Page.class), any(List.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/consultation/answers/page2")
                        .param("page", "PAGE_2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [
                                  {
                                    "id": "2",
                                    "section": "SYMPTOMS",
                                    "text": "Have you been sneezing more than usual?",
                                    "treatmentType": "ALLERGY",
                                    "options": [
                                      {"text": "Everyday", "score": 5},
                                      {"text": "Sometimes", "score": 3},
                                      {"text": "Rarely", "score": 1}
                                    ],
                                    "userSubmission": "Rarely"
                                  }
                                ]
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"canPrescribe":false,"message":"Fail: Insufficient score on page 2"}"""));
    }

}