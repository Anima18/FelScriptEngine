package com.chris.test;

import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;
import com.jakewharton.fliptables.FlipTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chris.fel.FelScriptEngine.DATASET;

public class FelTest {
    public static void main(String[] args) {

        FelEngine fel = new FelEngineImpl();
        FelContext ctx = fel.getContext();
        /*ctx.set("单价", 5000);
        ctx.set("数量", 12);
        ctx.set("运费", 7500);
        Object result = fel.eval("单价*数量+运费");
        ctx.set("运费", result);
        System.out.println(result);
        System.out.println(ctx.get("运费"));


        Map<String, List<String>> map = new HashMap<>();
        List<String> data = new ArrayList<>();
        data.add("hello");
        map.put("data", data);
        ctx.set("map", map);
        System.out.println(fel.eval("map.data[map.data.size-1]"));*/
/*
        Map<String, List<String>> map = new HashMap<>();
        for(int i = 1;i<=26;i++){
            String key = String.valueOf(Character.toUpperCase( (char)(96+i)));
            List<String> dataList = new ArrayList<>();
            for(int m = 1; m < 10; m++) {
                int data = i*10 + m;
                dataList.add(data+"");
            }
            map.put(key, dataList);
        }*/

        String[] headers = new String[26];
        for(int i = 0;i<26;i++){
            headers[i] = String.valueOf(Character.toUpperCase( (char)(97+i)));
        }

        String[][] datas = new String[10][26];
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 26; j++) {
                datas[i][j] = String.valueOf(i*10 + j);
            }
        }


        Map<String, List<Integer>> map = new HashMap<>();
        for(int i = 0;i<26;i++){
            String key = headers[i];
            List<Integer> dataList = new ArrayList<>();
            for(int j = 0; j < 10; j++) {
                dataList.add(j*10 + i);
            }
            map.put(key, dataList);
        }

        ctx.set(DATASET, map);
        System.out.println(FlipTable.of(headers, datas));
    }
}
