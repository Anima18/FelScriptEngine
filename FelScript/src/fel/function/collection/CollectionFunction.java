package fel.function.collection;

import com.greenpineyu.fel.context.FelContext;
import fel.FelScriptException;
import fel.function.BaseFunction;

import java.util.ArrayList;
import java.util.List;

public abstract class CollectionFunction extends BaseFunction {
    protected List<Double> dataList;
    public CollectionFunction(FelContext context) {
        super(context);
    }

    @Override
    protected void validateParam(Object[] objects) {
        if (objects != null && objects.length > 0) {
            dataList = new ArrayList<>();
            try {
                for(Object o : objects) {
                    dataList.add(Double.parseDouble(o.toString()));
                }
            }catch (NumberFormatException e) {
                throw new FelScriptException(String.format("%s运算出错，参数不正确！", getName()));
            }
        } else {
            throw new FelScriptException(String.format("%s运算出错，参数不正确！", getName()));
        }
    }

}
