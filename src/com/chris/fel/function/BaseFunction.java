package com.chris.fel.function;

import com.chris.fel.util.Constant;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.function.CommonFunction;

import java.util.List;
import java.util.Map;

public abstract class BaseFunction extends CommonFunction {
    protected FelContext context;

    public BaseFunction(FelContext context) {
        this.context = context;
    }

    public Map<String, List<Float>> getDataSet() {
        return (Map<String, List<Float>>) context.get(Constant.DATA_SET);
    }

    protected abstract void validateParam(Object[] objects);
}
