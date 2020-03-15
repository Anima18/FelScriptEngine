package fel.function.arithmetic;

import fel.script.Field;
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
        List<Field> dataList = getDataSet().get(refCode);
        int fromIndex = 0, toIndex = 0;
        if(objects.length == 2) {
            toIndex = dataList.size();
            fromIndex = toIndex - Integer.parseInt(objects[1].toString());
        }else if(objects.length == 3) {
            toIndex = Integer.parseInt(objects[1].toString());
            fromIndex = toIndex - Integer.parseInt(objects[2].toString());
        }

        List<Field> subDataList = dataList.subList(fromIndex, toIndex);
        return subDataList.stream().mapToDouble(e -> Double.parseDouble(e.getValue().toString())).average().getAsDouble();
    }

    @Override
    public String getName() {
        return "AVG";
    }
}
