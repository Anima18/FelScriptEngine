package com.chris.fel.function.arithmetic;

import com.chris.fel.FelScriptException;
import com.chris.fel.function.BaseFunction;
import com.chris.fel.script.Field;
import com.chris.fel.util.TextUtil;
import com.greenpineyu.fel.context.FelContext;

import java.util.List;
import java.util.Map;

public class ReflFuction extends BaseFunction {
    public ReflFuction(FelContext context) {
        super(context);
    }

    @Override
    protected void validateParam(Object[] objects) {
        if (objects != null && (objects.length == 1 || objects.length == 2)) {
            Map<String, List<Field>> dataSet = getDataSet();
            if(objects.length == 1 && !dataSet.containsKey(objects[0])) {
                throw new FelScriptException(String.format("%s运算出错，参数%s不存在！", getName(), objects[0]));
            }else if(objects.length == 2) {
                Object refCode = objects[0];
                Object count = objects[1];
                if (refCode == null || count == null) {
                    throw new FelScriptException(String.format("%s运算出错，参数不正确！", getName()));
                } else if (!dataSet.containsKey(refCode)) {
                    throw new FelScriptException(String.format("%s运算出错，参数%s不存在！", getName(), refCode));
                } else if (!TextUtil.isNumeric(count.toString())) {
                    throw new FelScriptException(String.format("%s运算出错，参数%s不是数值！", getName(), count));
                }else if(dataSet.get(refCode).size() <= Integer.parseInt(count.toString())) {
                    throw new FelScriptException(String.format("%s运算出错，%s超出了数组长度！", getName(), count.toString()));
                }
            }
        } else {
            throw new FelScriptException(String.format("%s运算出错，参数不正确！", getName()));
        }
    }

    @Override
    public Object call(Object[] objects) {
        validateParam(objects);
        List<Field> valueList = getDataSet().get(objects[0]);
        int lastIndex = 0;
        if(objects.length == 2) {
            lastIndex = Integer.parseInt(objects[1].toString());
        }
        return valueList.get(valueList.size()-lastIndex-1).getValue();
    }

    @Override
    public String getName() {
        return "REFL";
    }
}
