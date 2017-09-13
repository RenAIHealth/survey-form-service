package com.stardust.renai.survey.controllers;

import com.stardust.renai.survey.models.Survey;
import com.stardust.renai.survey.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


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
}
