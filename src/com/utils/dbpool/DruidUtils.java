package com.utils.dbpool;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DruidUtils {
    private static DataSource ds;
    static {
        //加载配置文件
        Properties pro = new Properties();
        InputStream is = DruidUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            pro.load(is);
            //
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void close(ResultSet res, Statement stmt, Connection conn){
        try {
            if (res!=null){res.close();}
            if (stmt!=null){stmt.close();}
            if (conn!=null){conn.close();}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws SQLException {
        Connection conn = getConnection();
        System.out.println(conn);
        conn.close();
    }
}
