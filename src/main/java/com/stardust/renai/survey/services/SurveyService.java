package com.stardust.renai.survey.services;


import com.stardust.renai.survey.models.Survey;
import jxl.write.WriteException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;

public interface SurveyService extends EntityService<Survey> {
    Page<Survey> findPendingSurveys(Set<String> tags, Pageable page);
    Page<Survey> findHandledSurveys(Set<String> tags, Pageable page);
    void export(List<String> ids, OutputStream outputStream) throws IOException, WriteException;
    void updateSurveysStatus(List<String> ids, String status);
}
