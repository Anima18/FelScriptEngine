package com.chris.test;

import fel.FelScriptEngine;
import fel.FelScriptException;
import fel.Log;
import fel.script.ScriptVar;
import com.jakewharton.fliptables.FlipTable;

import java.io.File;
import java.util.List;

public class FelScriptEngineTest {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        try {
            List<ScriptVar> varList = new FelScriptEngine.Builder()
                    .setScript(new File("E:/code/Idea_workspace/FelScriptEngine/scriptTest.txt"))
                    .setDataSource(FelTest.initData())
                    .eval();

            showValue(varList);
        } catch (FelScriptException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long endTime=System.currentTimeMillis();
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
        //System.out.println(Log.getLogs());
    }

    private static void showValue(List<ScriptVar> varList) {
        String[] header = new String[varList.size()];
        String[][] dataList = new String[1][varList.size()];
        for (int i = 0; i < varList.size(); i++) {
            header[i] = varList.get(i).getName();
            dataList[0][i] = varList.get(i).getValue().toString();
        }
        System.out.println(FlipTable.of(header, dataList));
    }
}
