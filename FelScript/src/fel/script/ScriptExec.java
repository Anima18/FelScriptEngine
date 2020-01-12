package fel.script;

public class ScriptExec {
    private int lineNum;
    private ScriptVar var;
    private String expression;

    public ScriptExec(int lineNum, ScriptVar var, String expression) {
        this.lineNum = lineNum;
        this.var = var;
        this.expression = expression;
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
}
