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
        try {
            Survey result = service.submit(survey);
            if (result != null && result.getId().length() > 0) {
                return "SUCCESS:" + result.getSeqNo();
            }
        } catch (Exception e) {
            return "ERROR:" + e.getMessage();
        }

        return "ERROR:UNKNOWN";
    }

    @RequestMapping(value = "/{mobile}/verifycode", method = {RequestMethod.GET})
    public void getVerifyCode(@PathVariable String mobile, HttpSession session) {
        session.setAttribute(VERIFY_CODE_PREFIX + mobile, "336699");
    }

    @RequestMapping(value = "/{name}/users/{userId}", method = {RequestMethod.GET})
    public String surveySubmittedUser(@PathVariable String name, @PathVariable String userId) {
        if (service.hasSubmitted(name, userId)) {
            return userId;
        }
        return null;
    }

    @RequestMapping(value = "/{state}", method = {RequestMethod.GET})
    public Page<Survey> filterSurveys(@PathVariable String state,
                                      @RequestParam(value = "name", defaultValue = "") String name,
                                      @RequestParam(value = "page", defaultValue = "0") Integer page,
                                      @RequestParam(value = "size", defaultValue = "8") Integer size,
                                      @RequestParam(value = "sort", defaultValue = "id") String sort,
                                      @RequestParam(value = "tags", defaultValue = "") String tags,
                                      HttpSession session) {

        if (session.getAttribute("currentUser") == null) return null;
        Set<String> tagSet = tags.isEmpty() ? null : new HashSet<>(Arrays.asList(tags.split(",")));

        if ("pending".equals(state)) {
            return service.findPendingSurveys(name, tagSet, new PageRequest(page, size, new Sort(sort)));
        } else if ("handled".equals(state)) {
            return service.findHandledSurveys(name, tagSet, new PageRequest(page, size, new Sort(sort)));
        }

        return null;
    }

    @RequestMapping(value = "/excel", method = {RequestMethod.GET})
    public void export(@RequestParam String ids, HttpServletResponse response, HttpSession session) throws IOException, WriteException {
        if (session.getAttribute("currentUser") == null) return;

        if (ids != null && !ids.isEmpty()) {
            List<String> surveyIds = Arrays.asList(ids.split(","));
            response.reset();
            response.setHeader("Content-disposition", "attachment;filename=surveys-export.xls");
            response.setContentType("application/msexcel");
            service.export(surveyIds, response.getOutputStream());
        }
    }

    @RequestMapping(value = "/handled", method = {RequestMethod.POST})
    public void markAsHandled(@RequestParam String ids, HttpSession session) {
        if (session.getAttribute("currentUser") == null) return;

        if (ids != null && !ids.isEmpty()) {
            List<String> surveyIds = Arrays.asList(ids.split(","));
            service.updateSurveysStatus(surveyIds, "已处理");
        }
    }

    @RequestMapping(value = "/pending", method = {RequestMethod.POST})
    public void markAsPending(@RequestParam String ids, HttpSession session) {
        if (session.getAttribute("currentUser") == null) return;

        if (ids != null && !ids.isEmpty()) {
            List<String> surveyIds = Arrays.asList(ids.split(","));
            service.updateSurveysStatus(surveyIds, "待处理");
        }
    }
}
