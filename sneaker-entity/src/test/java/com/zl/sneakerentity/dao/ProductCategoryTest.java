package com.zl.sneakerentity.dao;

import com.zl.sneakerentity.SneakerEntityApplication;
import com.zl.sneakerentity.model.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/7/26 17:03
 * @Description:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProductCategoryTest {
    @Autowired
    ProductCategoryDao productCategoryDao;

    @Test
    public void findProductCategoryByType(){
        List<ProductCategory> productCategoryList = productCategoryDao.findProductCategoryByType(Arrays.asList(1,2));
        System.out.println(productCategoryList.get(0).getCategoryName());
    }
}
