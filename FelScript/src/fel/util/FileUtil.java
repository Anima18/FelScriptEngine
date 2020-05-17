package fel.util;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.output.StringBuilderWriter;

import java.io.*;

/**
 * @author HansChen
 */
public class FileUtil {

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    public static final  int EOF                 = -1;

    private FileUtil() {
    }

    public static String readText(final String filePath, final String encoding) {
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(filePath), Charsets.toCharset(encoding));
            StringBuilderWriter writer = new StringBuilderWriter();
            int numRead;
            char[] buffer = new char[DEFAULT_BUFFER_SIZE];
            while (EOF != (numRead = reader.read(buffer))) {
                writer.write(buffer, 0, numRead);
            }
            return writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
        return "";
    }

    public static String readUTF8Text(final String filePath) {
        return readText(filePath, "GBK");
    }

    public static String getFileSuffix(String filePath) {
        String[] strArray = filePath.split("\\.");
        int suffixIndex = strArray.length -1;
        return strArray[suffixIndex];
    }
}
