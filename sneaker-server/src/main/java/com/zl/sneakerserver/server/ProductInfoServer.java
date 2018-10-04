package com.zl.sneakerserver.server;

import com.zl.sneakerentity.model.ProductImg;
import com.zl.sneakerentity.model.ProductInfo;
import com.zl.sneakerserver.dto.ImageHolder;
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

    /**
     *  通过状态查找, 0正常状态 1轮播图 2热销商品 3新品.
     * @param productShow
     * @return
     */
    List<ProductInfo> findByShow(Integer productShow);

    /**
     * 添加商品
     * @param productInfo
     * @param thumbnail
     * @param productImgList
     * @return
     */
    ProductInfoDto addProduct(ProductInfo productInfo, ImageHolder thumbnail, List<ImageHolder> productImgList);

    /**
     * 更新商品信息
     * @param productInfo
     * @param thumbnail
     * @param productImgList
     * @return
     */
    ProductInfoDto updateProduct(ProductInfo productInfo, ImageHolder thumbnail, List<ImageHolder> productImgList);
    /**
     * 添加商品图片
     * @param productImg
     * @return
     */
    int addProductImg(ProductImg productImg);

    /**
     * 展示商品图片
     * @param productID
     * @return
     */
    List<ProductImg> findProductImg(String productID);
}
