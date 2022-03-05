package fel;

import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.common.StringUtils;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.exception.EvalException;
import fel.function.FunctionRepository;
import fel.script.*;
import fel.util.Constant;
import fel.util.ResultSetRepository;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class FelScriptEngine {
    private FelEngine engine;
    private ScriptNode scriptNode;
    private ResultSetRepository resultSet;

    private Map<String, Field> dataSource;
    private List<String> timeColumn;
    private FunctionRepository repository;

    private String resultLastTime;

    /**
     * 根据历史最后结果在新参数的下标
     */
    int index = 0;

    private FelScriptEngine(Builder builder) {
        this.dataSource = builder.dataSource;
        this.timeColumn = (List<String>)dataSource.get("A").getValue();
        resultSet = new ResultSetRepository(builder.script);
        //加载历史数据
        resultSet.initResultSet();

        Log.clear();
        Log.i("========开始解析脚本========");
        ScriptParser parser = new ScriptParser(this.dataSource, builder.script);
        scriptNode = parser.parse();
        Log.i("========解析脚本完成========");

        engine = new FelEngineImpl();
        repository = new FunctionRepository(engine);
        repository.initFunction();
        Log.i("【√】初始化函数成功");

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

        resultSet.setResultSet((Map<String, Field>)engine.getContext().get(Constant.DATA_SET));

        /*if(index == 0) {
            scriptVars = scriptVars.stream().map(var -> {
                List values = (List)var.getValue();
                var.setValue(values.subList(values.size()-((List)dataSource.get("A").getValue()).size(), values.size()));
                return var;
            }).collect(Collectors.toList());
        }else {
            scriptVars = scriptVars.stream().map(var -> {
                List values = (List)var.getValue();
                if(values.size() >= 200) {
                    var.setValue(values.subList(200, values.size()));
                }
                return var;
            }).collect(Collectors.toList());
        }*/
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

    private Object getFieldValue(String value, FieldType fieldType) {
        if(fieldType.equals(FieldType.List_Bool)) {
            return value != null ? Boolean.valueOf(value) : false;
        }else if(fieldType.equals(FieldType.List_Numeric)) {
            return value != null ? Double.valueOf(value) : 0;
        }else if(fieldType.equals(FieldType.List_String)) {
            return value != null ? value : "";
        }else {
            return null;
        }
    }

    private void loadVars(List<ScriptVar> Vars) {
        FelContext context = engine.getContext();
        //上次运行结果
        Map<String, List<String>> resultListMap = resultSet.getResultListMap();

        if(resultListMap != null && resultListMap.size() > 0) {
            //获取上次最后执行记录时间
            List<String> resultTimeList = resultListMap.get("A");
            resultLastTime = resultTimeList.get(resultTimeList.size()-1);

            String newLastTime = timeColumn.get(timeColumn.size()-1);
            String newFirstTime = timeColumn.get(0);
            if(resultLastTime.compareTo(newLastTime) >= 0) {
                index = timeColumn.size();
            }else if(resultLastTime.compareTo(newFirstTime) <0){
                index = 0;
            }else if(timeColumn.contains(resultLastTime)) {
                index = timeColumn.lastIndexOf(resultLastTime) +1;
            }
        }
        /**
         * 加载历史参数和新增参数
         */
        Map<String, Field> dataSet = new LinkedHashMap<>();
        for (Field field : this.dataSource.values()) {
            String fieldName = field.getName();
            FieldType fieldType = field.getFieldType();
            List fieldValue;
            if (resultListMap != null) {
                fieldValue = resultListMap.get(fieldName).stream().map(value -> getFieldValue(value, fieldType)).collect(Collectors.toList());

                //获取新数据
                List data = (List) field.getValue();
                List newDataList = data.subList(index, data.size());
                fieldValue.addAll(newDataList);
            } else {
                fieldValue = (List) field.getValue();
            }
            dataSet.put(fieldName, new Field(fieldName, fieldValue, fieldType));
        }


        for(ScriptVar var : Vars) {
            String varName = var.getName();
            if(context.get(varName) != null) {
                String message = String.format(Constant.varRepeatError, varName);
                //Log.i(message);
                throw FelScriptException.withLog(message, var.getLineNum());
            }else {

                context.set(varName, varName);
                dataSet.put(varName, var);
                FieldType varType = var.getFieldType();

                List varValue = new ArrayList();
                if(resultListMap != null) {
                    varValue = resultListMap.get(varName).stream().map(value -> getFieldValue(value, varType)).collect(Collectors.toList());
                }
                for(int i = 0; i<timeColumn.size() - index; i++) {
                    varValue.add(getFieldValue(null, varType));
                }
                var.setValue(varValue);
            }
        }
        Log.i("【√】加载脚本Vars成功");

        repository.initData(dataSet);
        Log.i("【√】加载数据成功");
    }

    private void runExecs(List<ScriptExec> execs) {
        FelContext context = engine.getContext();
        for(ScriptExec exec : execs) {
            String expression = exec.getExpression();
            try {
                System.out.println(expression);
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
