package fel.script;

public class ScriptVar extends Field {
    private int lineNum;
    private ScriptVar(int lineNum, String name, FieldType fieldType) {
        super(name, fieldType);
        this.lineNum = lineNum;
        setValue(FieldType.initFiledValue(this));
    }

    public static ScriptVar parse(int lineNum, String varStr) {
        varStr = varStr.replaceAll(";", "").replaceAll("\\s*", "");
        String[] varSplitArray = Field.fieldTypeAndName(varStr);
        String fieldType = varSplitArray[0];
        String name = varSplitArray[1];
        return new ScriptVar(lineNum, name, FieldType.to(fieldType));
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }
}
