package com.zl.sneakerserver.server;

import com.zl.sneakerentity.model.ProductInfo;
import com.zl.sneakerserver.SneakerServerApplication;
import com.zl.sneakerserver.dto.ProductInfoDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

/**
 * @Auther: le
 * @Date: 2018/7/27 15:14
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SneakerServerApplication.class)
public class ProductInfoTest {

    @Autowired
    ProductInfoServer productInfoServer;

    @Test
    public void findUpAllTest(){
        ProductInfoDto productInfoDto = productInfoServer.findByProductCategoryTpye(Arrays.asList(1,11),1,10);
        System.out.println(productInfoDto.getProductInfoList().get(0).getProductName());
        System.out.println(productInfoDto.getStateInfo()+productInfoDto.getState());
    }

    @Test
    public void findUpByIdTest(){
        ProductInfo productInfo = productInfoServer.findById("1");
        System.out.println(productInfo.getProductName());
    }

}
