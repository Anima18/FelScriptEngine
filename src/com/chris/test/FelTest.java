package com.chris.test;

import com.chris.test.excelUtil.ExcelException;
import com.chris.test.excelUtil.ExcelSheet;
import com.chris.test.excelUtil.ExcelWorkbook;
import fel.function.FunctionRepository;
import fel.script.Field;
import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.jakewharton.fliptables.FlipTable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FelTest {
    public static Map<String, List<Field>> loadDataFromExcel() {
        try {
            ExcelWorkbook workbook = new ExcelWorkbook(new File("E:/code/Idea_workspace/FelScriptEngine/data.xlsx"));
            ExcelSheet sheet = workbook.getSheetAt(0);
            Map<String, List<Field>> datas = new HashMap<>();
            datas.put("time", new ArrayList<>());
            datas.put("open", new ArrayList<>());
            datas.put("high", new ArrayList<>());
            datas.put("low", new ArrayList<>());
            datas.put("close", new ArrayList<>());
            datas.put("amount", new ArrayList<>());
            for(int r = 1; r < sheet.getRowCount(); r++) {
                List<String> rowDataList = sheet.getRowDataList(r, 6);
                datas.get("time").add(Field.ofValue(rowDataList.get(0)));
                datas.get("open").add(Field.ofValue(Double.parseDouble(rowDataList.get(1))));
                datas.get("high").add(Field.ofValue(Double.parseDouble(rowDataList.get(2))));
                datas.get("low").add(Field.ofValue(Double.parseDouble(rowDataList.get(3))));
                datas.get("close").add(Field.ofValue(Double.parseDouble(rowDataList.get(4))));
                datas.get("amount").add(Field.ofValue(Double.parseDouble(rowDataList.get(5))));
            }

            workbook.close();
            return datas;

        } catch (ExcelException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, List<Field>> initData() {
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


        Map<String, List<Field>> map = new HashMap<>();
        for(int i = 0;i<26;i++){
            String key = headers[i];
            List<Field> dataList = new ArrayList<>();
            if(i == 24) {
                for(int j = 0; j < 10; j++) {
                    dataList.add(Field.ofValue(true));
                }
            }else if(i == 25) {
                for(int j = 0; j < 10; j++) {
                    dataList.add(Field.ofValue("等待"));
                }
            }else {
                for(int j = 0; j < 10; j++) {
                    dataList.add(Field.ofValue((float)(j*10 + i)));
                }
            }

            map.put(key, dataList);
        }
        System.out.println(FlipTable.of(headers, datas));
        return map;
    }
}
