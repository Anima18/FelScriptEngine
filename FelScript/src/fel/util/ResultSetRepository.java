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
            List<String> content = getResultItem(contentList.get(i));

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

        StringBuilder builder = new StringBuilder();
        String headerString = headerList.stream().collect(Collectors.joining("||"));
        builder.append(headerString);
        builder.append("\n");

        int contentSize = contentList.size();
        if(contentSize > 200) {
            contentList = contentList.subList(contentSize - 200, contentSize);
        }

        for(Map<String, String> content : contentList) {
            String contentString = headerList.stream().map(header -> StringUtils.isEmpty(content.get(header)) ? " " : content.get(header)).collect(Collectors.joining("||"));
            builder.append(contentString);
            builder.append("\n");
        }

        String resultFilePath = getResultDataFilePath();
        FileUtil.writeText(resultFilePath, builder.toString());
        long endTime=System.currentTimeMillis();
        System.out.println("保存历史数据时间： "+(endTime-startTime)+"ms");
    }
/*
    public void setResultSet(List<ScriptVar> scriptVars) {
        long startTime=System.currentTimeMillis();
        List<String> timeList = getAValues();
        if(timeList == null || timeList.size() == 0) {
            return;
        }

        //设置表头数据
        List<String> headerList = new ArrayList<>();
        headerList.add("A");
        scriptVars.forEach(scriptVar -> headerList.add(scriptVar.getName()));


        List<Map<String, String>> contentList = new ArrayList<>();
        for(int i = 0; i < timeList.size(); i++) {
            if(((List<?>) scriptVars.get(0).getValue()).get(i) == null) {
                continue;
            }
            Map<String, String> content = new HashMap<>();
            content.put("A", timeList.get(i));
            for(ScriptVar scriptVar : scriptVars) {
                List<?> values = (List<?>)scriptVar.getValue();
                content.put(scriptVar.getName(), values.get(i).toString());
            }
            contentList.add(content);
        }

        StringBuilder builder = new StringBuilder();
        String headerString = headerList.stream().collect(Collectors.joining("||"));
        builder.append(headerString);
        builder.append("\n");

        int contentSize = contentList.size();
        if(contentSize > 200) {
            contentList = contentList.subList(contentSize - 200, contentSize);
        }

        for(Map<String, String> content : contentList) {
            String contentString = headerList.stream().map(header -> StringUtils.isEmpty(content.get(header)) ? " " : content.get(header)).collect(Collectors.joining("||"));
            builder.append(contentString);
            builder.append("\n");
        }

        String resultFilePath = getResultDataFilePath();
        FileUtil.writeText(resultFilePath, builder.toString());
        long endTime=System.currentTimeMillis();
        System.out.println("保存历史数据时间： "+(endTime-startTime)+"ms");
    }*/
}
