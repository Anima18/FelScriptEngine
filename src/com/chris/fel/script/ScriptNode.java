package com.chris.fel.script;

import java.util.List;

public class ScriptNode {
    private List<ScriptParam> params;
    private List<ScriptVar> vars;
    private List<ScriptExec> execs;

    public ScriptNode(List<ScriptParam> params, List<ScriptVar> vars, List<ScriptExec> execs) {
        this.params = params;
        this.vars = vars;
        this.execs = execs;
    }

    public List<ScriptParam> getParams() {
        return params;
    }

    public List<ScriptVar> getVars() {
        return vars;
    }

    public List<ScriptExec> getExecs() {
        return execs;
    }
}
