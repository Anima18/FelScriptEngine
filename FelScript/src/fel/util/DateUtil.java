package fel.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String getNow() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
