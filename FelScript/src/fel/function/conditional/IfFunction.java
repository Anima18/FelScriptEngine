package fel.function.conditional;

import fel.FelScriptException;
import fel.function.BaseFunction;
import com.greenpineyu.fel.context.FelContext;

public class IfFunction extends BaseFunction {
    public IfFunction(FelContext context) {
        super(context);
    }

    @Override
    protected void validateParam(Object[] objects) {
        if (objects != null && objects.length == 3) {
            Object refCode = objects[0];
            Object action1 = objects[1];
            Object action2 = objects[2];
            if (refCode == null || action1 == null || action2 == null) {
                throw new FelScriptException(String.format("%s运算出错，参数不正确！", getName()));
            }
        } else {
            throw new FelScriptException(String.format("%s运算出错，参数不正确！", getName()));
        }
    }

    @Override
    public Object call(Object[] objects) {
        validateParam(objects);
        return Boolean.valueOf(objects[0].toString())? objects[1] : objects[2];
    }

    @Override
    public String getName() {
        return "IF";
    }
}
