package com.huy.appnoithat.Service.FileExport.Operation.Excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StylistFactory {
    Workbook workbook;

    public StylistFactory(Workbook workbook) {
        this.workbook = workbook;
    }

    public CellStyle cellStyleFactory(Map<String, String> style) {
        CellStyle cellStyle = this.workbook.createCellStyle();
        if (style.containsKey(Stylist.Element.ALIGNMENT)) {
            switch (style.get(Stylist.Element.ALIGNMENT)) {
                case Stylist.Style.HorizontalAlignment_CENTER -> cellStyle.setAlignment(HorizontalAlignment.CENTER);
                case Stylist.Style.VerticalAlignment_CENTER -> cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                case Stylist.Style.Alignment_BOTH -> {
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                }
            }
        }
        if (style.containsKey(Stylist.Element.BORDER)) {
            BorderStyle borderStyle = BorderStyle.NONE;
            switch (style.get(Stylist.Element.BORDER)) {
                case Stylist.Style.BorderStyle_THIN -> {
                    borderStyle = BorderStyle.THIN;
                }
                case Stylist.Style.BorderStyle_MEDIUM -> {
                    borderStyle = BorderStyle.MEDIUM;
                }
                case Stylist.Style.BorderStyle_NO -> {
                    borderStyle = BorderStyle.NONE;
                }
            }
            cellStyle.setBorderTop(borderStyle);
            cellStyle.setBorderBottom(borderStyle);
            cellStyle.setBorderLeft(borderStyle);
            cellStyle.setBorderRight(borderStyle);
        }

        cellStyle.setWrapText(true);
        return cellStyle;
    }

    public Font fontStyleFactory(String style, int size) {
        Font font = this.workbook.createFont();
        switch (style) {
            case Stylist.Style.Font_TimeNewRoman_NORMAL -> {
                font.setFontName("Times New Roman");
                font.setBold(false);
                font.setItalic(false);
            }
            case Stylist.Style.Font_TimeNewRoman_BOLD -> {
                font.setFontName("Times New Roman");
                font.setBold(true);
                font.setItalic(false);
            }
            case Stylist.Style.Font_TimeNewRoman_ITALIC -> {
                font.setFontName("Times New Roman");
                font.setBold(false);
                font.setItalic(true);
            }
            case Stylist.Style.Font_TimeNewRoman_BOLDTALIC -> {
                font.setFontName("Times New Roman");
                font.setBold(true);
                font.setItalic(true);
            }
        }
        font.setFontHeightInPoints((short) size);
        return font;
    }

    public RichTextString textStyleFactory(String style, String data, int size) {
        int endPos = 0;
        if (data.contains(":")) {
            endPos = data.indexOf(":");
        } else {
            endPos = data.length();
        }
        RichTextString textString = new XSSFRichTextString(data);
        switch (style) {
            case Stylist.Style.Text_CUSTOMBOLD1 -> {
                textString.applyFont(0, data.length(), fontStyleFactory(Stylist.Style.Font_TimeNewRoman_NORMAL, size));
                textString.applyFont(0, endPos, fontStyleFactory(Stylist.Style.Font_TimeNewRoman_BOLD, size));
            }
            case Stylist.Style.Text_CUSTOMBOLD2 -> {
                textString.applyFont(0, data.length(), fontStyleFactory(Stylist.Style.Font_TimeNewRoman_NORMAL, size));
                textString.applyFont(0, endPos, fontStyleFactory(Stylist.Style.Font_TimeNewRoman_BOLDTALIC, size));
            }
            case Stylist.Style.Text_BOLDALL -> {
                textString.applyFont(0, data.length(), fontStyleFactory(Stylist.Style.Font_TimeNewRoman_BOLD, size));
            }
            case Stylist.Style.Text_NORMAL -> {
                textString.applyFont(0, data.length(), fontStyleFactory(Stylist.Style.Font_TimeNewRoman_NORMAL, size));
            }
            case Stylist.Style.Text_CUSTOMBOLD3 -> {
                // Define a regular expression pattern to match the desired text
                Pattern pattern = Pattern.compile("-\\s(.*?):");
                // Create a Matcher object to find matches in the input text
                Matcher matcher = pattern.matcher(data);
                textString.applyFont(0, data.length(), fontStyleFactory(Stylist.Style.Font_TimeNewRoman_NORMAL, size));
                while (matcher.find()) {
                    int start = matcher.start(1);
                    int end = matcher.end(1);
                    textString.applyFont(start, end, fontStyleFactory(Stylist.Style.Font_TimeNewRoman_BOLD, size));
                }
            }
        }
        return textString;
    }

    public void CellPresetFactory(Cell cell, String data, int size, String preset) {
        Map<String, String> cellStyle = null;
        String fontStyle = null;
        String textStyle = null;
        switch (preset) {
            case Stylist.Preset.NormalText_TimeNewRoman_CenterBoth_ThinBorder -> {
                cellStyle = Map.of(
                        Stylist.Element.ALIGNMENT, Stylist.Style.Alignment_BOTH,
                        Stylist.Element.BORDER, Stylist.Style.BorderStyle_THIN
                );
                fontStyle = Stylist.Style.Font_TimeNewRoman_NORMAL;
                textStyle = Stylist.Style.Text_NORMAL;
            }
            case Stylist.Preset.BoldText01_TimeNewRoman_CenterBoth_ThinBorder -> {
                cellStyle = Map.of(
                        Stylist.Element.ALIGNMENT, Stylist.Style.Alignment_BOTH,
                        Stylist.Element.BORDER, Stylist.Style.BorderStyle_THIN
                );
                fontStyle = Stylist.Style.Font_TimeNewRoman_BOLD;
                textStyle = Stylist.Style.Text_CUSTOMBOLD1;
            }
            case Stylist.Preset.BoldText02_TimeNewRoman_CenterBoth_ThinBorder -> {
                cellStyle = Map.of(
                        Stylist.Element.ALIGNMENT, Stylist.Style.Alignment_BOTH,
                        Stylist.Element.BORDER, Stylist.Style.BorderStyle_THIN
                );
                fontStyle = Stylist.Style.Font_TimeNewRoman_BOLD;
                textStyle = Stylist.Style.Text_CUSTOMBOLD2;
            }
            case Stylist.Preset.NormalText_TimeNewRoman_VerticalCenter_ThinBorder -> {
                cellStyle = Map.of(
                        Stylist.Element.ALIGNMENT, Stylist.Style.VerticalAlignment_CENTER,
                        Stylist.Element.BORDER, Stylist.Style.BorderStyle_THIN
                );
                fontStyle = Stylist.Style.Font_TimeNewRoman_NORMAL;
                textStyle = Stylist.Style.Text_NORMAL;
            }
            case Stylist.Preset.BoldText01_TimeNewRoman_VerticalCenter_ThinBorder -> {
                cellStyle = Map.of(
                        Stylist.Element.ALIGNMENT, Stylist.Style.VerticalAlignment_CENTER,
                        Stylist.Element.BORDER, Stylist.Style.BorderStyle_THIN
                );
                fontStyle = Stylist.Style.Font_TimeNewRoman_BOLD;
                textStyle = Stylist.Style.Text_CUSTOMBOLD1;
            }
            case Stylist.Preset.BoldText02_TimeNewRoman_VerticalCenter_ThinBorder -> {
                cellStyle = Map.of(
                        Stylist.Element.ALIGNMENT, Stylist.Style.VerticalAlignment_CENTER,
                        Stylist.Element.BORDER, Stylist.Style.BorderStyle_THIN
                );
                fontStyle = Stylist.Style.Font_TimeNewRoman_BOLD;
                textStyle = Stylist.Style.Text_CUSTOMBOLD2;
            }
            case Stylist.Preset.BoldAll_TimeNewRoman_VerticalCenter_ThinBorder -> {
                cellStyle = Map.of(
                        Stylist.Element.ALIGNMENT, Stylist.Style.VerticalAlignment_CENTER,
                        Stylist.Element.BORDER, Stylist.Style.BorderStyle_THIN
                );
                fontStyle = Stylist.Style.Font_TimeNewRoman_BOLD;
                textStyle = Stylist.Style.Text_BOLDALL;
            }
            case Stylist.Preset.BoldAll_TimeNewRoman_CenterBoth_ThinBorder -> {
                cellStyle = Map.of(
                        Stylist.Element.ALIGNMENT, Stylist.Style.Alignment_BOTH,
                        Stylist.Element.BORDER, Stylist.Style.BorderStyle_THIN
                );
                fontStyle = Stylist.Style.Font_TimeNewRoman_BOLD;
                textStyle = Stylist.Style.Text_BOLDALL;
            }
            case Stylist.Preset.BoldText03_TimeNewRoman_VerticalCenter_ThinBorder -> {
                cellStyle = Map.of(
                        Stylist.Element.ALIGNMENT, Stylist.Style.VerticalAlignment_CENTER,
                        Stylist.Element.BORDER, Stylist.Style.BorderStyle_THIN
                );
                fontStyle = Stylist.Style.Font_TimeNewRoman_BOLD;
                textStyle = Stylist.Style.Text_CUSTOMBOLD3;
            }
            case Stylist.Preset.BoldText01_TimeNewRoman_VerticalCenter_NoBorder -> {
                cellStyle = Map.of(
                        Stylist.Element.ALIGNMENT, Stylist.Style.VerticalAlignment_CENTER,
                        Stylist.Element.BORDER, Stylist.Style.BorderStyle_NO
                );
                fontStyle = Stylist.Style.Font_TimeNewRoman_BOLD;
                textStyle = Stylist.Style.Text_CUSTOMBOLD1;
            }
            case Stylist.Preset.Italic01_TimeNewRoman_VerticalCenter_NoBorder -> {
                cellStyle = Map.of(
                        Stylist.Element.ALIGNMENT, Stylist.Style.VerticalAlignment_CENTER,
                        Stylist.Element.BORDER, Stylist.Style.BorderStyle_NO
                );
                fontStyle = Stylist.Style.Font_TimeNewRoman_ITALIC;
                textStyle = Stylist.Style.Text_NORMAL;
            }

        }
        applyPreset(cell, data, size, cellStyle, fontStyle, textStyle);
    }

    private void applyPreset(Cell cell, String data, int size, Map<String, String> cellStyle, String fontStyle, String textStyle) {
        CellStyle appliedCellStyle = cellStyleFactory(cellStyle);
        RichTextString appliedTextString = textStyleFactory(textStyle, data, size);
        Font appliedFontStyle = fontStyleFactory(fontStyle, size);
        appliedCellStyle.setFont(appliedFontStyle);
        cell.setCellValue(appliedTextString);
        if (data.length() >= 80 && data.contains("\n")) {
            cell.getRow().setHeight((short) (cell.getRow().getHeight() + 800));
        } else {
            cell.getRow().setHeight((short) (cell.getRow().getHeight() + 50));
        }
        cell.setCellStyle(appliedCellStyle);
    }
}
