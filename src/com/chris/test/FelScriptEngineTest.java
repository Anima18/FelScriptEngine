package com.chris.test;

import com.chris.fel.FelScriptEngine;
import com.chris.fel.FelScriptException;
import com.chris.fel.ScriptParser;

import java.io.File;

public class FelScriptEngineTest {
    public static void main(String[] args) {
        /*FelScriptEngine engine = new FelScriptEngine.Builder()
                .setScript(new File(""))
                .setDataSource(null)
                .build();*/
        ScriptParser parser = new ScriptParser(new File("E:/code/Idea_workspace/FelScriptEngine/scriptTest.txt"));
        try {
            parser.parse();
        } catch (FelScriptException e) {
            e.printStackTrace();
        }
    }
}
