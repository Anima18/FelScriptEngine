package com.chris.fel;

import com.chris.fel.function.FunctionRepository;
import com.chris.fel.script.*;
import com.chris.test.FelTest;
import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.exception.EvalException;

import java.io.File;
import java.util.List;
import java.util.Map;

public class FelScriptEngine {
    private FelEngine engine;
    private ScriptNode scriptNode;

    private FelScriptEngine(Builder builder) {
        Log.clear();
        Log.i("========开始解析脚本========");
        ScriptParser parser = new ScriptParser(builder.script);
        scriptNode = parser.parse();
        Log.i("========解析脚本完成========");

        engine = new FelEngineImpl();
        FunctionRepository repository = new FunctionRepository(engine);
        repository.initFunction();
        Log.i("【√】初始化函数成功");
        repository.initData(builder.dataSource);
        Log.i("【√】加载数据成功");
    }

    public List<ScriptVar> eval() {
        Log.i("========开始执行脚本========");
        List<ScriptParam> params = scriptNode.getParams();
        List<ScriptVar> vars = scriptNode.getVars();
        List<ScriptExec> execs = scriptNode.getExecs();

        FelContext context = engine.getContext();
        for(ScriptParam param : params) {
            context.set(param.getName(), FieldType.getFieldObject(param));
        }
        Log.i("【√】加载脚本Params成功");
        for(ScriptVar var : vars) {
            context.set(var.getName(), var.getValue());
        }
        Log.i("【√】加载脚本Vars成功");
        for(ScriptExec exec : execs) {
            String expression = exec.getExpression();
            try {
                Object value = engine.eval(expression);
                context.set(exec.getVar().getName(), value);
                exec.getVar().setValue(value);
                Log.i(String.format("【√】执行脚本语句 %s 成功", expression));
            } catch (FelScriptException e) {
                Log.i(String.format("【×】执行脚本语句 %s 失败: %s", expression, e.getMessage()));
                throw e;
            } catch (EvalException e) {
                Log.i(String.format("【×】执行脚本语句 %s 失败: %s", expression, e.getMessage()));
                throw e;
            }

        }
        Log.i("========执行脚本完成========");
        return vars;
    }

    public static class Builder {
        private File script;
        private Map<String, List<Float>> dataSource;

        public Builder setScript(File script) {
            this.script = script;
            return this;
        }

        public Builder setDataSource(Map<String, List<Float>> dataSource) {
            this.dataSource = dataSource;
            return this;
        }

        public FelScriptEngine build() {
            return new FelScriptEngine(this);
        }
    }
}
