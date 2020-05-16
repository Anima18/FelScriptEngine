package fel.function.arithmetic;

import com.greenpineyu.fel.context.FelContext;
import fel.util.TextUtil;

import java.math.BigDecimal;
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
/*
        int count = Integer.parseInt(objects[1].toString());

        int dataSize = dataList.size();

        List<Object> subDataList = dataList.subList(dataSize - count, dataSize);
*/

        List dataList = getDataSetItemValue(refCode);
        int fromIndex = 0, toIndex = 0;
        if(objects.length == 2) {
            fromIndex = 0;
            toIndex = Integer.parseInt(objects[1].toString());
        }else if(objects.length == 3) {
            toIndex = Integer.parseInt(objects[1].toString());
            fromIndex = toIndex - Integer.parseInt(objects[2].toString());
        }

        List<Object> subDataList = dataList.subList(fromIndex, toIndex+1);
        List<Double> valueList = subDataList.stream().map(e -> Double.parseDouble(e.toString())).collect(Collectors.toList());
        return standardDiviation(valueList);
    }

    @Override
    public String getName() {
        return "STDEV";
    }

    //标准差σ=sqrt(s^2)
    /*private double standardDiviation(List<Double> x) {
        int m=x.size();
        double sum=0;
        for(int i=0;i<m;i++){//求和
            sum+=x.get(i);
        }
        double dAve=sum/m;//求平均值
        double dVar=0;
        for(int i=0;i<m;i++){//求方差
            dVar+=(x.get(i)-dAve)*(x.get(i)-dAve);
        }
        return Math.sqrt(dVar/m);
        //2.152536075320357
    }*/

    private double avg(List<Double> subDataList) {
        return subDataList.stream().filter(e -> {
            if (TextUtil.isEmpty(e) || !TextUtil.isDouble(e.toString())) {
                return false;
            }
            return true;
        }).mapToDouble(e -> new BigDecimal(e.toString()).doubleValue()).average().getAsDouble();
    }

    private double standardDiviation(List<Double> vals){
        double rval = 0;
        double avg = avg(vals);
        for (int i = 0; i < vals.size(); i++) {
            rval += Math.pow((vals.get(i) - avg), 2);
        }
        rval /= vals.size()-1;
        rval = Math.sqrt(rval);
        return rval;
        //2.152536075320357
    }
    /**
     * 只遍历数组一次求总体标准差
     * @param sample doube数组
     * @return
     */
    /*private double standardDiviation(List<Double> sample) {
        if (1 > sample.size()) {
            return 0;
        }
        double dSum = 0.0; // 样本值之和
        double dAverage = 0.0; // 样本值的平均值
        // 遍历样本
        for (int i = 0; i < sample.size(); ++i) {
            dSum += sample.get(i);
        }
        dAverage = dSum / sample.size();

        // 遍历样本数字
        dSum = 0.0;
        for (int i = 0; i < sample.size(); ++i) {
            dSum += (sample.get(i) - dAverage) * (sample.get(i) - dAverage);
        }
        double dStdDev = Math.sqrt(dSum / sample.size());
        return dStdDev;
        //2.152536075320357
    }*/
}
