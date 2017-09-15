package com.stardust.renai.survey.dao.repositories;


import com.stardust.renai.survey.models.Survey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;

import java.util.Set;

public interface SurveyRepository extends DataRepository<Survey, String> {
    default Class<Survey> getEntityClass() {
        return Survey.class;
    }

    @Query("{tags: {$all:?0}, status: ?1}")
    Page<Survey> findSurveysByTagsAndStatus(Set<String> tags, String status, Pageable page);
}
