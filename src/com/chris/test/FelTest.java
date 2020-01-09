package com.chris.test;

import com.chris.fel.function.FunctionRepository;
import com.chris.fel.script.Field;
import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.jakewharton.fliptables.FlipTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FelTest {
    public static void main(String[] args) {

        FelEngine engine = new FelEngineImpl();
        FunctionRepository repository = new FunctionRepository(engine);
        repository.initFunction();
        repository.initData(initData());

        Object eval = null;
        System.out.println("============test SUM==============");
        eval = engine.eval("SUM(A, 10)");
        System.out.println(eval);

        System.out.println("============test AVG==============");
        eval = engine.eval("AVG(A, 10)");
        System.out.println(eval);

        System.out.println("============test IF==============");
        eval = engine.eval("IF(REFL(B,1) != 0, REFL(B,1)*11/13+REFL(E)*2/13, REFL(E))");
        System.out.println(eval);


        System.out.println("============test AND==============");
        eval = engine.eval("AND(REFL(A, 5) == 40, SUM(A, 5) > 0)");
        System.out.println(eval);

        System.out.println("============test OR==============");
        eval = engine.eval("OR(AVG(A, 5) > SUM(A, 5), 1 > 0, 5-4<0)");
        System.out.println(eval);

        System.out.println("============test REFL==============");
        eval = engine.eval("REFL(A, 1)*3");
        System.out.println(eval);

        System.out.println("============test STDEV==============");
        eval = engine.eval("STDEV(A, 5)");
        System.out.println(eval);
    }

    public static Map<String, List<Field>> initData() {
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


        Map<String, List<Field>> map = new HashMap<>();
        for(int i = 0;i<26;i++){
            String key = headers[i];
            List<Field> dataList = new ArrayList<>();
            for(int j = 0; j < 10; j++) {
                dataList.add(Field.ofValue((float)(j*10 + i)));
            }
            map.put(key, dataList);
        }
        System.out.println(FlipTable.of(headers, datas));
        return map;
    }
}
