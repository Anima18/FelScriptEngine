package fel.function.collection;

import com.greenpineyu.fel.context.FelContext;
import fel.function.arithmetic.ArithFunction;
import fel.script.Field;
import fel.util.TextUtil;

import java.util.List;

public class MinColFunction extends ArithFunction {

    public MinColFunction(FelContext context) {
        super(context);
    }

    @Override
    public Object call(Object[] objects) {
        validateParam(objects);
        String refCode = String.valueOf(objects[0]);
        int begin = Integer.parseInt(objects[1].toString());
        int count = Integer.parseInt(objects[2].toString());
        List dataList = getDataSetItemValue(refCode);
        if(begin == 503) {
            System.out.println("ddddddddd");
        }
        List subDataList = dataList.subList(begin - count, begin+1);
        return subDataList.stream().filter(e -> {
            if (TextUtil.isEmpty(e) || !TextUtil.isDouble(e.toString())) {
                return false;
            }
            return true;
        }).mapToDouble(e -> Double.parseDouble(e.toString())).min().getAsDouble();
    }

    @Override
    public String getName() {
        return "MINCOL";
    }
}
