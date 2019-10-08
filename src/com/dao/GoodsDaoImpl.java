package com.dao;

import com.domain.Goods;
import com.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoodsDaoImpl implements GoodsDao{
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
}
