package com.chris.test;

import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import fel.function.FunctionRepository;
import org.junit.*;

import static com.chris.test.FelTest.initData;

public class FelScriptFuncTest {

    private static FelEngine engine;

    @BeforeClass
    public static void beforeClass(){
        engine = new FelEngineImpl();
        FunctionRepository repository = new FunctionRepository(engine);
        repository.initFunction();
        repository.initData(initData());
    }

    @Test
    public void testSum(){
        Object eval = engine.eval("SUM(Y, 10)");
        Assert.assertEquals(eval, 450.0);
    }

    @Test
    public void testAvg(){
        Assert.assertEquals(engine.eval("AVG(A, 10)"), 45.0);
    }

    @Test
    public void testIf(){
        Object eval = engine.eval("IF(REFL(B,1) != 0, REFL(B,1)*11/13+REFL(E)*2/13, REFL(E))");
        Assert.assertEquals(eval, 83);
    }

    @Test
    public void testAnd(){
        Object eval = engine.eval("AND(REFL(A, 5) == 40, SUM(A, 5) > 0)");
        Assert.assertEquals(eval, "True");
    }

    @Test
    public void testOr(){
        Object eval = engine.eval("OR(AVG(A, 5) > SUM(A, 5), 1 > 0, 5-4<0)");
        Assert.assertEquals(eval, "True");
    }

    @Test
    public void testREFL(){
        Object eval =engine.eval("REFL(A, 1)*3");
        Assert.assertEquals(eval, 240);
    }

    @Test
    public void testSTDEV(){
        Object eval = engine.eval("STDEV(A, 5)");
        Assert.assertEquals(eval, 14.142135623730951);
    }
}
