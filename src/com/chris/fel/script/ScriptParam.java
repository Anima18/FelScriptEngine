package com.chris.fel.script;

public class ScriptParam extends Field {
    public ScriptParam(String name, String value, FieldType fieldType) {
        super(name, value, fieldType);
    }

    public ScriptParam(String name, FieldType fieldType) {
        super(name, fieldType);
    }
}
