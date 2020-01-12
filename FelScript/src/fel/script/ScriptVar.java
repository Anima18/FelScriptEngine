package fel.script;

public class ScriptVar extends Field {
    private int lineNum;
    public ScriptVar(int lineNum, String name, FieldType fieldType) {
        super(name, fieldType);
        this.lineNum = lineNum;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }
}
