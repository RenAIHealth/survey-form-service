package com.stardust.renai.survey.controllers;

import com.stardust.renai.survey.models.Survey;
import com.stardust.renai.survey.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping({"/surveys"})
@EnableAutoConfiguration
public class SurveyController  {
    @Autowired
    SurveyService service;

    @RequestMapping(method = {RequestMethod.POST})
    public String submit(@RequestBody Survey survey) {
        return service.save(survey).getId();
    }
}
