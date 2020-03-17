package fel.function.collection;

import com.greenpineyu.fel.context.FelContext;
import fel.FelScriptException;
import fel.function.BaseFunction;
import fel.function.arithmetic.ArithFunction;
import fel.script.Field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaxColFunction extends ArithFunction {

    public MaxColFunction(FelContext context) {
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
        return subDataList.stream().mapToDouble(e -> Double.parseDouble(e.toString())).max().getAsDouble();
    }

    @Override
    public String getName() {
        return "MAXCOL";
    }
}
