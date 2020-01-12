package com.zl.sneakerweb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zl.sneakerentity.enums.ProductInfoStateEnum;
import com.zl.sneakerentity.model.ProductInfo;
import com.zl.sneakerentity.model.User;
import com.zl.common.redis.GoodsKey;
import com.zl.common.redis.RedisService;
import com.zl.sneakerweb.authorization.annotatiaon.AdminUser;
import com.zl.sneakerweb.authorization.annotatiaon.Autorization;
import com.zl.sneakerserver.dto.ImageHolder;
import com.zl.sneakerserver.dto.ProductCategoryDto;
import com.zl.sneakerserver.dto.ProductInfoDto;
import com.zl.sneakerserver.server.ProductCategoryServer;
import com.zl.sneakerserver.server.ProductInfoServer;
import com.zl.common.utils.PathUtil;
import com.zl.sneakerweb.utils.RequestUtil;
import com.zl.sneakerweb.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;

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


    private static final int IMAGEMAXCOUNT = 6;

    /**
     * 查询所有类型
     *
     * @return
     */
    @GetMapping("/category/list_all")
    public Object categoryAll() {
        try {
            /**从缓存中读取**/
            String cacheProductCategory = redisService.get(GoodsKey.getGoodsList, "", String.class);
            if (!StringUtils.isEmpty(cacheProductCategory)) {
                //string转jsonObject,jsonObject再转实体类对象
                JSONObject cacheProductCategoryObject = JSONObject.parseObject(cacheProductCategory);
                ProductCategoryDto cacheProductCategoryDto = JSON.toJavaObject(cacheProductCategoryObject, ProductCategoryDto.class);
                //拼接缓存中的数据
                Map<String, Object> cacheMap = new HashMap<>();
                cacheMap.put("productcategory_list", cacheProductCategoryDto.getProductCategoryList());
                return ResultUtil.ok(cacheMap);
            }

            ProductCategoryDto productCategoryDto = productCategoryServer.getProductCategoryAll();
            //存入缓存
            try {
                redisService.set(GoodsKey.getGoodsList, "", productCategoryDto);
            } catch (Exception e) {
                log.error("缓存存入失败 error{}", e.getMessage());
            }

            Map<String, Object> map = new HashMap<>();
            map.put("productcategory_list", productCategoryDto.getProductCategoryList());
            return ResultUtil.ok(map);
        } catch (Exception e) {
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
            log.error("商品详情获取失败：{}", e.getMessage());
            return ResultUtil.fail();
        }
    }

    @GetMapping("/show")
    @ResponseBody
    public Object productShow(@RequestParam("product_show") Integer productShow) {
        try {
            if (StringUtils.isEmpty(productShow)) {
                return ResultUtil.badArgumentValue();
            }
            List<ProductInfo> productInfoList = productInfoServer.findByShow(productShow);
            return ResultUtil.ok(productInfoList);
        } catch (Exception e) {
            log.error("商品首页展示详情获取失败：{}", e.getMessage());
            return ResultUtil.fail();
        }


    }


    @PostMapping(value = "/upload")
    @ResponseBody
    @Autorization
    public Object uploadProductImg(HttpServletRequest request, @AdminUser User user) {
        Map<String, Object> modelMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        ProductInfo product = null;
        String productStr = RequestUtil.getString(request, "productStr");
        MultipartHttpServletRequest multipartRequest = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            //检查是否有图片
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage((MultipartHttpServletRequest) request, productImgList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errorMsg", "上传图片不能为空");
                return modelMap;
            }
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        try {
            product = mapper.readValue(productStr, ProductInfo.class);
            modelMap.put("data", product);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        if (product != null && thumbnail != null && productImgList.size() > 0) {
            try {
                ProductInfoDto pe = productInfoServer.addProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductInfoStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    private ImageHolder handleImage(MultipartHttpServletRequest request, List<ImageHolder> productImgList) throws IOException {
        //商品缩略图
        MultipartFile thumbnailFile = request.getFile("thumbnail");
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());

        //商品详情图
        for (int i = 0; i < IMAGEMAXCOUNT; i++) {
            MultipartFile productImgFile = request.getFile("productImg" + i);
            if (productImgFile != null) {
                ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
                productImgList.add(productImg);
            } else {
                break;
            }
        }
        return thumbnail;
    }


    @RequestMapping("/upload2")
    @ResponseBody
    public Object handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResultUtil.fail();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String location = df.format(new Date()) + System.getProperty("file.separator");
        // 判断文件夹是否存在，不存在则
        String basePath = PathUtil.getImgBasePath() + System.getProperty("file.separator");
        File targetFile = new File(basePath + location);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        String fileName = file.getOriginalFilename();
        fileName = fileName.length() > 10 ? fileName.substring(fileName.length() - 10) : fileName;
        String url = "";
        try {
            Files.copy(file.getInputStream(), Paths.get(basePath + location, fileName), StandardCopyOption.REPLACE_EXISTING);
            url = location + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return url;
    }


    @PostMapping(value = "/update")
    @ResponseBody
    @Autorization
    public Object updateProductImg(HttpServletRequest request,
                                   @RequestParam("product_id") String productId,
                                   @AdminUser User user) {
        Map<String, Object> modelMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        ProductInfo product = null;
        String productStr = RequestUtil.getString(request, "productStr");
        MultipartHttpServletRequest multipartRequest = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            //检查是否有图片
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage((MultipartHttpServletRequest) request, productImgList);
            }
            product = mapper.readValue(productStr, ProductInfo.class);

            product.setProductId(productId);
            modelMap.put("data", product);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        if (thumbnail != null && productImgList.size() > 0) {
            try {
                ProductInfoDto pe = productInfoServer.updateProduct(product, thumbnail, productImgList);
                if (ProductInfoStateEnum.SUCCESS.getState().equals(pe.getState())) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }


}
