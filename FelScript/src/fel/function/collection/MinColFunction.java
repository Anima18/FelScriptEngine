package fel.function.collection;

import com.greenpineyu.fel.context.FelContext;
import fel.function.arithmetic.ArithFunction;
import fel.script.Field;

import java.util.List;

public class MinColFunction extends ArithFunction {

    public MinColFunction(FelContext context) {
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
        return subDataList.stream().mapToDouble(e -> Double.parseDouble(e.getValue().toString())).min().getAsDouble();
    }

    @Override
    public String getName() {
        return "MINCOL";
    }
}
