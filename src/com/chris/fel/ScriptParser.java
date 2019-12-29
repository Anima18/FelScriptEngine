package com.chris.fel;

import com.chris.fel.script.*;
import com.chris.fel.util.FileUtil;
import com.chris.fel.util.TextUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScriptParser {
    private File script;

    public ScriptParser(File script) {
        this.script = script;
    }

    public ScriptNode parse() throws FelScriptException {
        if (script == null || !script.exists()) {
            throw new FelScriptException("模型脚本解析失败，文件不存在!");
        }

        String filePath = script.getPath();
        String fileSuffix = FileUtil.getFileSuffix(filePath);
        if (!"txt".equalsIgnoreCase(fileSuffix)) {
            throw new FelScriptException("模型脚本解析失败，文件类型不正确,请确保是txt文件!");
        }

        String fileData = FileUtil.readUTF8Text(filePath);
        String paramBlock, varBlock, execBlock;
        try {
            String[] varsSplitArray = fileData.split("Vars");
            paramBlock = varsSplitArray[0].split("Params")[1];
            String[] execSplitArray = varsSplitArray[1].split("Exec");
            varBlock = execSplitArray[0];
            execBlock = execSplitArray[1];
        } catch (Exception e) {
            throw new FelScriptException("模型脚本解析失败,请检查Params,Vars,Exec代码块格式!");
        }

        List<ScriptParam> params = parseParam(paramBlock);
        List<ScriptVar> vars = parseVar(varBlock);
        List<ScriptExec> execs = parseExec(vars, execBlock);


        return new ScriptNode(params, vars, execs);
    }

    private List<ScriptParam> parseParam(String paramBlock) throws FelScriptException {
        List<ScriptParam> params = new ArrayList<>();
        try {
            String[] paramStrArray = paramBlock.split("\n");
            for (String paramStr : paramStrArray) {
                if (!TextUtil.isEmpty(paramStr.trim())) {
                    paramStr = paramStr.replaceFirst("\t", "|").replaceAll(";", "").replaceAll("\\s*", "");
                    String[] paramSplitArray = paramStr.split("\\|");
                    String fieldType = paramSplitArray[0];
                    String[] nameSplitArray = paramSplitArray[1].split("\\(");
                    String name = nameSplitArray[0];
                    String value = nameSplitArray[1].split("\\)")[0];
                    params.add(new ScriptParam(name, value, FieldType.to(fieldType)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(String.format("【×】解析模型脚本Params失败: %s", e.getMessage()));
            throw new FelScriptException("模型脚本解析失败，Params代码块格式不正确!");
        }
        Log.i("【√】解析模型脚本Params成功");
        return params;
    }

    private List<ScriptVar> parseVar(String varBlock) throws FelScriptException {
        List<ScriptVar> vars = new ArrayList<>();
        try {
            String[] varStrArray = varBlock.split("\n");
            for (String varStr : varStrArray) {
                if (!TextUtil.isEmpty(varStr.trim())) {
                    varStr = varStr.replaceFirst("\t", "|").replaceAll(";", "").replaceAll("\\s*", "");
                    String[] varSplitArray = varStr.split("\\|");
                    String fieldType = varSplitArray[0];
                    String name = varSplitArray[1];
                    vars.add(new ScriptVar(name, FieldType.to(fieldType)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(String.format("【×】解析模型脚本Vars失败: %s", e.getMessage()));
            throw new FelScriptException("模型脚本解析失败，Vars代码块格式不正确!");
        }
        Log.i("【√】解析模型脚本Vars成功");
        return vars;
    }

    private List<ScriptExec> parseExec(List<ScriptVar> vars, String execBlock) throws FelScriptException {
        Map<String, ScriptVar> varMap = new HashMap<>();
        for (ScriptVar var : vars) {
            varMap.put(var.getName(), var);
        }

        List<ScriptExec> execs = new ArrayList<>();
        try {
            String[] execStrArray = execBlock.split("\n");
            for (String execStr : execStrArray) {
                if (!TextUtil.isEmpty(execStr.trim())) {
                    execStr = execStr.replaceAll(";", "").replaceAll("\\s*", "");
                    int index = execStr.indexOf("=");
                    String name = execStr.substring(0, index);
                    String expression = execStr.substring(index + 1);

                    ScriptVar var = varMap.get(name);
                    execs.add(new ScriptExec(var, expression));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(String.format("【×】解析模型脚本Execs失败: %s", e.getMessage()));
            throw new FelScriptException("模型脚本解析失败，Execs代码块格式不正确!");
        }
        Log.i("【√】解析模型脚本Execs成功");
        return execs;
    }
}
