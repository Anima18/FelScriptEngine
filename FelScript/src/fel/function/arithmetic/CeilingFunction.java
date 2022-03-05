package fel.function.arithmetic;

import com.greenpineyu.fel.context.FelContext;
import fel.FelScriptException;
import fel.function.BaseFunction;
import fel.util.TextUtil;

import java.math.BigDecimal;

/**
 * CEILING 函数将数字向上舍入（沿绝对值增大的方向）到最近的指定基数的倍数。
 */
public class CeilingFunction extends BaseFunction {
    public CeilingFunction(FelContext context) {
        super(context);
    }

    @Override
    protected void validateParam(Object[] objects) {
        if (objects == null || objects.length != 2) {
            throw new FelScriptException(String.format("%s运算出错，参数不正确！", getName()));
        }
    }

    @Override
    public Object call(Object[] objects) {
        validateParam(objects);

        double number = Double.parseDouble(objects[0].toString());
        double significance = Double.parseDouble(objects[1].toString());

        BigDecimal value1 = new BigDecimal(Double.toString(Math.ceil(number / significance)));
        BigDecimal value2 = new BigDecimal(Double.toString(significance));

        return value1.multiply(value2).doubleValue();
    }

    @Override
    public String getName() {
        return "CEILING";
    }
}
