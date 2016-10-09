package com.keto.optics.utils;

import jxl.CellView;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Created by ketanvichare on 10/9/16.
 */
public class ExcelUtils {

    private static WritableCellFormat timesBoldUnderline;
    private static WritableCellFormat times;

    public static void createLabel(WritableSheet sheet)
            throws WriteException {
        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(true);

        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(
                WritableFont.TIMES, 10, WritableFont.BOLD, false,
                UnderlineStyle.SINGLE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);

        CellView cv = new CellView();
        cv.setFormat(times);
        cv.setFormat(timesBoldUnderline);
        cv.setAutosize(true);

        // Write a few headers
        addCaption(sheet, 0, 0, "Header 1");
        addCaption(sheet, 1, 0, "This is second header");
    }

    private static void addCaption(WritableSheet sheet, int column, int row, String s)
            throws RowsExceededException, WriteException {
        Label label;
        label = new Label(column, row, s, timesBoldUnderline);
        sheet.addCell(label);
    }

    public static void createContent(WritableSheet sheet) throws WriteException,
            RowsExceededException {
        // Write a few number
        for (int i = 1; i < 10; i++) {
            // First column
            addNumber(sheet, 0, i, i + 10);
            // Second column
            addNumber(sheet, 1, i, i * i);
        }
        // Lets calculate the sum of it
        StringBuffer buf = new StringBuffer();
        buf.append("SUM(A2:A10)");
        Formula f = new Formula(0, 10, buf.toString());
        sheet.addCell(f);
        buf = new StringBuffer();
        buf.append("SUM(B2:B10)");
        f = new Formula(1, 10, buf.toString());
        sheet.addCell(f);

        // now a bit of text
        for (int i = 12; i < 20; i++) {
            // First column
            addLabel(sheet, 0, i, "Boring text " + i);
            // Second column
            addLabel(sheet, 1, i, "Another text");
        }

    }

    private static void addNumber(WritableSheet sheet, int column, int row,
                           Integer integer) throws WriteException, RowsExceededException {
        Number number;
        number = new Number(column, row, integer, times);
        sheet.addCell(number);
    }

    private static void addLabel(WritableSheet sheet, int column, int row, String s)
            throws WriteException, RowsExceededException {
        Label label;
        label = new Label(column, row, s, times);
        sheet.addCell(label);
    }
}
