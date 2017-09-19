package com.stardust.renai.survey.controllers;

import com.stardust.renai.survey.models.ResultReport;
import com.stardust.renai.survey.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping({"/api/reports"})
@EnableAutoConfiguration
public class ReportController {

    @Autowired
    ReportService service;

    @RequestMapping(value = "/{number}", method = {RequestMethod.GET})
    public ResultReport search(@PathVariable String number) {
        return service.getPublishedReportByNumber(number);
    }
}
