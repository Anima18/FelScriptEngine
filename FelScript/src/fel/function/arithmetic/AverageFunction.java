package fel.function.arithmetic;

import fel.script.Field;
import com.greenpineyu.fel.context.FelContext;
import fel.util.TextUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * 平均值函数，AVG(A, n, l), 求列表A第n项往前l个元素的平均值
 */
public class AverageFunction extends ArithFunction {
    public AverageFunction(FelContext context) {
        super(context);
    }

    @Override
    public Object call(Object[] objects) {
        validateParam(objects);
        String refCode = String.valueOf(objects[0]);
        List dataList = getDataSetItemValue(refCode);
        int fromIndex = 0, toIndex = 0;
        if(objects.length == 2) {
            toIndex = dataList.size();
            fromIndex = toIndex - Integer.parseInt(objects[1].toString());
        }else if(objects.length == 3) {
            toIndex = Integer.parseInt(objects[1].toString());
            fromIndex = toIndex - Integer.parseInt(objects[2].toString());
        }

        List subDataList = dataList.subList(fromIndex, toIndex+1);
        return subDataList.stream().filter(e -> {
            if (TextUtil.isEmpty(e) || !TextUtil.isDouble(e.toString())) {
                return false;
            }
            return true;
        }).mapToDouble(e -> new BigDecimal(e.toString()).doubleValue()).average().getAsDouble();
    }

    @Override
    public String getName() {
        return "AVG";
    }
}
