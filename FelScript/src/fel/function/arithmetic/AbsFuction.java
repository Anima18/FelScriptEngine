package fel.function.arithmetic;

import com.greenpineyu.fel.context.FelContext;
import fel.FelScriptException;
import fel.function.BaseFunction;
import fel.util.TextUtil;

/**
 * 绝对值函数
 */
public class AbsFuction extends BaseFunction {
    public AbsFuction(FelContext context) {
        super(context);
    }

    @Override
    protected void validateParam(Object[] objects) {
        try {
            if (objects == null || objects.length != 1 || !TextUtil.isDouble(objects[0].toString())) {
                throw new FelScriptException(String.format("%s运算出错，参数不正确！", getName()));
            }
        }catch (Exception e) {
            throw new FelScriptException(String.format("%s运算出错，参数不正确！", getName()));
        }

    }

    @Override
    public Object call(Object[] objects) {
        validateParam(objects);
        return Math.abs(Double.parseDouble(objects[0].toString()));
    }

    @Override
    public String getName() {
        return "ABS";
    }
}
