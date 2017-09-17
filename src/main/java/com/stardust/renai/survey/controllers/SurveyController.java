package com.stardust.renai.survey.controllers;

import com.stardust.renai.survey.models.Survey;
import com.stardust.renai.survey.services.SurveyService;
import jxl.write.WriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;


@CrossOrigin("*")
@RestController
@RequestMapping({"/api/surveys"})
@EnableAutoConfiguration
public class SurveyController {
    @Autowired
    SurveyService service;

    private final String VERIFY_CODE_PREFIX = "VERIFY_CODE_FOR_";

    @RequestMapping(method = {RequestMethod.POST})
    public String submit(@RequestBody Survey survey, @RequestParam String verifyCode, HttpSession session) {
        survey.setStatus("待处理");
        survey.setDate(new Date());
        Survey result = service.save(survey);
        if (result != null && result.getId().length() > 0) {
            return "SUCCESS";
        }
        return "ERROR";
    }

    @RequestMapping(value = "/{mobile}/verifycode", method = {RequestMethod.GET})
    public void getVerifyCode(@PathVariable String mobile, HttpSession session) {
        session.setAttribute(VERIFY_CODE_PREFIX + mobile, "336699");
    }

    @RequestMapping(value = "/{state}", method = {RequestMethod.GET})
    public Page<Survey> filterSurveys(@PathVariable String state,
                                      @RequestParam(value = "page", defaultValue = "0") Integer page,
                                      @RequestParam(value = "size", defaultValue = "8") Integer size,
                                      @RequestParam(value = "sort", defaultValue = "id") String sort,
                                      @RequestParam(value = "tags", defaultValue = "") String tags) {
        Set<String> tagSet = tags.isEmpty() ? null : new HashSet<>(Arrays.asList(tags.split(",")));

        if ("pending".equals(state)) {
            return service.findPendingSurveys(tagSet, new PageRequest(page, size, new Sort(sort)));
        } else if ("handled".equals(state)) {
            return service.findHandledSurveys(tagSet, new PageRequest(page, size, new Sort(sort)));
        }

        return null;
    }

    @RequestMapping(value = "/excel", method = {RequestMethod.GET})
    public void export(@RequestParam String ids, HttpServletResponse response) throws IOException, WriteException {
        if (ids != null && !ids.isEmpty()) {
            List<String> surveyIds = Arrays.asList(ids.split(","));
            response.reset();
            response.setHeader("Content-disposition", "attachment;filename=surveys-export.xls");
            response.setContentType("application/msexcel");
            service.export(surveyIds, response.getOutputStream());
        }
    }

    @RequestMapping(value = "/handled", method = {RequestMethod.POST})
    public void markAsHandled(@RequestParam String ids) {
        if (ids != null && !ids.isEmpty()) {
            List<String> surveyIds = Arrays.asList(ids.split(","));
            service.updateSurveysStatus(surveyIds, "已处理");
        }
    }

    @RequestMapping(value = "/pending", method = {RequestMethod.POST})
    public void markAsPending(@RequestParam String ids) {
        if (ids != null && !ids.isEmpty()) {
            List<String> surveyIds = Arrays.asList(ids.split(","));
            service.updateSurveysStatus(surveyIds, "待处理");
        }
    }
}
