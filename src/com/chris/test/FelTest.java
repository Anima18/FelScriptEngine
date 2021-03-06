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

    public static final String SCRIPT_FILE = "/home/jianjianhong/Documents/code/IdeaProjects/FelScriptEngine/scriptTest3.txt";
    public static final String SCRIPT_FILE2 = "E:/code/Idea_workspace/FelScriptEngine/scriptTest3.txt";
    public static final String DATA_FILE = "/home/jianjianhong/Documents/code/IdeaProjects/FelScriptEngine/data.xlsx";
    public static final String DATA_FILE2 = "E:/code/Idea_workspace/FelScriptEngine/data2.xlsx";
    public static Map<String, Field> loadDataFromExcel() {
        try {
            ExcelWorkbook workbook = new ExcelWorkbook(new File(DATA_FILE2));
            ExcelSheet sheet = workbook.getSheetAt(0);
            Map<String, Field> datas = new HashMap<>();
            datas.put("A", new Field("A", FieldType.List_String));
            datas.put("B", new Field("B", FieldType.List_Numeric));
            datas.put("C", new Field("C", FieldType.List_Numeric));
            datas.put("D", new Field("D", FieldType.List_Numeric));
            datas.put("E", new Field("E", FieldType.List_Numeric));
            datas.put("F", new Field("F", FieldType.List_Numeric));
            for(int r = 1; r < sheet.getRowCount(); r++) {
                List<String> rowDataList = sheet.getRowDataList(r, 6);
                ((List)datas.get("A").getValue()).add(rowDataList.get(0));
                ((List)datas.get("B").getValue()).add(Double.parseDouble(rowDataList.get(1)));
                ((List)datas.get("C").getValue()).add(Double.parseDouble(rowDataList.get(2)));
                ((List)datas.get("D").getValue()).add(Double.parseDouble(rowDataList.get(3)));
                ((List)datas.get("E").getValue()).add(Double.parseDouble(rowDataList.get(4)));
                ((List)datas.get("F").getValue()).add(Double.parseDouble(rowDataList.get(5)));
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
