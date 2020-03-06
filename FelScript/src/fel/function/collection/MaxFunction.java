package fel.function.collection;

import com.greenpineyu.fel.context.FelContext;

import java.util.Collections;

public class MaxFunction extends CollectionFunction {

    public MaxFunction(FelContext context) {
        super(context);
    }

    @Override
    public Object call(Object[] objects) {
        validateParam(objects);
        return Collections.max(dataList);
    }

    @Override
    public String getName() {
        return "MAX";
    }
}
