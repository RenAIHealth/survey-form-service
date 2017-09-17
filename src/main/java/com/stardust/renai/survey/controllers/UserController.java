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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;


@CrossOrigin("*")
@RestController
@RequestMapping({"/api/users"})
@EnableAutoConfiguration
public class UserController {

    @RequestMapping(value = "/session", method = {RequestMethod.POST})
    public String login(@RequestBody Map<String, String> userInfo, HttpSession session) {
        if ("gumsho".equals(userInfo.get("username"))
                && "blueStory#01".equals(userInfo.get("password"))) {
            session.setAttribute("currentUser", userInfo.get("username"));
            return "SUCCESS";
        }
        return "ERROR";
    }
}
