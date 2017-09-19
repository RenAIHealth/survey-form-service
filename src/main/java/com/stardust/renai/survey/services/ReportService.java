package com.stardust.renai.survey.services;


import com.stardust.renai.survey.models.ResultReport;

public interface ReportService {
    ResultReport getPublishedReportByNumber(String number);
}
