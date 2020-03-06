package fel.function.collection;

import com.greenpineyu.fel.context.FelContext;

import java.util.Collections;

public class MinFunction extends CollectionFunction {

    public MinFunction(FelContext context) {
        super(context);
    }

    @Override
    public Object call(Object[] objects) {
        validateParam(objects);
        return Collections.min(dataList);
    }

    @Override
    public String getName() {
        return "MIN";
    }
}
