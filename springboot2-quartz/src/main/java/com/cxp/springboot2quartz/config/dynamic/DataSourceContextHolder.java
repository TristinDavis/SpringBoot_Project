package com.cxp.springboot2quartz.config.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : cheng
 * @date : 2020-03-11 17:18
 */
public class DataSourceContextHolder {

    public static final Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    // 设置数据源名
    public static void setDB(String dbType) {
        log.info("切换到{}数据源", dbType);
        contextHolder.set(dbType);
    }

    // 获取数据源名
    public static String getDB() {
        return (contextHolder.get());
    }

    // 清除数据源名
    public static void clearDB() {
        log.info("清除数据源 {}", contextHolder.get());
        contextHolder.remove();
    }
}
