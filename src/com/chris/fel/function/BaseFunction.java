package com.chris.fel.function;

import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.function.CommonFunction;

public abstract class BaseFunction extends CommonFunction {
    protected FelContext context;

    public BaseFunction(FelContext context) {
        this.context = context;
    }
}
