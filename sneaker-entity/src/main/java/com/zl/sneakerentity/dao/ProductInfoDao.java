package com.zl.sneakerentity.dao;

import com.zl.sneakerentity.model.ProductInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/7/26 16:39
 * @Description:
 */
public interface ProductInfoDao {
    /**
     * 通过商品类别查询 上架商品
     *
     * @param categoryList
     * @return
     */
    List<ProductInfo> selectByProductCategoryTpye(@Param("categoryList") List<Integer> categoryList,
                                                  @Param("rowIndex") Integer rowIndex,
                                                  @Param("pageSize") Integer pageSize);

    /**
     * 通过Id查找商品
     *
     * @param productId
     * @return
     */
    ProductInfo findProductById(String productId);

    /**
     * 通过商品展示状态查找商品
     * @param productShow
     * @return
     */
    List<ProductInfo> findProductByShow(Integer productShow);

    /**
     * 根据订单数据减库存
     * @param productId  商品Id
     * @param orderDetailQuantity  商品数量
     * @return
     */
    Integer updateProductQuantity(@Param("productId") String productId,
                                  @Param("orderDetailQuantity") Integer orderDetailQuantity);


    /**
     * 根据订单回滚库存
     * @param productId
     * @param orderDetailQuantity
     * @return
     */
    Integer backProductQuantity(@Param("productId") String productId,
                                @Param("orderDetailQuantity") Integer orderDetailQuantity);

    /**
     * 添加商品
     * @param productInfo
     * @return
     */
    int insertProduct(ProductInfo productInfo);
}
