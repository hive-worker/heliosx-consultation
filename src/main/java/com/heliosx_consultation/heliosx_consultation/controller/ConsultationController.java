package com.heliosx_consultation.heliosx_consultation.controller;

import com.heliosx_consultation.heliosx_consultation.model.*;
import com.heliosx_consultation.heliosx_consultation.service.IConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultation")
public class ConsultationController {

    @Autowired
    private IConsultationService consultationService;

    @GetMapping("/questions/page1")
    public List<IQuestion> getPage1Questions(@RequestParam("treatmentType") TreatmentType treatmentType) {
        return consultationService.getQuestions(treatmentType, Page.PAGE_1);
    }

    @GetMapping("/questions/page2")
    public List<IQuestion> getPage2Questions(@RequestParam("treatmentType") TreatmentType treatmentType) {
        return consultationService.getQuestions(treatmentType, Page.PAGE_2);
    }

    @PostMapping("/answers/page1")
    public PrescriptionResponse submitPage1Answers(@RequestBody List<YesNoQuestion> answers) {
        return consultationService.evaluateAnswers(Page.PAGE_1, answers);
    }

    @PostMapping("/answers/page2")
    public PrescriptionResponse submitPage2Answers(@RequestBody List<MultipleSelectionQuestion> answers) {
        return consultationService.evaluateAnswers(Page.PAGE_2, answers);
    }
}