package com.stardust.renai.survey.services;


import com.stardust.renai.survey.models.ResultReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    JdbcTemplate template;

    public ResultReport getPublishedReportByNumber(String number) {
        ResultReport report = null;
        try {
            report = template.queryForObject("SELECT * FROM MSSHOP_EXAMINATION_RESULT_REPORT WHERE STATUS='P' and EXAMINATION_NUMBER=?", new RowMapper<ResultReport>() {
                @Override
                public ResultReport mapRow(ResultSet rs, int i) throws SQLException {
                    ResultReport report = new ResultReport();
                    report.setId(rs.getInt("ID"));
                    report.setStatus(rs.getString("STATUS"));
                    report.setData(rs.getString("DATA"));
                    report.setComment(rs.getString("COMMENT"));
                    report.setReportDate(rs.getDate("REPORT_DATE"));
                    report.setNumber(rs.getString("EXAMINATION_NUMBER"));
                    report.setReportName(rs.getString("REPORT_NAME"));
                    report.setResult(rs.getString("RESULT"));
                    report.setTimeBeenSearched(rs.getInt("TIMES_BEEN_SEARCHED"));
                    return report;
                }
            }, new Object[]{number});

            template.update("UPDATE MSSHOP_EXAMINATION_RESULT_REPORT SET TIMES_BEEN_SEARCHED=? WHERE EXAMINATION_NUMBER=?", new Object [] {report.getTimeBeenSearched() + 1, number});

        } catch (Exception e) {}
        return report;
    }
}
