package fel.function.arithmetic;

import fel.FelScriptException;
import fel.function.BaseFunction;
import fel.script.Field;
import fel.script.FieldType;
import fel.util.TextUtil;
import com.greenpineyu.fel.context.FelContext;

import java.util.List;
import java.util.Map;

/**
 * 引用函数，REFL(A, n), 获取列表A第n个元素
 */
public class ReflFuction extends BaseFunction {
    public ReflFuction(FelContext context) {
        super(context);
    }

    @Override
    protected void validateParam(Object[] objects) {
        if (objects != null && (objects.length == 1 || objects.length == 2)) {
            Map<String, Field> dataSet = getDataSet();
            Object refCode = objects[0];
            if(objects.length == 1) {
                FieldType fieldType = getDataType(refCode);
                if(!dataSet.containsKey(objects[0])) {
                    throw new FelScriptException(String.format("%s运算出错，参数%s不存在！", getName(), objects[0]));
                }else if(fieldType.equals(FieldType.List_String) || fieldType.equals(FieldType.List_Numeric) || fieldType.equals(FieldType.List_Bool)) {
                    throw new FelScriptException(String.format("%s运算出错，参数%s是列表，必须要指明下标参数", getName(), objects[0]));
                }
            }else if(objects.length == 2) {
                Object count = objects[1];
                if (refCode == null || count == null) {
                    throw new FelScriptException(String.format("%s运算出错，参数不正确！", getName()));
                } else if (!dataSet.containsKey(refCode)) {
                    throw new FelScriptException(String.format("%s运算出错，参数%s不存在！", getName(), refCode));
                } else if (!TextUtil.isInt(count.toString())) {
                    throw new FelScriptException(String.format("%s运算出错，参数%s不是数值！", getName(), count));
                }else if(getDataSetItemValueSize(refCode) <= Integer.parseInt(count.toString())) {
                    throw new FelScriptException(String.format("%s运算出错，%s超出了数组长度！", getName(), count.toString()));
                }
            }
        } else {
            throw new FelScriptException(String.format("%s运算出错，参数不正确！", getName()));
        }
    }

    @Override
    public Object call(Object[] objects) {
        validateParam(objects);
        Object value = "";
        if(objects.length == 1) {
            value = getItemValue(objects);
        }else {
            value = getListItemValue(objects);
        }
        Object reflCode = objects[0];
        FieldType fieldType = getDataType(reflCode);
        switch (fieldType) {
            case Numeric:
            case List_Numeric:
                return TextUtil.isEmpty(value) ? 0.0D : Double.parseDouble(value.toString());
            case String:
            case List_String:
                return TextUtil.isEmpty(value) ? "" : value.toString();
            case Bool:
            case List_Bool:
                return TextUtil.isEmpty(value) ? false :Boolean.valueOf(value.toString());
            default:
                return "";
        }
    }

    private Object getItemValue(Object[] objects) {
        Object reflCode = objects[0];
        return getDataSetItem(reflCode).getValue();
    }

    private Object getListItemValue(Object[] objects) {
        Object reflCode = objects[0];
        List valueList = getDataSetItemValue(reflCode);
        int index = 0;
        if(objects.length == 2) {
            index = Integer.parseInt(objects[1].toString());
        }
        Object value = valueList.get(index);
        return value;
    }

    @Override
    public String getName() {
        return "REFL";
    }
}
