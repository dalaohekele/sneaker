package com.zl.sneakerserver.server.impl;

import com.zl.sneakerentity.dao.ProductInfoDao;
import com.zl.sneakerentity.enums.ProductInfoStateEnum;
import com.zl.sneakerentity.model.ProductInfo;
import com.zl.sneakerserver.dto.ProductInfoDto;
import com.zl.sneakerserver.exceptions.ProductInfoException;
import com.zl.sneakerserver.server.ProductInfoServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/7/27 14:51
 * @Description:
 */
@Service
public class ProductInfoServerImpl implements ProductInfoServer {
    @Autowired
    ProductInfoDao productInfoDao;

    /**
     * 查询上架商品
     * @return
     * @throws ProductInfoException
     */
    @Override
    @Transactional
    public ProductInfoDto findByProductCategoryTpye(List<Integer> categoryTypeList,Integer pageIndex, Integer pageSize) throws ProductInfoException {
        try {
            List<ProductInfo> productInfoList = productInfoDao.selectByProductCategoryTpye(categoryTypeList,pageIndex,pageSize);
            return new ProductInfoDto(ProductInfoStateEnum.SUCCESS,productInfoList);
        }catch (Exception e){
            throw new ProductInfoException("findUpAll 查询失败"+e.getMessage());
        }
    }

    /**
     * 通过id查找商品
     * @param productId
     * @return
     */
    @Override
    @Transactional
    public ProductInfo findById(String productId) {
        try {
            ProductInfo productInfo = productInfoDao.findProductById(productId);
            return productInfo;
        }catch (Exception e){
            throw new ProductInfoException("findById 查询失败"+e.getMessage());
        }
    }




}
