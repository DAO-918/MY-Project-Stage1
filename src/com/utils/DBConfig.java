package com.utils;


import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConfig {
    static Properties pro = null;
    static {
        pro = new Properties();
        InputStream resourceAsStream = DBConfig.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            pro.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key){
        String value = pro.getProperty(key);
        return value;
    }

    @Test
    public static void main(String[] args) {
        System.out.println(DBConfig.getValue("url"));
        System.out.println(DBConfig.getValue("username"));
        System.out.println(DBConfig.getValue("password"));
        System.out.println(DBConfig.getValue("driver"));
    }
}
