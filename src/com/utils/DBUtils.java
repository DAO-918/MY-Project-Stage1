package com.utils;

import java.sql.*;

public class DBUtils {
    private static String url = DBConfig.getValue("url");
    private static String username = DBConfig.getValue("username");
    private static String password = DBConfig.getValue("password");
    private static String driver = DBConfig.getValue("driver");

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url,username,password);
        //在使用DriverManager.getConnection(url,user,password)
        // 获得数据库连接的过程中，错将getConnetion()方法的三个参数写成了一个
        return conn;
    }

    /**
     * 关闭数据库连接
     * @param res
     * @param stmt
     * @param conn
     */
    public static void closeConnection(ResultSet res, Statement stmt, Connection conn){
        try {
            if (res!=null){res.close();}
            if (stmt!=null){stmt.close();}
            if (conn!=null){conn.close();}
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
