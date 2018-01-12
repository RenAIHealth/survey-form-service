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

    @Query("{name:?0, tags: {$all:?1}, status: ?2}")
    Page<Survey> findSurveysByTagsAndStatus(String name, Set<String> tags, String status, Pageable page);

    @Query("{tags: {$all:?0}, status: ?1}")
    Page<Survey> findSurveysByTagsAndStatus(Set<String> tags, String status, Pageable page);

    @Query("{specimen: ?0, string: ?1}")
    Page<Survey> findSurveysBySpecimenAndStatus(String specimen, String status, Pageable page);

    @Query("{name: ?0, status: ?1}")
    Page<Survey> findSurveysByStatus(String name, String status, Pageable page);

    @Query("{status: ?0}")
    Page<Survey> findSurveysByStatus(String status, Pageable page);
}
