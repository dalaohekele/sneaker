package com.zl.sneakerentity.dao;

import com.zl.sneakerentity.model.ProductInfo;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * @Auther: le
 * @Date: 2018/7/26 18:53
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProductInfoTest {
    @Autowired
    ProductInfoDao productInfoDao;

    @Test
    public void productInfoTest(){
//        List categoryTypeList = new ArrayList();
//        Map map = new HashMap();
//        categoryTypeList.add(1);
//        categoryTypeList.add(2);
//        map.put("categoryMap",categoryTypeList);
        List<ProductInfo> productInfoList = productInfoDao.selectByProductCategoryTpye(Arrays.asList(1,11,22),1,10);
        System.out.println(productInfoList.get(1).getProductName());
//        System.out.println(productInfoList.get(2).getProductName());
    }

    @Test
    @Ignore
    public void findProductByIdTest(){
        ProductInfo productInfo = productInfoDao.findProductById("1");
        System.out.println(productInfo.getProductDescription());
    }

    @Test
    @Ignore
    public void updateProductQuantity(){
        Integer effectNum = productInfoDao.updateProductQuantity("122",1);
        System.out.println(effectNum);
    }

    @Test
    public void backProductQuantityTest(){
        Integer effectNum = productInfoDao.backProductQuantity("122",1);
        System.out.println(effectNum);
    }
}
