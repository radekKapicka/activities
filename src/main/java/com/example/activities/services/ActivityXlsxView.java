package com.example.activities.services;

import com.example.activities.model.*;
import com.example.activities.repositories.WorkRegisterRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActivityXlsxView extends AbstractXlsxView {

    Activity activity;

    WorkRegisters workRegisters;

    public ActivityXlsxView(Activity activity, WorkRegisters workRegisters) {

        this.activity = activity;
        this.workRegisters = workRegisters;
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

        List<WorkRegister> registers = new ArrayList<>();

        for (WorkRegister w : this.workRegisters.getRegisterList()){
            if((w.getActivity() == activity) && (w.getActivity().getState().equals("done"))){
                registers.add(w);
            }
        }

        int row = 0;
        for (WorkRegister w : registers) {
            Row courseRow = sheet.createRow(row);
            Cell c1 = courseRow.createCell(0);
                c1.setCellValue(w.getActivity().getId());
                courseRow.createCell(1).setCellValue(w.getActivity().getName());
                courseRow.createCell(2).setCellValue(w.getUser().getUsername());
                courseRow.createCell(3).setCellValue(w.getTimeFrom().toString());
                courseRow.createCell(4).setCellValue(w.getTimeTo().toString());
                courseRow.createCell(5).setCellValue(w.getPartOfTime());
                row++;

        }

    }
}
