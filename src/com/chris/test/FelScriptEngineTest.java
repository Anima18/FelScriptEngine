package com.chris.test;

import com.jakewharton.fliptables.FlipTable;
import fel.FelScriptEngine;
import fel.FelScriptException;
import fel.script.Field;
import fel.script.ScriptVar;
import fel.util.Constant;
import fel.util.ScriptModel;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FelScriptEngineTest {
    public static void main(String[] args) {
        try {
            File directory = new File("");//参数为空
            String courseFile = directory.getCanonicalPath();
            String dataFilePath = courseFile + File.separator + "data.xlsx";
            String scriptFilePath = courseFile + File.separator + "scriptTest.txt";

            String resultFilePath = courseFile + File.separator + "data_result.xlsx";
            System.out.println(dataFilePath);
            System.out.println(scriptFilePath);
            long startTime = System.currentTimeMillis();
            Map<String, Field> dataSet = FelTest.loadDataFromExcel(dataFilePath);

            Map<String, Field> dataSource = new HashMap<>();
            dataSource.put("A", dataSet.get("A"));
            dataSource.put("B", dataSet.get("B"));
            dataSource.put("C", dataSet.get("C"));
            dataSource.put("D", dataSet.get("D"));
            dataSource.put("E", dataSet.get("E"));
            dataSource.put("F", dataSet.get("F"));
            FelScriptEngine felScriptEngine = new FelScriptEngine.Builder()
                    .setScript(new File(scriptFilePath))
                    .setDataSource(dataSource)
                    .setModel(ScriptModel.Full)
                    .build();
            List<ScriptVar> varList = felScriptEngine.eval();
            long endTime = System.currentTimeMillis();
            System.out.println("程序运行时间： " + (endTime - startTime) + "ms");

            showValue(varList);

            Map<String, Field> resultSet = (Map<String, Field>) felScriptEngine.getEngine().getContext().get(Constant.DATA_SET);
            FelTest.updateFelResult(dataFilePath, dataSet, resultFilePath, resultSet);
        } catch (FelScriptException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(Log.getLogs());
    }

    private static void showValue(List<ScriptVar> varList) {
        if (varList.get(0).getValue() instanceof List) {
            showListValue(varList);
        } else {
            showItemValue(varList);
        }
    }

    private static void showItemValue(List<ScriptVar> varList) {
        String[] header = new String[varList.size()];
        String[][] dataList = new String[1][varList.size()];
        for (int i = 0; i < varList.size(); i++) {
            ScriptVar var = varList.get(i);
            header[i] = var.getName();
            dataList[0][i] = var.getValue().toString();
        }
        System.out.println(FlipTable.of(header, dataList));
    }

    private static void showListValue(List<ScriptVar> varList) {
        int dataListSize = ((List) varList.get(0).getValue()).size();
        String[] header = new String[varList.size() + 1];
        header[0] = "序号";
        String[][] dataList = new String[1][varList.size() + 1];
        for (int i = 0; i < varList.size(); i++) {
            header[i + 1] = varList.get(i).getName();
        }


        for (int i = 0; i < header.length; i++) {
            for (int j = 0; j < 1; j++) {
                if (i == 0) {
                    dataList[j][i] = j + "";
                } else {
                    List valueList = (List) varList.get(i - 1).getValue();
                    Object value = valueList.get(dataListSize - 1);
                    dataList[j][i] = value == null ? "" : value.toString();
                }
            }
        }

        System.out.println(FlipTable.of(header, dataList));
    }
}
