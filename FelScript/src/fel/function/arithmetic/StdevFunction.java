package fel.function.arithmetic;

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
    private double standardDiviation(List<Double> x) {
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
    }
}
