package fel.script;

public class ScriptParam extends Field {
    private int lineNum;
    private ScriptParam(int lineNum, String name, String value, FieldType fieldType) {
        super(name, value, fieldType);
        this.lineNum = lineNum;
    }

    public static ScriptParam parse(int lineNum, String paramStr) {
        paramStr = paramStr.replaceFirst("\t", "|").replaceAll(";", "").replaceAll("\\s*", "");
        String[] paramSplitArray = paramStr.split("\\|");
        String fieldType = paramSplitArray[0];
        String[] nameSplitArray = paramSplitArray[1].split("\\(");
        String name = nameSplitArray[0];
        String value = nameSplitArray[1].split("\\)")[0];
        return new ScriptParam(lineNum, name, value, FieldType.to(fieldType));
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }
}
