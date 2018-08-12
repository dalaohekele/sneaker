package com.zl.sneakerserver.server;

import com.zl.sneakerentity.model.ProductInfo;
import com.zl.sneakerserver.dto.ProductInfoDto;

import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/7/27 13:46
 * @Description:
 */
public interface ProductInfoServer {

    /**
     * 通过商品类别查询 上架商品
     * @return
     */
    ProductInfoDto findByProductCategoryTpye(List<Integer> categoryTypeList,Integer pageIndex, Integer pageSize);

    /**
     * 通过id查找商品
     * @param productId
     * @return
     */
    ProductInfo findById(String productId);
}
