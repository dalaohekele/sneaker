package com.zl.sneakerserver.server.impl;

import com.zl.sneakerentity.dao.ProductCategoryDao;
import com.zl.sneakerentity.enums.ProductCategoryStateEnum;
import com.zl.sneakerentity.model.ProductCategory;
import com.zl.sneakerserver.dto.ProductCategoryDto;
import com.zl.sneakerserver.exceptions.ProductCategoryException;
import com.zl.sneakerserver.server.ProductCategoryServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/7/27 10:06
 * @Description:
 */
@Service
public class ProductCategoryServerImpl implements ProductCategoryServer {

    @Autowired
    ProductCategoryDao productCategoryDao;


    /**
     * 分类查询
     * @param listCategoryType
     * @return
     * @throws ProductCategoryException
     */
    @Override
    @Transactional
    public ProductCategoryDto getProductCategoryList(List<Integer> listCategoryType) throws ProductCategoryException {
        if (listCategoryType.size() != 0) {
            try {
                List<ProductCategory> productCategoryList = productCategoryDao.findProductCategoryByType(listCategoryType);
                return new ProductCategoryDto(ProductCategoryStateEnum.SUCCESS,productCategoryList);
            }catch (Exception e){
                throw new ProductCategoryException("getProductCategoryList方法查询失败"+e.getMessage());
            }
        }else {
            return new ProductCategoryDto(ProductCategoryStateEnum.ERROR);
        }
    }

    /**
     * 查询所有
     * @return
     * @throws ProductCategoryException
     */
    @Override
    public ProductCategoryDto getProductCategoryAll()throws ProductCategoryException{
        try {
            List<ProductCategory> productCategoryList = productCategoryDao.allProductCategory();
            return new ProductCategoryDto(ProductCategoryStateEnum.SUCCESS,productCategoryList);
        }catch (Exception e){
            throw new ProductCategoryException("getProductCategoryAll方法查询失败" + e.getMessage());
        }
    }

}
