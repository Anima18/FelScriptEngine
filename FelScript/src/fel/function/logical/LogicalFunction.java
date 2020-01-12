package fel.function.logical;

import fel.FelScriptException;
import fel.function.BaseFunction;
import com.greenpineyu.fel.context.FelContext;

public abstract class LogicalFunction extends BaseFunction {
    public LogicalFunction(FelContext context) {
        super(context);
    }

    @Override
    protected void validateParam(Object[] objects) {
        if (objects == null || objects.length < 2) {
            throw new FelScriptException(String.format("%s运算出错，参数不正确！", getName()));
        }
    }
}
