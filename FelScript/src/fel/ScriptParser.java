package fel;

import fel.script.*;
import fel.util.FileUtil;
import fel.util.ReflLexer;
import fel.util.TextUtil;

import java.io.File;
import java.util.*;

public class ScriptParser {
    private File script;
    private List<String> codeLineList;
    private Map<String, Field> dataSource;

    public ScriptParser(Map<String, Field> dataSource, File script ) {
        this.dataSource = dataSource;
        this.script = script;
    }

    public ScriptNode parse() throws FelScriptException {
        if (script == null || !script.exists()) {
            throw FelScriptException.withLog("解析模型脚本失败, 文件不存在!");
        }

        String filePath = script.getPath();
        String fileSuffix = FileUtil.getFileSuffix(filePath);
        if (!"txt".equalsIgnoreCase(fileSuffix)) {
            throw FelScriptException.withLog("模型脚本解析失败，文件类型不正确,请确保是txt文件!");
        }

        String fileData = FileUtil.readUTF8Text(filePath);
        codeLineList = Arrays.asList(fileData.split("\n"));
        String paramBlock, varBlock, execBlock;
        try {
            String[] varsSplitArray = fileData.split("Vars");
            paramBlock = varsSplitArray[0].split("Params")[1];
            String[] execSplitArray = varsSplitArray[1].split("Exec");
            varBlock = execSplitArray[0];
            execBlock = execSplitArray[1];
        } catch (Exception e) {
            throw FelScriptException.withLog("解析模型脚本失败, Params,Vars,Exec代码块格式不正确!");
        }

        List<ScriptParam> params = parseParam(paramBlock);
        List<ScriptVar> vars = parseVar(varBlock);
        List<ScriptExec> execs = parseExec(vars, execBlock);

        return new ScriptNode(params, vars, execs);
    }

    private List<ScriptParam> parseParam(String paramBlock) throws FelScriptException {
        List<ScriptParam> params = new ArrayList<>();
        Integer lineNum = null;
        try {
            String[] paramStrArray = paramBlock.split("\n");
            for (String paramStr : paramStrArray) {
                if (!TextUtil.isEmpty(paramStr.trim())) {
                    lineNum = codeLineList.indexOf(paramStr);
                    params.add(ScriptParam.parse(lineNum, paramStr));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = String.format("解析模型脚本Params失败: %s", e.getMessage());
            showError(lineNum, errorMessage);

        }
        Log.i("【√】解析模型脚本Params成功");
        return params;
    }

    private List<ScriptVar> parseVar(String varBlock) throws FelScriptException {
        List<ScriptVar> vars = new ArrayList<>();
        Integer lineNum = null;
        try {
            String[] varStrArray = varBlock.split("\n");
            for (String varStr : varStrArray) {
                if (!TextUtil.isEmpty(varStr.trim())) {
                    lineNum = codeLineList.indexOf(varStr);
                    vars.add(ScriptVar.parse(lineNum, varStr));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = String.format("解析模型脚本Vars失败: %s", e.getMessage());
            showError(lineNum, errorMessage);
        }
        Log.i("【√】解析模型脚本Vars成功");
        return vars;
    }

    private List<ScriptExec> parseExec(List<ScriptVar> vars, String execBlock) throws FelScriptException {
        Map<String, ScriptVar> varMap = new HashMap<>();
        for (ScriptVar var : vars) {
            varMap.put(var.getName(), var);
        }
        ReflLexer lexer = new ReflLexer(dataSource, varMap);

        List<ScriptExec> execs = new ArrayList<>();
        Integer lineNum = null;
        try {
            String[] execStrArray = execBlock.split("\n");
            for(int i = 0; i < execStrArray.length; i++) {
                String execStr = execStrArray[i].trim();
                if(TextUtil.isEmpty(execStr)) {
                    continue;
                }else if(execStr.equals("#LOOP")) {
                    for(int j = i+1; j < execStrArray.length; j++) {
                        String loopCode = execStrArray[j].trim();
                        if(!TextUtil.isEmpty(loopCode) && loopCode.equals("#END")) {
                            List<String> loopBlock = Arrays.asList(execStrArray).subList(i+1, j);
                            lineNum = codeLineList.indexOf(execStr);
                            execs.add(ScriptExec.parseLoop(lineNum, lexer, loopBlock));
                            i = j;
                            break;
                        }
                    }
                }else {
                    lineNum = codeLineList.indexOf(execStr);
                    execs.add(ScriptExec.parse(lineNum, varMap, execStr));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = String.format("解析模型脚本Execs失败: %s", e.getMessage());
            showError(lineNum, errorMessage);
        }
        Log.i("【√】解析模型脚本Execs成功");
        return execs;
    }

    private void showError(Integer lineNum, String errorMessage) {
        if(lineNum == null) {
            throw FelScriptException.withLog(errorMessage);
        }else {
            throw FelScriptException.withLog(errorMessage, lineNum);
        }
    }
}
