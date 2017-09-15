package com.stardust.renai.survey.services;

import com.stardust.renai.survey.dao.repositories.SurveyRepository;
import com.stardust.renai.survey.dao.repositories.DataRepository;
import com.stardust.renai.survey.models.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SurveyServiceImpl extends AbstractEntityService<Survey> implements SurveyService {

    @Autowired
    SurveyRepository repository;

    @Override
    protected DataRepository getRepository() {
        return repository;
    }

    @Override
    public Page<Survey> findPendingSurveys(Set<String> tags, Pageable page) {
        return repository.findSurveysByTagsAndStatus(tags, "待处理" ,page);
    }

    @Override
    public Page<Survey> findHandledSurveys(Set<String> tags, Pageable page) {
        return repository.findSurveysByTagsAndStatus(tags, "已处理" ,page);
    }
}
