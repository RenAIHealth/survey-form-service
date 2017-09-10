package com.stardust.renai.survey.services;

import com.stardust.renai.survey.dao.repositories.SurveyRepository;
import com.stardust.renai.survey.dao.repositories.DataRepository;
import com.stardust.renai.survey.models.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyServiceImpl extends AbstractEntityService<Survey> implements SurveyService {

    @Autowired
    SurveyRepository repository;

    @Override
    protected DataRepository getRepository() {
        return repository;
    }
}
