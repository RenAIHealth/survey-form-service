package com.stardust.renai.survey.services;

import com.stardust.renai.survey.dao.repositories.SurveyRepository;
import com.stardust.renai.survey.dao.repositories.DataRepository;
import com.stardust.renai.survey.models.Question;
import com.stardust.renai.survey.models.Survey;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

@Service
public class SurveyServiceImpl extends AbstractEntityService<Survey> implements SurveyService {

    @Autowired
    SurveyRepository repository;

    @Override
    protected DataRepository getRepository() {
        return repository;
    }

    @Override
    public Page<Survey> findPendingSurveys(Set<String> tags, Pageable page) {
        return findSurveys(tags, "待处理" ,page);
    }

    @Override
    public Page<Survey> findHandledSurveys(Set<String> tags, Pageable page) {
        return findSurveys(tags, "已处理" ,page);
    }

    @Override
    public void export(List<String> ids, OutputStream outputStream) throws IOException, WriteException {
        WritableWorkbook workbook = Workbook.createWorkbook(outputStream);
        WritableFont boldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableCellFormat labelFormat = new WritableCellFormat(boldFont);
        labelFormat.setBorder(Border.NONE, BorderLineStyle.THIN);
        labelFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        labelFormat.setAlignment(Alignment.CENTRE);
        labelFormat.setWrap(false);
        WritableSheet sheet = workbook.createSheet("调查问卷", 0);

        sheet.addCell(new Label(0, 2, "手机", labelFormat));
        sheet.addCell(new Label(1, 2, "日期", labelFormat));
        sheet.addCell(new Label(2, 2, "邮寄地址", labelFormat));
        sheet.addCell(new Label(3, 2, "图片附件", labelFormat));
        int columnIdx = 4;
        Survey firstSurvey = repository.findOne(ids.get(0));

        for (Question question : firstSurvey.getQuestions()) {
            sheet.addCell(new Label(columnIdx, 2, question.getQuestion(), labelFormat));
            columnIdx++;
        }

        int rowIdx = 3;
        for (String id : ids) {
            Survey survey = repository.findOne(id);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sheet.addCell(new Label(0, rowIdx, survey.getMobile(), labelFormat));
            sheet.addCell(new Label(1, rowIdx, sdf.format(survey.getDate()), labelFormat));
            sheet.addCell(new Label(2, rowIdx, survey.getShippingAddress(), labelFormat));
            sheet.addHyperlink(new WritableHyperlink(3, rowIdx, new URL(survey.getPictureUrl())));
            columnIdx = 4;
            for (Question question : survey.getQuestions()) {
                sheet.addCell(new Label(columnIdx, rowIdx, question.getValue(), labelFormat));
                columnIdx++;
            }
            rowIdx++;
        }

        workbook.write();
        workbook.close();
    }

    @Override
    public void updateSurveysStatus(List<String> ids, String status) {
        for (String id : ids) {
            Survey survey = repository.findOne(id);
            survey.setStatus(status);
            repository.save(survey);
        }
    }

    Page<Survey> findSurveys(Set<String> tags, String status, Pageable page) {
        if (tags != null && !tags.isEmpty()) {
            return repository.findSurveysByTagsAndStatus(tags, status ,page);
        }
        return repository.findSurveysByStatus(status, page);
    }
}
