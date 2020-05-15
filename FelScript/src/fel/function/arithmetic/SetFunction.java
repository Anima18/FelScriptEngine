package fel.function.arithmetic;

import com.greenpineyu.fel.context.FelContext;
import fel.FelScriptException;
import fel.function.BaseFunction;
import fel.script.Field;
import fel.script.FieldType;
import fel.util.TextUtil;

import java.util.List;
import java.util.Map;

/**
 * 设值函数，SET(A, n, v), 给列表A第n项设值为v
 */
public class SetFunction extends BaseFunction {
    public SetFunction(FelContext context) {
        super(context);
    }

    @Override
    protected void validateParam(Object[] objects) {
        if (objects != null) {
            Object refCode = objects[0];
            Map<String, Field> dataSet = getDataSet();
            if (refCode == null) {
                throw new FelScriptException(String.format("%s运算出错，参数不正确！", getName()));
            }else if (!dataSet.containsKey(refCode)) {
                throw new FelScriptException(String.format("%s运算出错，参数%s不存在！", getName(), refCode));
            }
            if(objects.length == 3) {
                Object count = objects[1];
                if( count == null || !TextUtil.isInt(count.toString())) {
                    throw new FelScriptException(String.format("%s运算出错，参数%s不是数值！", getName(), count));
                }
            }
        } else {
            throw new FelScriptException(String.format("%s运算出错，参数不正确！", getName()));
        }
    }

    @Override
    public Object call(Object[] objects) {
        validateParam(objects);
        if(objects.length == 2) {
            setItemValue(objects);
        } else if(objects.length == 3) {
            setListItemValue(objects);
        }
        return null;
    }

    private void setItemValue(Object[] objects) {
        String refCode = String.valueOf(objects[0]);
        Object value = objects[1];
        getDataSetItem(refCode).setValue(value);
    }

    private void setListItemValue(Object[] objects) {
        String refCode = String.valueOf(objects[0]);
        int index = Integer.parseInt(objects[1].toString());
        Object value = objects[2];
        getDataSetItemValue(refCode).set(index, value);
    }

    @Override
    public String getName() {
        return "SET";
    }
}
