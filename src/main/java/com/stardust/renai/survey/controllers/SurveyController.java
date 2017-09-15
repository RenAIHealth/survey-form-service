package com.stardust.renai.survey.controllers;

import com.stardust.renai.survey.models.Survey;
import com.stardust.renai.survey.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
        // Object code = session.getAttribute(VERIFY_CODE_PREFIX + survey.getMobile());

        // if (code != null && verifyCode.equals(code.toString())) {
        survey.setStatus("待处理");
        survey.setDate(new Date());
        Survey result = service.save(survey);
        if (result != null && result.getId().length() > 0) {
            return "SUCCESS";
        }
        // } else {
        //   return "验证码错误";
        // }
        return "ERROR";
    }

    @RequestMapping(value = "/{mobile}/verifycode", method = {RequestMethod.GET})
    public void getVerifyCode(@PathVariable String mobile, HttpSession session) {
        session.setAttribute(VERIFY_CODE_PREFIX + mobile, "336699");
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public Page<Survey> filterSurveys(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                      @RequestParam(value = "size", defaultValue = "8") Integer size,
                                      @RequestParam(value = "sort", defaultValue = "id") String sort) {
        Set<String> tags = new HashSet<>();
        tags.add("男");
        return service.findPendingSurveys(tags, new PageRequest(page, size, new Sort(sort)));
    }
}
