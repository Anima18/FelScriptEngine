package fel.script;

import fel.FelScriptException;

public enum FieldType {
    Numeric("Numeric"), Bool("Bool"), String("String");
    private String type;

    FieldType(java.lang.String type) {
        this.type = type;
    }

    @Override
    public java.lang.String toString() {
        return type;
    }

    public static FieldType to(String type) throws FelScriptException {
        if(type.equals("Numeric")) {
            return Numeric;
        }else if(type.equals("Bool")) {
            return Bool;
        }else if(type.equals("String")) {
            return String;
        }else {
            throw new FelScriptException(java.lang.String.format("模型脚本参数不正确,不存在%s类型,请检查!", type));
        }
    }

    public static Object getFieldObject(Field field) {
        FieldType type = field.getFieldType();
        java.lang.String value = field.getValue().toString();
        switch (type) {
            case Bool:
                return Boolean.valueOf(value);
            case String:
                return value;
            case Numeric:
                return Float.parseFloat(value);
            default:
                return field.getValue();
        }
    }
}
