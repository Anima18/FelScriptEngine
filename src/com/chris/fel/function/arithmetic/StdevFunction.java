package com.chris.fel.function.arithmetic;

import com.greenpineyu.fel.context.FelContext;

import java.util.List;

public class StdevFunction extends ArithFunction {
    public StdevFunction(FelContext context) {
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
        return standardDiviation(subDataList);
    }

    @Override
    public String getName() {
        return "STDEV";
    }

    //标准差σ=sqrt(s^2)
    private double standardDiviation(List<Float> x) {
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
