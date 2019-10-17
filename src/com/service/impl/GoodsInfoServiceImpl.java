package com.service.impl;

import com.dao.GoodsDao;
import com.dao.impl.GoodsDaoImpl;
import com.domain.Goods;
import com.domain.PageBean;
import com.service.GoodsInfoService;

import java.util.List;

public class GoodsInfoServiceImpl implements GoodsInfoService {
    private GoodsDao goodsDao = new GoodsDaoImpl();

    @Override
    public PageBean<Goods> findByPage(int currentPage, int rows) {
        //根据当前页码和每页要显示的记录数，计算开始索引
        //第一页从0开始；第二页从rows开始；第三页从rows*2开始
        int start = (currentPage-1)*rows;
        //根据分页参数查询分页集合
        List<Goods> goodsList = goodsDao.findByPage(start,rows);
        //查询总记录数
        int totalCount = goodsDao.findTotalCount();
        //计算总页码：判断总记录数是否为每行显示的整数倍
        int totalPage = totalCount%rows==0?totalCount/rows:totalCount/rows+1;

        //封装PageBean
        PageBean<Goods> goods_page = new PageBean<>();
        goods_page.setCurrentPage(currentPage);
        goods_page.setRows(rows);
        goods_page.setTotalCount(totalCount);
        goods_page.setTotalPage(totalPage);
        goods_page.setList(goodsList);

        return goods_page;
    }
}
