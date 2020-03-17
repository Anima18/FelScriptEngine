package com.chris.test;

import com.jakewharton.fliptables.FlipTable;
import fel.FelScriptEngine;
import fel.FelScriptException;
import fel.script.Field;
import fel.script.ScriptVar;

import java.io.File;
import java.util.List;
import java.util.Map;

import static com.chris.test.FelTest.SCRIPT_FILE;

public class FelScriptEngineTest {
    public static void main(String[] args) {
        Map<String, Field> datas = FelTest.loadDataFromExcel();

        try {
            long startTime = System.currentTimeMillis();
            List<ScriptVar> varList = new FelScriptEngine.Builder()
                    .setScript(new File(SCRIPT_FILE))
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
        String[] header = new String[varList.size()];
        String[][] dataList = new String[((List)varList.get(0).getValue()).size()][varList.size()];
        for (int i = 0; i < varList.size(); i++) {
            header[i] = varList.get(i).getName();
            List valueList = (List)varList.get(i).getValue();
            for(int j = 0; j < valueList.size(); j++) {
                Object value = valueList.get(j);
                dataList[j][i] = value==null ? "" : value.toString();
            }

        }
        System.out.println(FlipTable.of(header, dataList));
    }
}
