package com.cxp.springboot2shardingsphere.util;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

/**
 * @author : cheng
 * @date : 2019-10-27 10:28
 */
public class DataSourceUtil {
    private static final String HOST = "192.168.8.101";

    private static final int PORT = 3306;

    private static final String USER_NAME = "root";

    private static final String PASSWORD = "123456";

    public static DataSource createDataSource(final String dataSourceName) {
        HikariDataSource result = new HikariDataSource();
        result.setDriverClassName(com.mysql.cj.jdbc.Driver.class.getName());
        result.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8", HOST, PORT, dataSourceName));
        result.setUsername(USER_NAME);
        result.setPassword(PASSWORD);
        return result;
    }

}