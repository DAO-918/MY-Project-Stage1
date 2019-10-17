package com.service;

import com.domain.Goods;
import com.domain.PageBean;

public interface GoodsInfoService {
    PageBean<Goods> findByPage(int currentPage, int rows);
}
