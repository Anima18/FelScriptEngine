package fel.script;

import com.sun.istack.internal.NotNull;
import fel.util.Constant;

import java.util.List;

public class Field {
    private String name;
    private Object value;
    private FieldType fieldType;

    private Field() {}

    public Field(String name, Object value, FieldType fieldType) {
        this.name = name;
        this.value = value;
        this.fieldType = fieldType;
    }

    public Field(String name, FieldType fieldType) {
        this.name = name;
        this.fieldType = fieldType;
        this.value = FieldType.initFiledValue(this);
    }

    public static Field ofValue(@NotNull Object value) {
        Field field = new Field();
        if(value instanceof Number) {
            field.setFieldType(FieldType.Numeric);
            field.setValue(Double.parseDouble(value.toString()));
        }else if(value instanceof String) {
            if(Constant.TRUE.equalsIgnoreCase(value.toString()) || Constant.FALSE.equalsIgnoreCase(value.toString())) {
                field.setFieldType(FieldType.Bool);
            }else {
                field.setFieldType(FieldType.String);
            }
            field.setValue(value.toString());
        }else if(value instanceof Boolean) {
            field.setFieldType(FieldType.Bool);
            field.setValue(value.toString());
        }else if(value instanceof List) {
            List valueList = ((List) value);
            if(valueList.size() > 0) {
                Object itemValue = valueList.get(0);
                if(itemValue instanceof Number) {
                    field.setFieldType(FieldType.List_Numeric);
                }else if(itemValue instanceof String) {
                    field.setFieldType(FieldType.List_String);
                }else if(itemValue instanceof Boolean) {
                    field.setFieldType(FieldType.List_Bool);
                }
            }else {
                field.setFieldType(FieldType.List_String);
            }

            field.setValue(value);
        }

        return field;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }
}
