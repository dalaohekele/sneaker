package com.zl.sneakerserver.server;

import com.zl.sneakerserver.dto.ProductCategoryDto;

import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/7/27 10:05
 * @Description:
 */
public interface ProductCategoryServer {

    /**
     * 通过类别获取所有商品分类
     * @return
     */
    ProductCategoryDto getProductCategoryList(List<Integer> listCategoryType);

    /**
     * 查询所有类别
     * @return
     */
    ProductCategoryDto getProductCategoryAll();
}
