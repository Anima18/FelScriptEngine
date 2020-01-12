package fel.function;

import fel.function.arithmetic.AverageFunction;
import fel.function.arithmetic.ReflFuction;
import fel.function.arithmetic.StdevFunction;
import fel.function.arithmetic.SumFunction;
import fel.function.conditional.IfFunction;
import fel.function.logical.AndFunction;
import fel.function.logical.OrFunction;
import fel.script.Field;
import fel.util.Constant;
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

    public void initData(Map<String, List<Field>> dataSet) {
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
