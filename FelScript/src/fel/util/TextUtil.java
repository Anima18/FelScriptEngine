package fel.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {
    public static boolean isEmpty(Object str) {
        return (str == null || str.toString().length() == 0);
    }

    public static boolean isInt(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public static boolean isDouble(String str) {
        return str.matches("-?[0-9]+.*[0-9]*");
    }
}
