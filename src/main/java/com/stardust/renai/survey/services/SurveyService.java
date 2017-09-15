package com.stardust.renai.survey.services;


import com.stardust.renai.survey.models.Survey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface SurveyService extends EntityService<Survey> {
    Page<Survey> findPendingSurveys(Set<String> tags, Pageable page);
    Page<Survey> findHandledSurveys(Set<String> tags, Pageable page);
}
