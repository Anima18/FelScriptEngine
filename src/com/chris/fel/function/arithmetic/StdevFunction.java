package com.chris.fel.function.arithmetic;

import com.chris.fel.script.Field;
import com.greenpineyu.fel.context.FelContext;

import java.util.List;
import java.util.stream.Collectors;

public class StdevFunction extends ArithFunction {
    public StdevFunction(FelContext context) {
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
        List<Double> valueList = subDataList.stream().map(e -> Double.parseDouble(e.getValue().toString())).collect(Collectors.toList());
        return standardDiviation(valueList);
    }

    @Override
    public String getName() {
        return "STDEV";
    }

    //标准差σ=sqrt(s^2)
    private double standardDiviation(List<Double> x) {
        int m=x.size();
        float sum=0;
        for(int i=0;i<m;i++){//求和
            sum+=x.get(i);
        }
        float dAve=sum/m;//求平均值
        float dVar=0;
        for(int i=0;i<m;i++){//求方差
            dVar+=(x.get(i)-dAve)*(x.get(i)-dAve);
        }
        return Math.sqrt(dVar/m);
    }
}
