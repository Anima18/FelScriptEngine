package com.chris.fel.script;

public class ScriptExec {
    private ScriptVar var;
    private String expression;

    public ScriptExec(ScriptVar var, String expression) {
        this.var = var;
        this.expression = expression;
    }

    public ScriptVar getVar() {
        return var;
    }

    public String getExpression() {
        return expression;
    }
}
