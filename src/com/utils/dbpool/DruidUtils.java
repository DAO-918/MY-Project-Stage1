package com.utils.dbpool;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class DruidUtils {
    private static DataSource ds;
    static {
        //加载配置文件
        Properties pro = new Properties();
        InputStream is = DruidUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            pro.load(is);
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

    /**
     * 可以执行新增，修改，删除
     *
     * @param sql      sql语句
     * @param bindArgs 绑定参数
     * @return effectRow 影响的行数
     * @throws SQLException SQL异常
     */
    public static int executeUpdate(String sql, Object[] bindArgs) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int effectRow = -1;
        try {
            conn = DruidUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            conn.setAutoCommit(false);//取消自动提交
            if (bindArgs!=null&&bindArgs.length>0){
                for (int i=0;i<bindArgs.length;i++){
                    pstmt.setObject(i+1,bindArgs[i]);
                }
            }
            effectRow = pstmt.executeUpdate();
            conn.commit();
            String sql_uppercase = sql.toUpperCase();
            String sql_full = getExecSQL(sql,bindArgs);
            if (sql_uppercase.contains("UPDATE")){
                System.out.println("更新"+effectRow+"行-"+"SQL语句："+sql_full);
            } else if (sql_uppercase.contains("INSERT INTO")){
                System.out.println("新增"+effectRow+"行-"+"SQL语句："+sql_full);
            } else if (sql_uppercase.contains("DELETE FROM")){
                System.out.println("删除"+effectRow+"行-"+"SQL语句："+sql_full);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (conn!=null) conn.rollback();
            //抛出异常到方法throws SQLException
        }finally {
            close(null,pstmt,conn);
        }

        return effectRow;
    }


    /**
     * 执行条件查询
     *
     * @param sql      要执行的sql语句
     * @param bindArgs 绑定的参数
     * @return  List<Map<String, Object>>结果集对象
     * @throws SQLException SQL执行异常
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object[] bindArgs) {
        List<Map<String, Object>> datas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet res = null;

        try {
            conn = DruidUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            if (bindArgs!=null&&bindArgs.length>0){
                //!!!注意下标
                for (int i=0;i<bindArgs.length;i++){
                    pstmt.setObject(i+1,bindArgs[i]);
                }
            }
            res = pstmt.executeQuery();
            datas = getDatas(res);
            String sql_full = getExecSQL(sql,bindArgs);
            System.out.println("查询到"+datas.size()+"行-"+"SQL语句："+sql_full);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            close(res,pstmt,conn);
        }

        return null;
    }

    /**
     * 将结果集对象封装成List<Map<String, Object>>对象
     *
     * @param resultSet 结果多想
     * @return 结果的封装
     * @throws SQLException
     */
    private static List<Map<String, Object>> getDatas(ResultSet resultSet) {
        List<Map<String, Object>> datas = new ArrayList<>();
        try {
            //getMetaData
            ResultSetMetaData metaData = resultSet.getMetaData();
            //没有该句会抛出Before start of result set
            while (resultSet.next()){
                Map<String,Object> map = new HashMap<>();
                //从1开始：
                for (int i=1;i<=metaData.getColumnCount();i++){
                    //getColumnName
                    //getObject
                    map.put(metaData.getColumnName(i),resultSet.getObject(i));
                }
                datas.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i=0;i<datas.size();i++){
            //Map<String,Object> map = datas.get(i);
            System.out.println("第"+(i+1)+"行："+datas.get(i));
        }
        return datas;
    }


    /**
     * 显示最终提交给数据库的sql语句，只是为了展示语句内容，与搜索的最终结果无关
     *
     * @param sql      传入statement的半成品sql语句
     * @param bindArgs 搜索的条件内容
     * @return         最终提交给数据库的sql语句
     */
    private static String getExecSQL(String sql, Object[] bindArgs) {
        StringBuilder strBuilder = new StringBuilder(sql);
        if (bindArgs!=null&&bindArgs.length>0){
            int index = 0;
            for (int i=0;i<bindArgs.length;i++){
                index = strBuilder.indexOf("?",index);
                strBuilder.replace(index,index+1,bindArgs[i].toString());
                //String.valueOf(bindArgs[i])与bindArgs[i].toString()的差别？？？、
            }
        }
        return strBuilder.toString();
    }


    @Test
    public void test() throws SQLException {
        //Connection conn = getConnection();
        //System.out.println(conn);
        //conn.close();


        String sql = "update account set money=? where id=?";
        Object[] bindArgs = {100,1};
        String sql_full = getExecSQL(sql,bindArgs);
        System.out.println(sql_full);

        String sql_2 = "SELECT * FROM goods WHERE g_id<?";
        Object[] bindArgs_2 = {4};
        executeQuery(sql_2,bindArgs_2);

    }
}
