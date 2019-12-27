package com.chris.fel;

import com.chris.fel.script.ScriptVar;

import java.io.File;
import java.util.List;
import java.util.Map;

public class FelScriptEngine {
    public static final String DATASET = "DATASET";

    private File script;
    private Map<String, List<String>> dataSource;

    private FelScriptEngine(Builder builder) {
        this.script = builder.script;
        this.dataSource = builder.dataSource;
    }

    public List<ScriptVar> eval() {
        return null;
    }

    public static class Builder {
        private File script;
        private Map<String, List<String>> dataSource;

        public Builder setScript(File script) {
            this.script = script;
            return this;
        }

        public Builder setDataSource(Map<String, List<String>> dataSource) {
            this.dataSource = dataSource;
            return this;
        }

        public FelScriptEngine build() {
            return new FelScriptEngine(this);
        }
    }
}
