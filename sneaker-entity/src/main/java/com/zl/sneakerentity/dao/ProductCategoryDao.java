package com.zl.sneakerentity.dao;

import com.zl.sneakerentity.model.ProductCategory;

import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/7/26 16:39
 * @Description:
 */
public interface ProductCategoryDao {

    /**
     * 通过类别查询
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findProductCategoryByType(List<Integer> categoryTypeList);

    /**
     * 所有商品类型
     * @return
     */
    List<ProductCategory> allProductCategory();
}
