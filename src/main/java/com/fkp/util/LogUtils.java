package com.fkp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fengkunpeng
 * @version 1.0
 * @description
 * @date 2024/1/15 15:16
 */
public class LogUtils {

    public static final Logger logger = LoggerFactory.getLogger("javaStd");

    public static void error(String stringFormat, Object... args){
        logger.error(buildStringFormat(stringFormat), Thread.currentThread().getStackTrace()[2], args);
    }

    public static void debug(String stringFormat, Object... args){
        logger.debug(buildStringFormat(stringFormat), Thread.currentThread().getStackTrace()[2], args);
    }

    public static void info(String stringFormat, Object... args){
        logger.info(buildStringFormat(stringFormat), Thread.currentThread().getStackTrace()[2], args);
    }

    public static void warn(String stringFormat, Object... args){
        logger.warn(buildStringFormat(stringFormat), Thread.currentThread().getStackTrace()[2], args);
    }

    private static String buildStringFormat(String stringFormat){
        return "stackTrace: {}--" + stringFormat;
    }
}
