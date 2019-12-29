package com.chris.fel.function.logical;

import com.chris.fel.util.Constant;
import com.greenpineyu.fel.context.FelContext;

public class AndFunction extends LogicalFunction {
    public AndFunction(FelContext context) {
        super(context);
    }

    @Override
    public Object call(Object[] objects) {
        validateParam(objects);
        String result = Constant.TRUE;
        for(Object o : objects) {
            if(!Boolean.valueOf(o.toString())) {
                result = Constant.FALSE;
                break;
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "AND";
    }
}
