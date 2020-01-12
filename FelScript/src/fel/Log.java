package fel;

import fel.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class Log {
    private static Log log = new Log();
    private List<String> messageList;

    private Log() {
        this.messageList = new ArrayList<>();
    }

    private static Log getInstance() {
        return log;
    }

    public static void clear() {
        getInstance().messageList.clear();
    }

    public static void i(String log) {
        getInstance().messageList.add(String.format("%s %s", DateUtil.getNow(), log));
    }

    public static String getLogs() {
        StringBuffer sb = new StringBuffer();
        for(String message : getInstance().messageList) {
            sb.append(message);
            sb.append("\n");
        }
        return sb.toString();
    }
}
