package com.example.activities.services;

import com.example.activities.model.Activities;
import com.example.activities.model.Activity;
import com.example.activities.model.User;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActivityXlsxView extends AbstractXlsxView {

    private final User user;
    Activities activities;
    public ActivityXlsxView(Activities activities, User user) {

        this.activities = activities;
        this.user = user;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> map
            , Workbook workbook, HttpServletRequest httpServletRequest,
                                      HttpServletResponse httpServletResponse) throws Exception {

        Sheet sheet = workbook.createSheet("activity_report");
        CellStyle highlight = workbook.createCellStyle();
        highlight.cloneStyleFrom(sheet.getColumnStyle(0));
        highlight.setFillPattern(FillPatternType.SOLID_FOREGROUND );
        highlight.setFillForegroundColor(IndexedColors.RED.getIndex());

        List<Activity> activityPom = this.activities.getActivityList();
        List<Activity> activityFilter = new ArrayList<>();

        for (int i = 0;i<activityPom.size();i++){
            if(activityPom.get(i).getUser() == this.user){
                activityFilter.add(activityPom.get(i));
            }
        }

        int row = 0;
        for (Activity a : activityFilter) {
            Row courseRow = sheet.createRow(row);
            Cell c1 = courseRow.createCell(0);
                c1.setCellValue(a.getId());
                courseRow.createCell(1).setCellValue(a.getName());
                courseRow.createCell(2).setCellValue(a.getTime());
                courseRow.createCell(3).setCellValue(a.getTimeWorked());
                courseRow.createCell(4).setCellValue(a.getDateFrom().toString());
                courseRow.createCell(5).setCellValue(a.getDateTo().toString());
                row++;

        }

    }
}
