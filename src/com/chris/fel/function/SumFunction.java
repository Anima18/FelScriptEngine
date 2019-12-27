package com.chris.fel.function;

import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.function.CommonFunction;

import java.util.List;
import java.util.Map;

import static com.chris.fel.FelScriptEngine.DATASET;

public class SumFunction extends BaseFunction {
    public SumFunction(FelContext context) {
        super(context);
    }

    @Override
    public Object call(Object[] objects) {
        Object refCode, count = null;
        if(objects != null && objects.length == 2) {
            refCode = objects[0];
            count = objects[1];
        }
        Map<String, List<Integer>> dataSet = (Map<String, List<Integer>>) context.get(DATASET);
        return null;
    }

    @Override
    public String getName() {
        return "SUM";
    }
}
