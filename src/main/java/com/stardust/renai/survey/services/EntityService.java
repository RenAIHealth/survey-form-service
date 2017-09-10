package com.stardust.renai.survey.services;

import com.stardust.renai.survey.common.Selection;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EntityService<T> {
    T get(String id);

    Page<T> list(int page, int size, String sortBy);

    Page<T> list(int page, int size, String sortBy, List<Selection> selections);

    T save(T model);

    void remove(T model);

    void remove(String id);
}
