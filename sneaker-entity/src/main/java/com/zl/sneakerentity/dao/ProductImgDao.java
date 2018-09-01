package com.zl.sneakerentity.dao;

import com.zl.sneakerentity.model.ProductImg;

import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/8/30 19:34
 * @Description:
 */
public interface ProductImgDao {
    /**
     * 批量添加商品缩略图
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);
}
