package com.stardust.renai.survey.models;

import java.util.Date;

public class ResultReport {
    private Integer id;
    private String number;
    private String status;
    private String data;
    private int timeBeenSearched;
    private String result;
    private Date reportDate;
    private String reportName;
    private String comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getTimeBeenSearched() {
        return timeBeenSearched;
    }

    public void setTimeBeenSearched(int timeBeenSearched) {
        this.timeBeenSearched = timeBeenSearched;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
