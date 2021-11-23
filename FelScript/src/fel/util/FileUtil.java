package fel.util;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.output.StringBuilderWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jianjianhong
 */
public class FileUtil {

    public static void main(String[] args) {
        //FileUtil.writeText("E:/code/Idea_workspace/FelScriptEngine/scriptTest_run_data.txt", "hello\nworld");

        String sourcePath = "/home/jianjianhong/work/code/github_workspace/FelScriptEngine/scriptTest_run_data.txt";
        String targetPath = "/home/jianjianhong/work/code/github_workspace/FelScriptEngine/scriptTest_run_data_ddddddd.txt";
        List<String> value = FileUtil.readText(sourcePath);
        StringBuffer buffer = new StringBuffer();
        value.forEach(v -> {
            buffer.append(v);
            buffer.append("\n");
        });
        FileUtil.writeText(targetPath, buffer.toString());
    }

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    public static final  int EOF                 = -1;

    private FileUtil() {
    }

    public static void writeText(String filePath, String content ) {

        Path path = Paths.get(filePath);
        //创建文件
        if(!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //写模式
        try (BufferedWriter writer =
                     Files.newBufferedWriter(path,
                             StandardCharsets.UTF_8,
                             StandardOpenOption.WRITE)){
            writer.write(content);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeTextList(String filePath, List<String> contentList ) {

        Path path = Paths.get(filePath);
        if(!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));
            for (int i=0; i<contentList.size(); i++) {
                String s = contentList.get(i);
                bufferedWriter.write(s);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                System.out.println("--------------------------第"+i+"记录--------------------------------");
                System.out.println(s);
                System.out.println("----------------------------------------------------------");
            }
            bufferedWriter.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<String> readText(String filePath) {

        Path path = Paths.get(filePath);
        if(!Files.exists(path)) {
            return null;
        }
        try {
            return Files.lines(path).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

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
