package com.stardust.renai.survey.dao.repositories;


import com.stardust.renai.survey.models.Survey;

public interface SurveyRepository extends DataRepository<Survey, String> {
    default Class<Survey> getEntityClass() {
        return Survey.class;
    }
}
