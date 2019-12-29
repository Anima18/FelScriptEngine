package com.chris.fel.function.arithmetic;

import com.greenpineyu.fel.context.FelContext;

import java.util.List;

public class AverageFunction extends ArithFunction {
    public AverageFunction(FelContext context) {
        super(context);
    }

    @Override
    public Object call(Object[] objects) {
        validateParam(objects);
        String refCode = String.valueOf(objects[0]);
        int count = Integer.parseInt(objects[1].toString());
        List<Float> dataList = getDataSet().get(refCode);
        int dataSize = dataList.size();

        List<Float> subDataList = dataList.subList(dataSize - count, dataSize);
        float sum = subDataList.stream().reduce((item1, item2) -> item1 + item2).get();
        return sum/count;
    }

    @Override
    public String getName() {
        return "AVG";
    }
}
