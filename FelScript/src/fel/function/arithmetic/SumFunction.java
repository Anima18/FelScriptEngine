package fel.function.arithmetic;

import fel.script.Field;
import com.greenpineyu.fel.context.FelContext;
import fel.util.TextUtil;

import java.util.List;

public class SumFunction extends ArithFunction {
    public SumFunction(FelContext context) {
        super(context);
    }

    @Override
    public Object call(Object[] objects) {
        validateParam(objects);
        String refCode = String.valueOf(objects[0]);

        List dataList = getDataSetItemValue(refCode);
        int fromIndex = 0, toIndex = 0;
        if(objects.length == 2) {
            fromIndex = 0;
            toIndex = Integer.parseInt(objects[1].toString());
        }else if(objects.length == 3) {
            toIndex = Integer.parseInt(objects[1].toString());
            fromIndex = toIndex - Integer.parseInt(objects[2].toString());
        }

        List subDataList = dataList.subList(fromIndex, toIndex+1);
        return subDataList.stream()
                .filter(e -> {
                    if (TextUtil.isEmpty(e) || !TextUtil.isDouble(e.toString())) {
                        return false;
                    }
                    return true;
                })
                .mapToDouble(e -> Double.parseDouble(e.toString())).sum();
    }

    @Override
    public String getName() {
        return "SUM";
    }
}
