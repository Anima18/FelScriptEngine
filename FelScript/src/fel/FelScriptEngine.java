package fel;

import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.exception.EvalException;
import fel.function.FunctionRepository;
import fel.script.*;
import fel.util.Constant;

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
        loadParams(scriptNode.getParams());
        loadVars(scriptNode.getVars());
        runExecs(scriptNode.getExecs());
        return scriptNode.getVars();
    }

    private void loadParams(List<ScriptParam> params) {
        FelContext context = engine.getContext();
        for(ScriptParam param : params) {
            String paramName = param.getName();
            if(context.get(paramName) != null) {
                String message = String.format(Constant.paramRepeatError, paramName);
                throw FelScriptException.withLog(message, param.getLineNum());
            }else {
                context.set(paramName, FieldType.getFieldObject(param));
            }
        }
        Log.i("【√】加载脚本Params成功");
    }

    private void loadVars(List<ScriptVar> Vars) {
        FelContext context = engine.getContext();
        for(ScriptVar var : Vars) {
            String varName = var.getName();
            if(context.get(varName) != null) {
                String message = String.format(Constant.varRepeatError, varName);
                //Log.i(message);
                throw FelScriptException.withLog(message, var.getLineNum());
            }else {
                context.set(varName, var.getValue());
            }
        }
        Log.i("【√】加载脚本Vars成功");
    }

    private void runExecs(List<ScriptExec> execs) {
        FelContext context = engine.getContext();
        for(ScriptExec exec : execs) {
            String expression = exec.getExpression();
            try {
                Object value = engine.eval(expression);
                context.set(exec.getVar().getName(), value);
                exec.getVar().setValue(value);
                Log.i(String.format("【√】执行脚本语句 %s 成功", expression));
            } catch (FelScriptException e) {
                throw FelScriptException.withLog(String.format(Constant.execRunError, exec.getLineNum(), expression, e.getMessage()));
            } catch (EvalException e) {
                throw FelScriptException.withLog(String.format(Constant.execRunError, exec.getLineNum(), expression, e.getMessage()));
            }
        }
        Log.i("========执行脚本完成========");
    }

    public static class Builder {
        private File script;
        private Map<String, List<Field>> dataSource;

        public Builder setScript(File script) {
            this.script = script;
            return this;
        }

        public Builder setDataSource(Map<String, List<Field>> dataSource) {
            this.dataSource = dataSource;
            return this;
        }

        public FelScriptEngine build() {
            return new FelScriptEngine(this);
        }
    }
}
