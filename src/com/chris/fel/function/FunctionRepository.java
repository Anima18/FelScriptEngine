package com.chris.fel.function;

import com.chris.fel.function.arithmetic.AverageFunction;
import com.chris.fel.function.arithmetic.ReflFuction;
import com.chris.fel.function.arithmetic.StdevFunction;
import com.chris.fel.function.arithmetic.SumFunction;
import com.chris.fel.function.conditional.IfFunction;
import com.chris.fel.function.logical.AndFunction;
import com.chris.fel.function.logical.OrFunction;
import com.chris.fel.util.Constant;
import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.context.FelContext;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FunctionRepository {
    private FelEngine engine;

    public FunctionRepository(FelEngine engine) {
        this.engine = engine;
    }

    public void initFunction() {
        FelContext context = engine.getContext();
        engine.addFun(new SumFunction(context));
        engine.addFun(new AverageFunction(context));
        engine.addFun(new IfFunction(context));
        engine.addFun(new AndFunction(context));
        engine.addFun(new OrFunction(context));
        engine.addFun(new ReflFuction(context));
        engine.addFun(new StdevFunction(context));
    }

    public void initData(Map<String, List<Float>> dataSet) {
        FelContext ctx = engine.getContext();
        //把数据源加载到context
        ctx.set(Constant.DATA_SET, dataSet);
        //把变量True、False加载到context
        ctx.set(Constant.TRUE, Constant.TRUE);
        ctx.set(Constant.FALSE, Constant.FALSE);
        //把数据源列表索引加载到context
        Iterator<String> iterator =dataSet.keySet().iterator();
        while (iterator.hasNext()) {
            String refCode = iterator.next();
            ctx.set(refCode, refCode);
        }
    }

}
