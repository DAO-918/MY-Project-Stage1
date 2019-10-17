package com.dao;

import com.domain.Goods;

import java.util.List;
import java.util.Map;

public interface GoodsDao {
    List<Goods> showGoods();
    Goods getGoodsById(int g_id);
    int addGoodsInfo(Goods goods);
    int editGoodsInfo(Goods goods);
    int delGoodsInfo(String[] str_arr);

    //分页//接下来的这部分使用新学到的线程池获取数据库信息
    //查询记录总数
    int findTotalCount();
    //查询分页集合
    List<Goods> findByPage(int start,int rows);
    //查询符合条件的记录总数
    int findTotalCount(Map<String, String[]> condition);
    //查询符合条件的记录的分页集合
    List<Map<String, Object>> findByPage(int start, int rows, Map<String, String[]> condition);

}
