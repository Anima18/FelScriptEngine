package fel;

public class FelScriptException extends RuntimeException {
    public FelScriptException(String message) {
        super(message);
    }

    public FelScriptException(int lineNum, String message) {
        super(String.format("第%s行, %s", lineNum+"", message));
    }

    public static FelScriptException withLog(String message, int... lineNum) {
        Log.i(message);
        if(lineNum == null || lineNum.length == 0) {
            return new FelScriptException(message);
        }else {
            return new FelScriptException(lineNum[0], message);
        }

    }
}
