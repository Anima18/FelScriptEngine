package com.chris.fel;

import com.chris.fel.script.*;
import com.chris.fel.util.FileUtil;
import com.chris.fel.util.TextUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScriptParser {
    private File script;

    public ScriptParser(File script) {
        this.script = script;
    }

    public ScriptNode parse() throws FelScriptException {
        if(script == null || !script.exists()) {
            throw new FelScriptException("模型脚本不存在!");
        }

        String filePath = script.getPath();
        String fileSuffix = FileUtil.getFileSuffix(filePath);
        if(!"txt".equalsIgnoreCase(fileSuffix)) {
            throw new FelScriptException("模型脚本文件类型不正确,请确保是txt文件!");
        }

        String fileData = FileUtil.readUTF8Text(filePath);
        String paramBlock, varBlock, execBlock;
        try {
            String[] varsSplitArray = fileData.split("Vars");
            paramBlock = varsSplitArray[0].split("Params")[1];
            String[] execSplitArray = varsSplitArray[1].split("Exec");
            varBlock = execSplitArray[0];
            execBlock = execSplitArray[1];
        }catch (Exception e) {
            throw new FelScriptException("模型脚本格式不正确,请检查Params,Vars,Exec代码快!");
        }

        List<ScriptParam> params = parseParam(paramBlock);
        List<ScriptVar> vars = parseVar(varBlock);
        List<ScriptExec> execs = parseExec(execBlock);



        return new ScriptNode(params, vars, execs);
    }

    private List<ScriptParam> parseParam(String paramBlock) throws FelScriptException {
        List<ScriptParam> params = new ArrayList<>();
        String[] paramStrArray = paramBlock.split("\n");
        for(String paramStr : paramStrArray) {
            if(!TextUtil.isEmpty(paramStr.trim())) {
                paramStr = paramStr.replaceFirst("\t", "|").replaceAll("\\s*", "");
                String[] paramSplitArray = paramStr.split("\\|");
                String fieldType = paramSplitArray[0];
                String[] nameSplitArray = paramSplitArray[1].split("\\(");
                String name = nameSplitArray[0];
                String value = nameSplitArray[1].split("\\)")[0];
                params.add(new ScriptParam(name, value, FieldType.to(fieldType)));
            }
        }
        return params;
    }

    private List<ScriptVar> parseVar(String varBlock) {
        return null;
    }

    private List<ScriptExec> parseExec(String execBlock) {
        return null;
    }
}
