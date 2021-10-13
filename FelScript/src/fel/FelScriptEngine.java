package fel;

import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.exception.EvalException;
import fel.function.FunctionRepository;
import fel.script.*;
import fel.util.Constant;
import fel.util.FileUtil;
import fel.util.ResultSetRepository;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static fel.util.Constant.DATA_SET;
import static fel.util.Constant.DATA_SIZE;

public class FelScriptEngine {
    private FelEngine engine;
    private ScriptNode scriptNode;
    private ResultSetRepository resultSet;


    private FelScriptEngine(Builder builder) {
        resultSet = new ResultSetRepository(builder.script, builder.dataSource);

        Log.clear();
        Log.i("========开始解析脚本========");
        ScriptParser parser = new ScriptParser(builder.dataSource, builder.script);
        scriptNode = parser.parse();
        Log.i("========解析脚本完成========");

        engine = new FelEngineImpl();
        FunctionRepository repository = new FunctionRepository(engine);
        repository.initFunction();
        Log.i("【√】初始化函数成功");
        repository.initData(builder.dataSource);
        Log.i("【√】加载数据成功");

        //加载历史数据
        resultSet.initResultSet();
    }

    public List<ScriptVar> eval() {
        Log.i("========开始执行脚本========");
        long startTime1 = System.currentTimeMillis();
        loadParams(scriptNode.getParams());
        long endTime1 = System.currentTimeMillis();
        System.out.println("loadParams运行时间： "+(endTime1-startTime1)+"ms");

        long startTime2 = System.currentTimeMillis();
        loadVars(scriptNode.getVars());
        long endTime2 = System.currentTimeMillis();
        System.out.println("loadVars运行时间： "+(endTime2-startTime2)+"ms");

        long startTime3 = System.currentTimeMillis();
        runExecs(scriptNode.getExecs());
        long endTime3 = System.currentTimeMillis();
        System.out.println("runExecs运行时间： "+(endTime3-startTime3)+"ms");

        List<ScriptVar> scriptVars = scriptNode.getVars();

        resultSet.setResultSet(scriptVars);
        return scriptVars;
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
        /*if(context.get(VAR_SET) == null) {
            context.set(VAR_SET, new HashMap<String, Field>());
        }*/
        Map<String, Field> varSet = (Map<String, Field>)context.get(DATA_SET);
        for(ScriptVar var : Vars) {
            String varName = var.getName();
            if(context.get(varName) != null) {
                String message = String.format(Constant.varRepeatError, varName);
                //Log.i(message);
                throw FelScriptException.withLog(message, var.getLineNum());
            }else {
                context.set(varName, varName);
                varSet.put(varName, var);

                FieldType varType = var.getFieldType();
                if(varType.equals(FieldType.List_Bool) || varType.equals(FieldType.List_Numeric) || varType.equals(FieldType.List_String)) {
                    for(int i = 0; i < (int)context.get(DATA_SIZE); i++) {
                        ((List)var.getValue()).add(null);
                    }
                }
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
                if(exec.getType() == ScriptExec.ExecType.assign) {
                    //context.set(exec.getVar().getName(), value);
                    exec.getVar().setValue(value);
                }
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
        private Map<String, Field> dataSource;

        public Builder setScript(File script) {
            this.script = script;
            return this;
        }

        public Builder setDataSource(Map<String, Field> dataSource) {
            this.dataSource = dataSource;
            return this;
        }

        public FelScriptEngine build() {
            return new FelScriptEngine(this);
        }

        public List<ScriptVar> eval() {
            FelScriptEngine engine = build();
            return engine.eval();
        }
    }
}
