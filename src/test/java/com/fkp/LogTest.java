package com.fkp;

import com.fkp.util.LogUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author fengkunpeng
 * @version 1.0
 * @description
 * @date 2024/1/19 14:33
 */
@SpringBootTest
public class LogTest {

    @Test
    void test(){
        //在打印日志时，若参数是一个耗时的操作，务必添加if判断，当开启对应日志级别是才执行，否则可能严重影响性能且不好排查
        //例如System.currentTimeMillis()是一个耗时操作，打印debug日志时写法
        if(LogUtils.logger.isDebugEnabled()){
            LogUtils.debug("time: {}", System.currentTimeMillis());
        }
        //若不添加LogUtils.logger.isDebugEnabled()，那么在日志级别为info时，虽然不会打印该日志，但是System.currentTimeMillis()会执行，造成耗时。
    }
}
