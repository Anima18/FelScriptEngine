package fel.script;

import java.util.Map;

public class ScriptExec {
    public enum ExecType {
        assign, //赋值语句
        justRun //仅仅运行表达式
    }

    private int lineNum;
    private ScriptVar var;
    private String expression;
    private ExecType type;

    private ScriptExec(int lineNum, ScriptVar var, String expression, ExecType type) {
        this.lineNum = lineNum;
        this.var = var;
        this.expression = expression;
        this.type = type;
    }

    public static ScriptExec parse(Integer lineNum, Map<String, ScriptVar> varMap, String execStr) {
        execStr = execStr.replaceAll(";", "").replaceAll("\\s*", "");

        if(execStr.contains("=")) {
            int index = execStr.indexOf("=");
            String name = execStr.substring(0, index);
            if(execStr.contains(name)) {
                String expression = execStr.substring(index + 1);
                ScriptVar var = varMap.get(name);
                return new ScriptExec(lineNum, var, expression, ExecType.justRun);
            }else {
                return new ScriptExec(lineNum, null, execStr, ExecType.justRun);
            }

        }else {
            return new ScriptExec(lineNum, null, execStr, ExecType.justRun);
        }

    }

    public ScriptVar getVar() {
        return var;
    }

    public String getExpression() {
        return expression;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public ExecType getType() {
        return type;
    }
}
