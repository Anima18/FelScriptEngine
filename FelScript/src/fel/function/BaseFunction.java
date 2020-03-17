package fel.function;

import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.function.CommonFunction;
import fel.script.Field;
import fel.util.Constant;

import java.util.List;
import java.util.Map;

public abstract class BaseFunction extends CommonFunction {
    protected FelContext context;

    public BaseFunction(FelContext context) {
        this.context = context;
    }

    public Map<String, Field> getDataSet() {
        return (Map<String, Field>) context.get(Constant.DATA_SET);
    }

    public Field getDataSetItem(Object name) {
        return getDataSet().get(name);
    }

    public List getDataSetItemValue(Object name) {
        return (List) getDataSetItem(name).getValue();
    }

    public int getDataSetItemValueSize(Object name) {
        return getDataSetItemValue(name).size();
    }

    /*public Map<String, Field> getVarSet() {
        return (Map<String, Field>)context.get(Constant.VAR_SET);
    }*/

    protected abstract void validateParam(Object[] objects);
}
