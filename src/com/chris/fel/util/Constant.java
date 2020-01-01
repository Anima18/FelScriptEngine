package com.chris.fel.util;

public interface Constant {
    String TRUE = "True";
    String FALSE = "False";
    String DATA_SET = "dataSet";

    /**
     * error message
     */
    String paramRepeatError = "【×】初始化参数 %s 失败: 参数名和数据源列表索引重名";
    String varRepeatError = "【×】初始化变量 %s 失败: 变量名和数据源列表索引重名";
    String execRunError = "【×】第%s行，执行脚本语句 %s 失败: %s";
}
