package com.chris.fel.function.arithmetic;

import com.chris.fel.script.Field;
import com.greenpineyu.fel.context.FelContext;

import java.util.List;

public class SumFunction extends ArithFunction {
    public SumFunction(FelContext context) {
        super(context);
    }

    @Override
    public Object call(Object[] objects) {
        validateParam(objects);
        String refCode = String.valueOf(objects[0]);
        int count = Integer.parseInt(objects[1].toString());
        List<Field> dataList = getDataSet().get(refCode);
        int dataSize = dataList.size();

        List<Field> subDataList = dataList.subList(dataSize - count, dataSize);
        return subDataList.stream().mapToDouble(e -> Double.parseDouble(e.getValue().toString())).sum();
    }

    @Override
    public String getName() {
        return "SUM";
    }
}
