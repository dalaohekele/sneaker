package com.zl.sneakerserver.server;

import com.zl.sneakerserver.SneakerServerApplication;
import com.zl.sneakerserver.dto.ProductCategoryDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

/**
 * @Auther: le
 * @Date: 2018/7/27 11:53
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SneakerServerApplication.class)
public class ProductCategoryServerTest {
    @Autowired
    ProductCategoryServer productCategoryServer;

    @Test
    public void getProductCategoryListTest(){
        ProductCategoryDto productCategoryDto = productCategoryServer.getProductCategoryList(Arrays.asList(1,2));
        System.out.println(productCategoryDto.getState());
        System.out.println(productCategoryDto.getProductCategoryList().get(0).getCategoryName());
    }
}
