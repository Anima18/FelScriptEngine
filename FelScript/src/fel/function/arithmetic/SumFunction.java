package fel.function.arithmetic;

import fel.script.Field;
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
        List dataList = getDataSetItemValue(refCode);
        int dataSize = dataList.size();

        List subDataList = dataList.subList(dataSize - count, dataSize);
        return subDataList.stream().mapToDouble(e -> Double.parseDouble(e.toString())).sum();
    }

    @Override
    public String getName() {
        return "SUM";
    }
}
