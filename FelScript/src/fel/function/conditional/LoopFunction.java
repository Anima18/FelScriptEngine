package fel.function.conditional;

import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.context.FelContext;
import fel.function.BaseFunction;

import static fel.util.Constant.DATA_SIZE;

public class LoopFunction extends BaseFunction {
    private FelEngine engine;
    public LoopFunction(FelContext context, FelEngine engine) {
        super(context);
        this.engine = engine;
    }

    @Override
    protected void validateParam(Object[] objects) {

    }

    @Override
    public Object call(Object[] objects) {
        String indexCode = objects[0].toString();
        int startIndex = Integer.parseInt(context.get(indexCode).toString());
        int endIndex = Integer.parseInt(context.get(DATA_SIZE).toString());
        for(int i = startIndex; i < endIndex; i++) {
            for(int j = 1; j < objects.length; j++) {
                String expression = objects[j].toString();
                engine.eval(expression);
            }
            context.set(indexCode, i+1);

        }
        return null;
    }

    @Override
    public String getName() {
        return "LOOP";
    }
}
