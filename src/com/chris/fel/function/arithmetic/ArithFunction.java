package com.chris.fel.function.arithmetic;

import com.chris.fel.FelScriptException;
import com.chris.fel.function.BaseFunction;
import com.chris.fel.util.TextUtil;
import com.greenpineyu.fel.context.FelContext;

import java.util.List;
import java.util.Map;

public abstract class ArithFunction extends BaseFunction {
    public ArithFunction(FelContext context) {
        super(context);
    }

    @Override
    protected void validateParam(Object[] objects) {
        if (objects != null && objects.length == 2) {
            Object refCode = objects[0];
            Object count = objects[1];
            Map<String, List<Float>> dataSet = getDataSet();
            if (refCode == null || count == null) {
                throw new FelScriptException(String.format("%s运算出错，参数不正确！", getName()));
            } else if (!dataSet.containsKey(refCode)) {
                throw new FelScriptException(String.format("%s运算出错，参数%s不存在！", getName(), refCode));
            } else if (!TextUtil.isNumeric(count.toString())) {
                throw new FelScriptException(String.format("%s运算出错，参数%s不是数值！", getName(), count));
            } else if(dataSet.get(refCode).size() < Integer.parseInt(count.toString())) {
                throw new FelScriptException(String.format("%s运算出错，%s超出了数组长度！", getName(), count.toString()));
            }
        } else {
            throw new FelScriptException(String.format("%s运算出错，参数不正确！", getName()));
        }
    }
}
