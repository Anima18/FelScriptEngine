package fel.function.collection;

import com.greenpineyu.fel.context.FelContext;
import fel.function.arithmetic.ArithFunction;
import fel.util.TextUtil;

import java.util.List;
import java.util.OptionalDouble;

public class MaxColFunction extends ArithFunction {

    public MaxColFunction(FelContext context) {
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
        OptionalDouble optional = subDataList.stream().filter(e -> {
            if (TextUtil.isEmpty(e) || !TextUtil.isDouble(e.toString())) {
                return false;
            }
            return true;
        }).mapToDouble(e -> Double.parseDouble(e.toString())).max();
        if(optional != null && optional.isPresent()) {
            return optional.getAsDouble();
        }else {
            return 0;
        }

    }

    @Override
    public String getName() {
        return "MAXCOL";
    }
}
