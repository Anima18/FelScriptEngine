package com.chris.fel.function.logical;

import com.chris.fel.util.Constant;
import com.greenpineyu.fel.context.FelContext;

public class OrFunction extends LogicalFunction {
    public OrFunction(FelContext context) {
        super(context);
    }

    @Override
    public Object call(Object[] objects) {
        validateParam(objects);
        String result = Constant.FALSE;
        for(Object o : objects) {
            if(Boolean.valueOf(o.toString())) {
                result = Constant.TRUE;
                break;
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "OR";
    }
}
