package com.dao;

import com.domain.Goods;

import java.util.List;

public interface GoodsDao {
    List<Goods> showGoods();
    Goods getGoodsById(int g_id);
    int addGoodsInfo(Goods goods);
    int editGoodsInfo(Goods goods);
    int delGoodsInfo(String[] str_arr);
}
