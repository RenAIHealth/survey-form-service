package com.stardust.renai.survey.common;

public interface SelectionQueryProvider<T> {
    T toQueryObject(Selection selection);
}
