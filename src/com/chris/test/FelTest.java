package com.chris.test;

import com.chris.test.excelUtil.ExcelException;
import com.chris.test.excelUtil.ExcelSheet;
import com.chris.test.excelUtil.ExcelWorkbook;
import com.greenpineyu.fel.common.StringUtils;
import fel.script.Field;
import fel.script.FieldType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

public class FelTest {

    public static Map<String, Field> loadDataFromExcel(String dataFilePath) {

        try {
            ExcelWorkbook workbook = new ExcelWorkbook(new File(dataFilePath));
            ExcelSheet sheet = workbook.getSheetAt(0);
            Map<String, Field> datas = new HashMap<>();
            datas.put("A", new Field("A", FieldType.List_String));
            datas.put("B", new Field("B", FieldType.List_Numeric));
            datas.put("C", new Field("C", FieldType.List_Numeric));
            datas.put("D", new Field("D", FieldType.List_Numeric));
            datas.put("E", new Field("E", FieldType.List_Numeric));
            datas.put("F", new Field("F", FieldType.List_Numeric));

            int columnCount = sheet.getRow(0).getCellCount();
            for(int i = 6; i < columnCount; i++) {
                String columnCode = convertColumnIndexToColumnName(i);
                datas.put(columnCode, new Field(columnCode, FieldType.List_String));
            }

            for(int r = 1; r < sheet.getRowCount(); r++) {
                System.out.println("========"+r+"=========");
                List<String> rowDataList = sheet.getRowDataList(r, columnCount);
                if(rowDataList.get(0) == null) {
                    break;
                }
                ((List)datas.get("A").getValue()).add(rowDataList.get(0));
                ((List)datas.get("B").getValue()).add(Double.parseDouble(rowDataList.get(1)));
                ((List)datas.get("C").getValue()).add(Double.parseDouble(rowDataList.get(2)));
                ((List)datas.get("D").getValue()).add(Double.parseDouble(rowDataList.get(3)));
                ((List)datas.get("E").getValue()).add(Double.parseDouble(rowDataList.get(4)));
                ((List)datas.get("F").getValue()).add(Double.parseDouble(rowDataList.get(5)));

                for(int i = 6; i < columnCount; i++) {
                    String columnCode = convertColumnIndexToColumnName(i);
                    ((List)datas.get(columnCode).getValue()).add(rowDataList.get(i));
                }
            }

            workbook.close();
            return datas;

        } catch (ExcelException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String convertColumnIndexToColumnName(int index){
        index = index + 1;
        int system = 26;
        char[] digArray = new char[100];
        int i = 0;

        while (index > 0){
            int mod = index % system;
            if (mod == 0)
                mod = system;
            digArray[i++] = (char)(mod - 1 + 'A');
            index = (index - 1) / 26;
        }

        StringBuilder sb = new StringBuilder(i);
        for (int j = i - 1; j >= 0; j--){
            sb.append(digArray[j]);
        }
        return sb.toString();
    }

    public static void updateFelResult(String dataFilePath, Map<String, Field> dataSet, String resultFilePath, Map<String, Field> resultSet) {
        try {
            Workbook workbook = new XSSFWorkbook(new FileInputStream(dataFilePath));
            Sheet resultSheet = workbook.createSheet("计算结果");
            Sheet compareSheet = workbook.createSheet("数据比较");
            resultSheet.setDefaultColumnWidth(15);
            compareSheet.setDefaultColumnWidth(15);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            CellStyle successCellStyle = workbook.createCellStyle();
            successCellStyle.setAlignment(HorizontalAlignment.CENTER);
            successCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            successCellStyle.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());// 设置背景色
            successCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle errorCellStyle = workbook.createCellStyle();
            errorCellStyle.setAlignment(HorizontalAlignment.CENTER);
            errorCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            errorCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());// 设置背景色
            errorCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);


            List<String> keyList = new ArrayList<>();
            Iterator<String> keyIterator = resultSet.keySet().iterator();
            while (keyIterator.hasNext()) {
                String key = keyIterator.next();
                keyList.add(key);
            }

            Row resultTitleRow = resultSheet.createRow(0);
            Row compareTitleRow = compareSheet.createRow(0);
            for(int i = 0; i<keyList.size(); i++) {
                Cell resultCell = resultTitleRow.createCell(i);
                resultCell.setCellStyle(cellStyle);
                resultCell.setCellValue(keyList.get(i));

                Cell compareCell = compareTitleRow.createCell(i);
                compareCell.setCellStyle(cellStyle);
                compareCell.setCellValue(keyList.get(i));
            }


            for(int i = 0; i<((List<Object>)resultSet.get(keyList.get(0)).getValue()).size(); i++) {
                Row resultRow = resultSheet.createRow(i+1);
                Row compareRow = compareSheet.createRow(i+1);
                for(int m = 0; m<keyList.size(); m++) {
                    Cell resultCell = resultRow.createCell(m);
                    resultCell.setCellStyle(cellStyle);
                    resultCell.setCellValue(((List<Object>)resultSet.get(keyList.get(m)).getValue()).get(i).toString());

                    Cell compareCell = compareRow.createCell(m);

                    if(m < 6) {
                        compareCell.setCellValue(((List<Object>)resultSet.get(keyList.get(m)).getValue()).get(i).toString());
                        compareCell.setCellStyle(cellStyle);
                    }else {
                        List<Object> resultList = (List<Object>)resultSet.get(keyList.get(m)).getValue();
                        Object result = resultList.get(i);
                        Field dateSetField = dataSet.get(keyList.get(m));
                        if(dateSetField == null) {
                            break;
                        }

                        List<Object> datasetList = (List<Object>)dateSetField.getValue();
                        Object data = datasetList.get(i);

                        if(data == null || data.toString().equals(result.toString())) {
                            compareCell.setCellValue(0);
                            compareCell.setCellStyle(successCellStyle);
                        }else {
                            compareCell.setCellValue(1);
                            compareCell.setCellStyle(errorCellStyle);
                        }
                    }
                }
            }

            workbook.write(new FileOutputStream(resultFilePath));
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Field> initData() {
        String[] headers = new String[26];
        for(int i = 0;i<26;i++){
            headers[i] = String.valueOf(Character.toUpperCase( (char)(97+i)));
        }

        String[][] datas = new String[10][26];
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 26; j++) {
                if(j == 24) {
                    datas[i][j] = "True";
                }else if(j == 25) {
                    datas[i][j] = "等待";
                }else {
                    datas[i][j] = String.valueOf(i*10 + j);
                }
            }
        }


        Map<String, Field> map = new HashMap<>();
        for(int i = 0;i<26;i++){
            String key = headers[i];
            Field field = Field.ofValue(new ArrayList<>());
            if(i == 24) {
                for(int j = 0; j < 10; j++) {
                    ((List)field.getValue()).add(true);
                }
            }else if(i == 25) {
                for(int j = 0; j < 10; j++) {
                    ((List)field.getValue()).add("等待");
                }
            }else {
                for(int j = 0; j < 10; j++) {
                    ((List)field.getValue()).add(j*10 + i);
                }
            }

            map.put(key, field);
        }
        //System.out.println(FlipTable.of(headers, datas));
        return map;
    }
}
