package fel.script;

public class ScriptParam extends Field {
    private int lineNum;
    public ScriptParam(int lineNum, String name, String value, FieldType fieldType) {
        super(name, value, fieldType);
        this.lineNum = lineNum;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }
}
