package com.chris.test;

import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import fel.function.FunctionRepository;
import org.junit.*;

import static com.chris.test.FelTest.initData;
import static com.chris.test.FelTest.loadDataFromExcel;

public class FelScriptFuncTest {

   /* private static FelEngine engine;

    @BeforeClass
    public static void beforeClass(){
        engine = new FelEngineImpl();
        FunctionRepository repository = new FunctionRepository(engine);
        repository.initFunction();
        repository.initData(loadDataFromExcel());
    }

    @Test
    public void testSum(){
        Object eval = engine.eval("SUM(A, 10)");
        Assert.assertEquals(eval, 450.0);
    }
    @Test
    public void testAvg(){
        Assert.assertEquals(engine.eval("AVG(A, 10)"), 45.0);
    }

    @Test
    public void testAvgRang(){
        Assert.assertEquals(engine.eval("AVG(A, 10, 10)"), 45.0);
    }
    @Test
    public void testIf(){
        //Object eval = engine.eval("IF(REFL(B,1) > REFL(B,2), 'ccc', 'bbb')");
        Object eval = engine.eval("IF(REFL(B, 0)<REFL(B, 1), REFL(F), False)");
        Assert.assertEquals(eval, "ccc");
    }

    @Test
    public void testAnd(){
        Object eval = engine.eval("AND(REFL(A, 5) == 40, SUM(A, 5) > 0, REFL(B) == 1)");
        Assert.assertEquals(eval, "True");
    }

    @Test
    public void testOr(){
        Object eval = engine.eval("OR(AVG(A, 5) > SUM(A, 5), 1 > 0, 5-4<0)");
        Assert.assertEquals(eval, "True");
    }

    @Test
    public void testREFL(){
        Object eval =engine.eval("REFL(B, 0)");
        Assert.assertEquals(eval, 0);
    }

    @Test
    public void testSTDEV(){
        Object eval = engine.eval("STDEV(A, 5)");
        Assert.assertEquals(eval, 14.142135623730951);
    }

    @Test
    public void testMAX(){
        Object eval = engine.eval("MAX(1.0, 2.1, 5, 0.0002, 8.8888)");
        Assert.assertEquals(eval, 8.8888);
    }

    @Test
    public void testMIN(){
        Object eval = engine.eval("MIN(1.0, 2.1, 5, 0.0002, 8.8888)");
        Assert.assertEquals(eval, 0.0002);
    }

    @Test
    public void testMAXCOL(){
        Object eval = engine.eval("MAXCOL(A, 5, 3)");
        Assert.assertEquals(eval, 90.0);
    }

    @Test
    public void testMINCOL(){
        Object eval = engine.eval("MINCOL(A, 5)");
        Assert.assertEquals(eval, 50.0);
    }

    @Test
    public void testABS(){
        Object eval = engine.eval("ABS(-1)");
        Assert.assertEquals(eval, 1.0);
    }*/
}
