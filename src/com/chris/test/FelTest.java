package com.chris.test;

import com.chris.test.excelUtil.ExcelException;
import com.chris.test.excelUtil.ExcelSheet;
import com.chris.test.excelUtil.ExcelWorkbook;
import fel.script.Field;
import fel.script.FieldType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FelTest {
    public static final String SCRIPT_FILE = "/home/jianjianhong/Documents/code/IdeaProjects/FelScriptEngine/scriptTest2.txt";
    public static final String SCRIPT_FILE2 = "E:/code/Idea_workspace/FelScriptEngine/scriptTest.txt";
    public static final String DATA_FILE = "/home/jianjianhong/Documents/code/IdeaProjects/FelScriptEngine/data.xlsx";
    public static final String DATA_FILE2 = "E:/code/Idea_workspace/FelScriptEngine/data.xlsx";
    public static Map<String, Field> loadDataFromExcel() {
        try {
            ExcelWorkbook workbook = new ExcelWorkbook(new File(DATA_FILE));
            ExcelSheet sheet = workbook.getSheetAt(0);
            Map<String, Field> datas = new HashMap<>();
            datas.put("time", new Field("time", FieldType.List_String));
            datas.put("open", new Field("open", FieldType.List_Numeric));
            datas.put("high", new Field("high", FieldType.List_Numeric));
            datas.put("low", new Field("low", FieldType.List_Numeric));
            datas.put("close", new Field("close", FieldType.List_Numeric));
            datas.put("amount", new Field("amount", FieldType.List_Numeric));
            for(int r = 1; r < sheet.getRowCount(); r++) {
                List<String> rowDataList = sheet.getRowDataList(r, 6);
                ((List)datas.get("time").getValue()).add(rowDataList.get(0));
                ((List)datas.get("open").getValue()).add(Double.parseDouble(rowDataList.get(1)));
                ((List)datas.get("high").getValue()).add(Double.parseDouble(rowDataList.get(2)));
                ((List)datas.get("low").getValue()).add(Double.parseDouble(rowDataList.get(3)));
                ((List)datas.get("close").getValue()).add(Double.parseDouble(rowDataList.get(4)));
                ((List)datas.get("amount").getValue()).add(Double.parseDouble(rowDataList.get(5)));
            }

            workbook.close();
            return datas;

        } catch (ExcelException e) {
            e.printStackTrace();
            return null;
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
