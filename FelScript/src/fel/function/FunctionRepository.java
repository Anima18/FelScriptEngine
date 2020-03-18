package fel.function;

import fel.function.arithmetic.*;
import fel.function.collection.MaxColFunction;
import fel.function.collection.MaxFunction;
import fel.function.collection.MinColFunction;
import fel.function.collection.MinFunction;
import fel.function.conditional.IfFunction;
import fel.function.conditional.LoopFunction;
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
        engine.addFun(new MaxFunction(context));
        engine.addFun(new MinFunction(context));
        engine.addFun(new MaxColFunction(context));
        engine.addFun(new MinColFunction(context));
        engine.addFun(new AbsFuction(context));
        engine.addFun(new SetFunction(context));
        engine.addFun(new LoopFunction(context, engine));
    }

    public void initData(Map<String, Field> dataSet) {
        FelContext ctx = engine.getContext();
        //把数据源加载到context
        ctx.set(Constant.DATA_SET, dataSet);

        for (Map.Entry<String, Field> entry : dataSet.entrySet()) {
            Field obj = entry.getValue();
            if (obj != null) {
                ctx.set(Constant.DATA_SIZE, ((List)obj.getValue()).size());
                break;
            }
        }
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
