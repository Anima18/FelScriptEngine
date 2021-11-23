package fel.util;

import com.greenpineyu.fel.common.StringUtils;
import fel.FelScriptEngine;
import fel.script.Field;
import fel.script.ScriptVar;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class ResultSetRepository {
    private String scriptFilePath;
    private String scriptFileName;
    private Map<String, List<String>> resultListMap;

    public ResultSetRepository() {
    }

    public ResultSetRepository(File scriptFile) {
        scriptFilePath = scriptFile.getPath();
        scriptFileName = scriptFile.getName();
    }

    /**
     * 获取历史脚本运行结果文件路径
     * @return
     */
    private String getResultDataFilePath() {
        String resultFileName = String.format("%s_%s.txt", scriptFileName.split("\\.")[0], "run_data");
        return scriptFilePath.replace(scriptFileName, resultFileName);
    }

    public Map<String, List<String>> getResultListMap() {
        return resultListMap;
    }

    public void initResultSet() {
        long startTime = System.currentTimeMillis();
        String filePath = getResultDataFilePath();
        List<String> contentList = FileUtil.readText(filePath);
        if(contentList == null || contentList.size() == 0) {
            return;
        }
        List<String> header = getResultItem(contentList.get(0));
        Map<String, Integer> headerIndexMap = new HashMap<>();
        for(int i = 0; i < header.size(); i++) {
            headerIndexMap.put(header.get(i), i);
        }

        resultListMap = new HashMap<>();
        for(int i = 1; i < contentList.size(); i++) {
            if(StringUtils.isEmpty(contentList.get(i).trim())) {
                continue;
            }
            List<String> content = getResultItem(contentList.get(i));
            if(content.size() != header.size()) {
                continue;
            }

            headerIndexMap.forEach((k, v) -> {
                List<String> resultList;
                if(resultListMap.containsKey(k)) {
                    resultList = resultListMap.get(k);
                }else {
                    resultList = new ArrayList<>();
                    resultListMap.put(k, resultList);
                }
                resultList.add(content.get(v));
            });
        }

        long endTime=System.currentTimeMillis();
        System.out.println("加载历史数据时间： "+(endTime-startTime)+"ms");
    }

    private List<String> getResultItem(String content) {
        return Arrays.asList(content .split("\\|\\|")).stream().map(s -> (s.trim())).collect(Collectors.toList());
    }

    public void setResultSet(Map<String, Field> dataSet) {
        long startTime=System.currentTimeMillis();
        final List<String> headerList = new ArrayList<>(dataSet.keySet());
        List<Map<String, String>> contentList = new ArrayList<>();
        for(int i = 0; i < ((List<String>)dataSet.get("A").getValue()).size(); i++) {
            Map<String, String> content = new HashMap<>();
            for(Field field : dataSet.values()) {
                List<?> values = (List<?>)field.getValue();
                content.put(field.getName(), values.get(i).toString());
            }
            contentList.add(content);
        }

        List<String> resultList = new ArrayList<>();
        String headerString = headerList.stream().collect(Collectors.joining("||"));
        resultList.add(headerString);

        int contentSize = contentList.size();
        if(contentSize > 200) {
            contentList = contentList.subList(contentSize - 200, contentSize);
        }

        for(int i=0; i< contentList.size(); i++) {
            Map<String, String> content = contentList.get(i);
            String contentString = headerList.stream().map(header -> StringUtils.isEmpty(content.get(header)) ? " " : content.get(header)).collect(Collectors.joining("||"));
            resultList.add(contentString);
        }

        String resultFilePath = getResultDataFilePath();
        FileUtil.writeTextList(resultFilePath, resultList);
        long endTime=System.currentTimeMillis();
        System.out.println("保存历史数据时间： "+(endTime-startTime)+"ms");
    }

    /*public void setResultSet(Map<String, Field> dataSet) {
        long startTime=System.currentTimeMillis();
        final List<String> headerList = new ArrayList<>(dataSet.keySet());
        List<Map<String, String>> contentList = new ArrayList<>();
        for(int i = 0; i < ((List<String>)dataSet.get("A").getValue()).size(); i++) {
            Map<String, String> content = new HashMap<>();
            for(Field field : dataSet.values()) {
                List<?> values = (List<?>)field.getValue();
                content.put(field.getName(), values.get(i).toString());
            }
            contentList.add(content);
        }

        StringBuffer buffer = new StringBuffer();
        String headerString = headerList.stream().collect(Collectors.joining("||"));
        buffer.append(headerString);
        buffer.append("\n");

        int contentSize = contentList.size();
        if(contentSize > 200) {
            contentList = contentList.subList(contentSize - 200, contentSize);
        }

        for(int i=0; i< contentList.size(); i++) {
            Map<String, String> content = contentList.get(i);
            String contentString = headerList.stream().map(header -> StringUtils.isEmpty(content.get(header)) ? " " : content.get(header)).collect(Collectors.joining("||"));
            buffer.append(contentString);
            buffer.append("\n");
            System.out.println("--------------------------第"+i+"记录--------------------------------");
            System.out.println(contentString);
            System.out.println("----------------------------------------------------------");
        }

        System.out.println("--------------------------所有数据--------------------------------");
        System.out.println(buffer.toString());
        System.out.println("----------------------------------------------------------");

        String resultFilePath = getResultDataFilePath();
        FileUtil.writeText(resultFilePath, buffer.toString());
        long endTime=System.currentTimeMillis();
        System.out.println("保存历史数据时间： "+(endTime-startTime)+"ms");
    }*/

    public static void main(String[] args) {
        ResultSetRepository repository = new ResultSetRepository();
        for(int i = 0; i < 100; i++) {
            repository.initResultSetTest();
            repository.setResultSetTest(repository.resultListMap);
        }

    }

    public void initResultSetTest() {
        long startTime = System.currentTimeMillis();
        String filePath = "/home/jianjianhong/work/code/github_workspace/FelScriptEngine/scriptTest_run_data.txt";
        List<String> contentList = FileUtil.readText(filePath);
        if(contentList == null || contentList.size() == 0) {
            return;
        }
        List<String> header = getResultItem(contentList.get(0));
        Map<String, Integer> headerIndexMap = new HashMap<>();
        for(int i = 0; i < header.size(); i++) {
            headerIndexMap.put(header.get(i), i);
        }

        resultListMap = new HashMap<>();
        for(int i = 1; i < contentList.size(); i++) {
            if(StringUtils.isEmpty(contentList.get(i).trim())) {
                continue;
            }
            List<String> content = getResultItem(contentList.get(i));
            if(content.size() != header.size()) {
                continue;
            }

            headerIndexMap.forEach((k, v) -> {
                List<String> resultList;
                if(resultListMap.containsKey(k)) {
                    resultList = resultListMap.get(k);
                }else {
                    resultList = new ArrayList<>();
                    resultListMap.put(k, resultList);
                }
                resultList.add(content.get(v));
            });
        }

        long endTime=System.currentTimeMillis();
        System.out.println("加载历史数据时间： "+(endTime-startTime)+"ms");
    }

    public void setResultSetTest(Map<String, List<String>> dataSet) {
        long startTime=System.currentTimeMillis();
        final List<String> headerList = new ArrayList<>(dataSet.keySet());
        List<Map<String, String>> contentList = new ArrayList<>();


        /*dataSet.values().forEach(value -> {
            Map<String, String> content = new HashMap<>();
            dataSet.keySet().forEach(key -> {
                content.put(key, values.get(i).toString());
            });
        });*/

        for(int i = 0; i < dataSet.get("A").size(); i++) {
            Map<String, String> content = new HashMap<>();
            final int index = i;
            dataSet.keySet().forEach(key -> {
                content.put(key, dataSet.get(key).get(index));
            });
            contentList.add(content);
        }

        StringBuffer buffer = new StringBuffer();
        String headerString = headerList.stream().collect(Collectors.joining("||"));
        buffer.append(headerString);
        buffer.append("\n");

        int contentSize = contentList.size();
        if(contentSize > 200) {
            contentList = contentList.subList(contentSize - 200, contentSize);
        }

        for(Map<String, String> content : contentList) {
            String contentString = headerList.stream().map(header -> StringUtils.isEmpty(content.get(header)) ? " " : content.get(header)).collect(Collectors.joining("||"));
            buffer.append(contentString);
            buffer.append("\n");
        }
        System.out.println(buffer.toString());
        String resultFilePath = "/home/jianjianhong/work/code/github_workspace/FelScriptEngine/scriptTest_run_data_ddddddd.txt";
        FileUtil.writeText(resultFilePath, buffer.toString());
        long endTime=System.currentTimeMillis();
        System.out.println("保存历史数据时间： "+(endTime-startTime)+"ms");
    }
}
