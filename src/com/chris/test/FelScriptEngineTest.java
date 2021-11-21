package com.chris.test;

import com.jakewharton.fliptables.FlipTable;
import fel.FelScriptEngine;
import fel.FelScriptException;
import fel.script.Field;
import fel.script.ScriptVar;

import java.io.File;
import java.util.List;
import java.util.Map;

import static com.chris.test.FelTest.SCRIPT_FILE2;

public class FelScriptEngineTest {
    public static void main(String[] args) {
        Map<String, Field> datas = FelTest.loadDataFromExcel();

        try {
            long startTime = System.currentTimeMillis();
            List<ScriptVar> varList = new FelScriptEngine.Builder()
                    .setScript(new File(SCRIPT_FILE2))
                    .setDataSource(datas)
                    .eval();
            long endTime=System.currentTimeMillis();
            System.out.println("程序运行时间： "+(endTime-startTime)+"ms");

            showValue(varList);
        } catch (FelScriptException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(Log.getLogs());
    }

    private static void showValue(List<ScriptVar> varList) {
        if(varList.get(0).getValue() instanceof List) {
            showListValue(varList);
        }else {
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
        int dataListSize = ((List)varList.get(0).getValue()).size();
        String[] header = new String[varList.size()+1];
        header[0] = "序号";
        String[][] dataList = new String[dataListSize][varList.size()+1];
        for (int i = 0; i < varList.size(); i++) {
            header[i+1] = varList.get(i).getName();
        }


        for(int i= 0; i < header.length; i++) {
            for(int j = 0; j < dataListSize; j++) {
                if(i == 0) {
                    dataList[j][i] = j+"";
                }else {
                    List valueList = (List)varList.get(i-1).getValue();
                    Object value = valueList.get(j);
                    dataList[j][i] = value==null ? "" : value.toString();
                }
            }
        }

        System.out.println(FlipTable.of(header, dataList));
    }
}
