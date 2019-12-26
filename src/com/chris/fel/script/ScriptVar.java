package com.chris.fel.script;

public class ScriptVar extends Field {
    public ScriptVar(String name, String value, FieldType fieldType) {
        super(name, value, fieldType);
    }

    public ScriptVar(String name, FieldType fieldType) {
        super(name, fieldType);
    }
}
