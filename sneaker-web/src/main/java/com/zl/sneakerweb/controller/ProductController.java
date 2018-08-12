package com.zl.sneakerweb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zl.sneakerentity.model.ProductInfo;
import com.zl.sneakerentity.redis.GoodsKey;
import com.zl.sneakerentity.redis.RedisService;
import com.zl.sneakerserver.dto.ProductCategoryDto;
import com.zl.sneakerserver.dto.ProductInfoDto;
import com.zl.sneakerserver.server.ProductCategoryServer;
import com.zl.sneakerserver.server.ProductInfoServer;
import com.zl.sneakerweb.utils.RequestUtil;
import com.zl.sneakerweb.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: le
 * @Date: 2018/8/1 15:53
 * @Description:
 */
@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {
    @Autowired
    ProductCategoryServer productCategoryServer;

    @Autowired
    ProductInfoServer productInfoServer;

    @Autowired
    RedisService redisService;

    /**
     * 查询所有类型
     * @return
     */
    @GetMapping("/category/list_all")
    public Object categoryAll(){
        /**从缓存中读取**/
        String cacheProductCategory= redisService.get(GoodsKey.getGoodsList,"",String.class);
        //string转jsonObject,jsonObject再转实体类对象
        JSONObject cacheProductCategoryObject =JSONObject.parseObject(cacheProductCategory);
        ProductCategoryDto cacheProductCategoryDto = JSON.toJavaObject(cacheProductCategoryObject,ProductCategoryDto.class);
        //拼接缓存中的数据
        if (!StringUtils.isEmpty(cacheProductCategoryDto.getProductCategoryList())){
            Map<String,Object> cacheMap = new HashMap<>();
            cacheMap.put("productcategory_list",cacheProductCategoryDto.getProductCategoryList());
            return ResultUtil.ok(cacheMap);
        }
        try {
            ProductCategoryDto productCategoryDto = productCategoryServer.getProductCategoryAll();
            //存入缓存
            try {
                redisService.set(GoodsKey.getGoodsList,"",productCategoryDto);
            }catch (Exception e){
                log.error("缓存存入失败 error{}", e.getMessage());
            }

            Map<String,Object> map= new HashMap<>();
            map.put("productcategory_list",productCategoryDto.getProductCategoryList());
            return ResultUtil.ok(map);
        }catch (Exception e){
            log.error("商品分类 error{}", e.getMessage());
            return ResultUtil.fail();
        }
    }

    /**
     * 通过类型查询商品类别
     *
     * @return
     */
    @PostMapping("/category/list")
    public Object categoryList(@RequestBody Map<String, Object> reqMap) {

        Object typeObject = reqMap.get("type_list");
        if (typeObject == null) {
            return ResultUtil.badArgument();
        } else {
            try {
                String typeString = JSON.toJSONString(typeObject);
                List<Integer> typeList = JSONObject.parseArray(typeString, Integer.class);
                //获取数据
                ProductCategoryDto productCategoryDto = productCategoryServer.getProductCategoryList(typeList);
                Map<String, Object> map = new HashMap<>();
                map.put("productcategory_list", productCategoryDto.getProductCategoryList());
                return ResultUtil.ok(map);
            } catch (Exception e) {
                log.error("商品分类 error{}", e.getMessage());
                return ResultUtil.fail();
            }
        }
    }

    /**
     * 通过类型（多种类型）查询商品
     *
     * @param reqMap
     * @return
     */
    @PostMapping("/product_info/list")
    public Object productList(@RequestBody Map<String, Object> reqMap) {

        //获取参数
        Object typeObject = reqMap.get("type_list");
        String pageString = RequestUtil.getMapString(reqMap.get("page").toString());
        String sizeString = RequestUtil.getMapString(reqMap.get("size").toString());
        if (typeObject == null || pageString == null || sizeString == null) {
            return ResultUtil.badArgumentValue();
        }
        try {
            //转换数据类型
            Integer page = Integer.parseInt(pageString);
            Integer size = Integer.parseInt(sizeString);
            String typeString = JSON.toJSONString(typeObject);
            List<Integer> typeList = JSONObject.parseArray(typeString, Integer.class);

            //查询数据
            ProductInfoDto productInfoDto = productInfoServer.findByProductCategoryTpye(typeList, page, size);
            return ResultUtil.ok(productInfoDto.getProductInfoList());

        } catch (Exception e) {
            log.error("查询商品失败：{}", e.getMessage());
            return ResultUtil.fail();
        }

    }


    /**
     * 商品详情
     *
     * @param productId
     * @return
     */
    @GetMapping("/product_info/detail")
    public Object productDrtail(@RequestParam("product_id") String productId) {
        try {
            //get请求获取参数，参数判断
            if (StringUtils.isEmpty(productId)) {
                return ResultUtil.badArgumentValue();
            }
            //通过id获取商品数据
            ProductInfo productInfo = productInfoServer.findById(productId);
            return ResultUtil.ok(productInfo);
        } catch (Exception e) {
            log.error("商品详情获取失败：{}",e.getMessage());
            return ResultUtil.fail();
        }
    }

}
