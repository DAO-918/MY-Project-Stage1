package com.dao.impl;

import com.dao.GoodsDao;
import com.domain.Goods;
import com.utils.DBUtils;
import com.utils.dbpool.DruidUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GoodsDaoImpl implements GoodsDao {
    @Override
    public List<Goods> showGoods() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        List<Goods> goods_List = new ArrayList<>();//没有写new ArrayList<>()，发现使用add，list还是null
        String sql = "SELECT * FROM goods WHERE is_delete='0'";
        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            res = pstmt.executeQuery();
            while (res.next()){
                int g_id = res.getInt("g_id");
                String g_goods_name = res.getString("g_goods_name");
                float g_goods_price = res.getFloat("g_goods_price");
                int g_goods_stock = res.getInt("g_goods_stock");
                String g_goods_description = res.getString("g_goods_description");
                String g_goods_pic = res.getString("g_goods_pic");
                String is_delete = res.getString("is_delete");
                Goods goods = new Goods(g_id,g_goods_name,g_goods_price,g_goods_stock,g_goods_description,g_goods_pic,is_delete);
                System.out.println("DAOIMPL--showGoods成功获取："+goods);
                goods_List.add(goods);
            }
            return goods_List;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(res,pstmt,conn);
        }
        return null;
    }

    @Override
    public Goods getGoodsById(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        Goods goods = null;
        String sql = "SELECT * FROM goods WHERE g_id=? AND is_delete='0'";
        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            res = pstmt.executeQuery();
            while (res.next()){
                int g_id = res.getInt("g_id");
                String g_goods_name = res.getString("g_goods_name");
                float g_goods_price = res.getFloat("g_goods_price");
                int g_goods_stock = res.getInt("g_goods_stock");
                String g_goods_description = res.getString("g_goods_description");
                String g_goods_pic = res.getString("g_goods_pic");
                String is_delete = res.getString("is_delete");
                goods = new Goods(g_id,g_goods_name,g_goods_price,g_goods_stock,g_goods_description,g_goods_pic,is_delete);
                System.out.println("DAOIMPL--getGoodsById成功获取："+goods);
            }
            return goods;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(null,pstmt,conn);
        }
        return null;
    }

    @Override
    public int addGoodsInfo(Goods goods) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO goods VALUES (0,?,?,?,?,?,'0')";
        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,goods.getG_goods_name());
            pstmt.setFloat(2,goods.getG_goods_price());
            pstmt.setInt(3,goods.getG_goods_stock());
            pstmt.setString(4,goods.getG_goods_description());
            pstmt.setString(5,goods.getG_goods_pic());
            int result = pstmt.executeUpdate();
            System.out.println("DAOIMPL--添加商品-受影响的行数："+result);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(null,pstmt,conn);
        }
        return -1;
    }

    @Override
    public int editGoodsInfo(Goods goods) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String pic = goods.getG_goods_pic();
        Boolean is_add_pic = false;
        String sql = null;
        //判断是否重新提交了图片
        if (pic!=null){
            sql = "UPDATE goods SET g_goods_name=?,g_goods_price=?,g_goods_stock=?,g_goods_description=?,g_goods_pic=? where g_id=?";
            is_add_pic = true;
            System.out.println("存在图片的更新:--更新的内容"+goods);
        }else {
            sql = "UPDATE goods SET g_goods_name=?,g_goods_price=?,g_goods_stock=?,g_goods_description=? where g_id=?";
            is_add_pic = false;
            System.out.println("不存在图片的更新:--更新的内容"+goods);
        }
        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,goods.getG_goods_name());
            pstmt.setFloat(2,goods.getG_goods_price());
            pstmt.setInt(3,goods.getG_goods_stock());
            pstmt.setString(4,goods.getG_goods_description());
            if (is_add_pic){
                pstmt.setString(5,goods.getG_goods_pic());
                pstmt.setInt(6,goods.getG_id());
            }else {
                pstmt.setInt(5,goods.getG_id());
            }
            int result = pstmt.executeUpdate();
            System.out.println("DAOIMPL--更新商品-受影响的行数："+result);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(null,pstmt,conn);
        }

        return 0;
    }

    @Override
    public int delGoodsInfo(String[] str_arr) {
        Connection conn = null;
        PreparedStatement pstmt = null;
         StringBuffer str_buff = new StringBuffer();
        String insert_str = null;
        if (str_arr!=null){
            for (String s:str_arr){
                str_buff.append(s);
                str_buff.append(",");
            }
            System.out.println("str_buff:--"+str_buff);
            System.out.println("str_buff.toString():--"+str_buff.toString());
            insert_str = str_buff.toString().substring(0,str_buff.length()-1);
            System.out.println("即将删除的id序列：--insert_str:"+insert_str);
        }
        String sql = "UPDATE goods SET is_delete=? where g_id in ("+insert_str+")";
        try {
            conn = DBUtils.getConnection();//忘了连接数据库了
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,"1");
            //pstmt.setString(2,insert_str);
            int result = pstmt.executeUpdate();
            System.out.println("DAOIMPL--删除商品-受影响的行数："+result);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.closeConnection(null,pstmt,conn);
        }
        return -1;
    }

    //分页
    //查询记录总数
    @Override
    public int findTotalCount() {
        String sql = "select count(*) totalCount from goods where is_delete='0'";
        Connection conn = null;
        Statement stmt = null;
        ResultSet res = null;
        try {
            conn = DruidUtils.getConnection();
            stmt = conn.createStatement();
            res = stmt.executeQuery(sql);
            //为什么用if因为查询后得到的一定是一条数据
            if (res.next()){
                int totalCount = res.getInt("totalCount");
                return totalCount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtils.close(res,stmt,conn);
        }
        return 0;
    }

    //查询分页集合
    @Override
    public List<Goods> findByPage(int start, int rows) {
        List<Goods> goods_list = new ArrayList<>();
        String sql = "select * from goods limit ?,?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        try {
            conn = DruidUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,start);
            pstmt.setInt(2,rows);
            res = pstmt.executeQuery();
            while (res.next()){
                int g_id = res.getInt("g_id");
                String g_goods_name = res.getString("g_goods_name");
                float g_goods_price = res.getFloat("g_goods_price");
                int g_goods_stock = res.getInt("g_goods_stock");
                String g_goods_description = res.getString("g_goods_description");
                String g_goods_pic = res.getString("g_goods_pic");
                String is_delete = res.getString("is_delete");
                Goods goods = new Goods(g_id,g_goods_name,g_goods_price,g_goods_stock,g_goods_description,g_goods_pic,is_delete);
                goods_list.add(goods);
            }
            return goods_list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //忘记添加该句时，没有将连接放回连接池中
            //导致连接数量达到峰值，页面发生的请求没有线程去处理，一直处在加载状态
            DruidUtils.close(res,pstmt,conn);
        }
        return null;
    }

    //查询符合条件的记录总数
    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        //定义模板初始化sql 不做更改的情况下一定会执行成功
        String sql = "select count(*) totalCount from goods where 1=1 and is_delete='0'";
        StringBuilder sb = new StringBuilder(sql);
        //====与下面的代码相同的部分=====
        //遍历map
        Set<String> keySet = condition.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {
            //排除分页条件参数
            if("currentPage".equals(key) || "rows".equals(key) || "method".equals(key)){
                continue;
            }
            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if(value != null && !"".equals(value)){
                //有值
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//?条件的值
            }
        }
        //=========
        System.out.println(sb.toString());
        System.out.println(params);

        //int 是否结果一样
        Long count = 0L;
        List<Map<String, Object>> datas = new ArrayList<>();

        //使用DBUtils.executeQuery动态生成SQL语句并查询
        //只查询删除状态为0的，没有逻辑删除过的记录
        //select count(*) totalCount from goods where 1=1 and flag='0' and xx like '%```%' and........
        try {
            datas = DBUtils.executeQuery(sb.toString(),params.toArray());
            //获得第一个map的value值
            count = (Long) datas.get(0).get("totalCount");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count.intValue();
    }

    //查询符合条件的记录的分页集合
    @Override
    public List<Map<String, Object>> findByPage(int start, int rows, Map<String, String[]> condition) {
        String sql = "select * from goods where 1=1 and is_delete='0'";
        StringBuilder sb = new StringBuilder(sql);
        //=========
        //遍历map
        Set<String> keySet = condition.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {
            //排除分页条件参数
            if("currentPage".equals(key) || "rows".equals(key) ||"method".equals(key)){
                continue;
            }
            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if(value != null && !"".equals(value)){
                //有值
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//？条件的值
            }
        }
        //=========
        //添加分页查询
        sb.append(" limit ?,? ");
        //添加分页查询参数值
        params.add(start);
        params.add(rows);
        System.out.println(sb.toString());
        System.out.println(params);

        List<Map<String, Object>> datas = new ArrayList<>();
        //只查询删除状态为0的，没有逻辑删除过的记录
        //select count(*) totalCount from goods where 1=1 and flag='0' and xx like '%```%' and........
        try {
            datas = DBUtils.executeQuery(sb.toString(),params.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datas;
    }

}
