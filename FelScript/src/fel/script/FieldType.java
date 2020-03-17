package fel.script;

import fel.FelScriptException;
import fel.util.TextUtil;

import java.util.ArrayList;

public enum FieldType {
    Numeric("Numeric"), Bool("Bool"), String("String"), List("List");
    private String type;

    FieldType(java.lang.String type) {
        this.type = type;
    }

    @Override
    public java.lang.String toString() {
        return type;
    }

    /**
     * 关键字转换成字段类型
     * @param type
     * @return
     * @throws FelScriptException
     */
    public static FieldType to(String type) throws FelScriptException {
        if(type.equals("Numeric")) {
            return Numeric;
        }else if(type.equals("Bool")) {
            return Bool;
        }else if(type.equals("String")) {
            return String;
        }else if(type.contains("List")) {
            return List;
        }else {
            throw new FelScriptException(java.lang.String.format("模型脚本参数不正确,不存在%s类型,请检查!", type));
        }
    }

    /**
     * 根据字段类型初始化字段值
     * @param field
     * @return
     */
    public static Object initFiledValue(Field field) {
        FieldType type = field.getFieldType();
        switch (type) {
            case Bool:
                return false;
            case String:
                return "";
            case Numeric:
                return 0.0;
            case List:
                return new ArrayList<>();
            default:
                return null;
        }
    }

    /**
     * 根据字段类型返回字段值
     * @param field
     * @return
     */
    public static Object getFieldObject(Field field) {
        FieldType type = field.getFieldType();
        java.lang.String value = field.getValue().toString();
        switch (type) {
            case Bool:
                return Boolean.valueOf(value);
            case String:
                return value;
            case Numeric:
                if(TextUtil.isInt(value)) {
                    return Integer.parseInt(value);
                }else {
                    return Double.parseDouble(value);
                }
            case List:
                return (java.util.List)field.getValue();
            default:
                return field.getValue();
        }
    }
}
