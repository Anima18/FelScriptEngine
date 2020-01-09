package com.chris.fel.script;

import com.chris.fel.util.Constant;
import com.sun.istack.internal.NotNull;

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
    }

    public static Field ofValue(@NotNull Object value) {
        Field field = new Field();
        if(value instanceof Number) {
            field.setFieldType(FieldType.Numeric);
        }else if(value instanceof String) {
            if(Constant.TRUE.equalsIgnoreCase(value.toString()) || Constant.FALSE.equalsIgnoreCase(value.toString())) {
                field.setFieldType(FieldType.Bool);
            }else {
                field.setFieldType(FieldType.String);
            }
        }else if(value instanceof Boolean) {
            field.setFieldType(FieldType.Bool);
        }
        field.setValue(value);
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
