package com.zl.sneakerweb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zl.sneakerentity.model.ProductInfo;
import com.zl.sneakerentity.redis.GoodsKey;
import com.zl.sneakerentity.redis.RedisService;
import com.zl.sneakerserver.dto.ImageHolder;
import com.zl.sneakerserver.dto.ProductCategoryDto;
import com.zl.sneakerserver.dto.ProductInfoDto;
import com.zl.sneakerserver.server.ProductCategoryServer;
import com.zl.sneakerserver.server.ProductInfoServer;
import com.zl.sneakerserver.utils.PathUtil;
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
import java.io.*;
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
     * @return
     */
    @GetMapping("/category/list_all")
    public Object categoryAll(){
        try {
        /**从缓存中读取**/
        String cacheProductCategory= redisService.get(GoodsKey.getGoodsList,"",String.class);
        if (!StringUtils.isEmpty(cacheProductCategory)){
            //string转jsonObject,jsonObject再转实体类对象
            JSONObject cacheProductCategoryObject =JSONObject.parseObject(cacheProductCategory);
            ProductCategoryDto cacheProductCategoryDto = JSON.toJavaObject(cacheProductCategoryObject,ProductCategoryDto.class);
            //拼接缓存中的数据
            Map<String,Object> cacheMap = new HashMap<>();
            cacheMap.put("productcategory_list",cacheProductCategoryDto.getProductCategoryList());
            return ResultUtil.ok(cacheMap);
        }

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

    @PostMapping(value = "/upload")
    @ResponseBody
    public Object uploadProductImg(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        ProductInfo product = null;
        String productStr = RequestUtil.getString(request, "productStr");
        MultipartHttpServletRequest multipartRequest = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try
        {
            //检查是否有图片
            if (multipartResolver.isMultipart(request))
            {
                thumbnail = handleImage((MultipartHttpServletRequest) request, productImgList);
//                System.out.println("图片名字"+thumbnail.getImageName());
//                System.out.println(productImgList.get(0).getImageName());
            } else
            {
                modelMap.put("success", false);
                modelMap.put("errorMsg", "上传图片不能为空");
                return modelMap;
            }
        } catch (IOException e)
        {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        try
        {
            product = mapper.readValue(productStr, ProductInfo.class);
            modelMap.put("errMsg",product.getProductName());
        } catch (Exception e)
        {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

//        if (product != null && thumbnail != null && productImgList.size() > 0)
//        {
//            try
//            {
//                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
//                //减少对前端的依赖
//                Shop shop = new Shop();
//                shop.setShopId(currentShop.getShopId());
//                product.setShop(shop);
//                ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
//                if (pe.getState() == ProductStateEnum.SUCCESS.getState())
//                {
//                    modelMap.put("success", true);
//                } else
//                {
//                    modelMap.put("success", false);
//                    modelMap.put("errMsg", pe.getStatInfo());
//                }
//            } catch (ProductOperationException e)
//            {
//                modelMap.put("success", false);
//                modelMap.put("errMsg", e.toString());
//                return modelMap;
//            }
//        } else
//        {
//            modelMap.put("success", false);
//            modelMap.put("errMsg", "请输入商品信息");
//        }
        return modelMap;
    }

    private ImageHolder handleImage(MultipartHttpServletRequest request, List<ImageHolder> productImgList) throws IOException
    {
        //构建ImageHolder
        MultipartFile thumbnailFile = request.getFile("thumbnail");
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
        System.out.println(thumbnail.getImageName());

        byte[] bytes=new byte[1024000];
        InputStream inputStream = thumbnailFile.getInputStream();
        File dest = new File(PathUtil.getImgBasePath()+"123.jpg");
        int len= -2;
        len = inputStream.read(bytes);
        OutputStream outputStream = new FileOutputStream(dest);
        outputStream.write(bytes,0,len);

        for (int i = 0; i < IMAGEMAXCOUNT; i++)
        {
            MultipartFile productImgFile =request.getFile("productImg" + i);
            if (productImgFile != null)
            {
                ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
                productImgList.add(productImg);
                System.out.println(productImg.getImageName());
            } else
            {
                break;
            }
        }
        return thumbnail;
    }


    @RequestMapping("/upload2")
    @ResponseBody
    public Object handleFileUpload(@RequestParam("file")MultipartFile file){
        if (file.isEmpty()){
            return ResultUtil.fail();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String location =df.format(new Date()) + System.getProperty("file.separator");
        // 判断文件夹是否存在，不存在则
        String basePath = PathUtil.getImgBasePath() +System.getProperty("file.separator");
        File targetFile = new File(basePath + location);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        String fileName = file.getOriginalFilename();
        fileName = fileName.length()>10?fileName.substring(fileName.length()-10):fileName;
        String url ="";
        try{
            Files.copy(file.getInputStream(), Paths.get(basePath + location, fileName), StandardCopyOption.REPLACE_EXISTING);
            url = location + fileName;
        }catch (Exception e){
            e.printStackTrace();
        }

        return url;
    }


}
