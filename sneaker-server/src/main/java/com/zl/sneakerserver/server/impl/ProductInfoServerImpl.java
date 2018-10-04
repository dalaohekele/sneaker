package com.zl.sneakerserver.server.impl;

import com.zl.sneakerentity.dao.ProductImgDao;
import com.zl.sneakerentity.dao.ProductInfoDao;
import com.zl.sneakerentity.enums.ProductInfoStateEnum;
import com.zl.sneakerentity.model.ProductImg;
import com.zl.sneakerentity.model.ProductInfo;
import com.zl.sneakerserver.dto.ImageHolder;
import com.zl.sneakerserver.dto.ProductInfoDto;
import com.zl.sneakerserver.exceptions.ProductInfoException;
import com.zl.sneakerserver.server.ProductInfoServer;
import com.zl.sneakerserver.utils.ImageUtil;
import com.zl.sneakerserver.utils.KeyUtils;
import com.zl.sneakerserver.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    ProductImgDao productImgDao;

    /**
     * 查询上架商品
     *
     * @return
     * @throws ProductInfoException
     */
    @Override
    public ProductInfoDto findByProductCategoryTpye(List<Integer> categoryTypeList, Integer pageIndex, Integer pageSize) throws ProductInfoException {
        try {
            List<ProductInfo> productInfoList = productInfoDao.selectByProductCategoryTpye(categoryTypeList, pageIndex, pageSize);
            return new ProductInfoDto(ProductInfoStateEnum.SUCCESS, productInfoList);
        } catch (Exception e) {
            throw new ProductInfoException("findUpAll 查询失败" + e.getMessage());
        }
    }

    /**
     * 通过id查找商品
     *
     * @param productId
     * @return
     */
    @Override
    public ProductInfo findById(String productId) {
        try {
            ProductInfo productInfo = productInfoDao.findProductById(productId);
            return productInfo;
        } catch (Exception e) {
            throw new ProductInfoException("findById 查询失败" + e.getMessage());
        }
    }


    @Override
    public List<ProductInfo> findByShow(Integer productShow) {
        try {
            List<ProductInfo> productInfoList = productInfoDao.findProductByShow(productShow);
            return productInfoList;
        }catch (Exception e){
            throw new ProductInfoException("findByShow 查询失败" + e.getMessage());
        }
    }

    /**
     * 添加商品
     * @param productInfo
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws ProductInfoException
     */
    @Override
    @Transactional
    public ProductInfoDto addProduct(ProductInfo productInfo, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductInfoException{
        if (productInfo != null ){
            String productId = KeyUtils.genUniqueKey();
            productInfo.setCreateTime(new Date());
            productInfo.setUpdateTime(new Date());
            productInfo.setProductId(productId);
            //添加商品缩率图
            if (thumbnail != null) {
                addThumbnail(productInfo, thumbnail);
            }
            try {
                //添加商品信息
                int effectNum = productInfoDao.insertProduct(productInfo);
                if (effectNum <=0){
                    return new ProductInfoDto(ProductInfoStateEnum.INNER_ERROE);
                }
            }catch (Exception e){
                throw new ProductInfoException("添加商品失败："+e.getMessage());
            }
            //添加商品详情图片
            if (productImgList !=null&&productImgList.size()>0){
                addProductImgList(productInfo,productImgList);
            }
            return new ProductInfoDto(ProductInfoStateEnum.SUCCESS);
        }else {
            return new ProductInfoDto(ProductInfoStateEnum.EMPTY);
        }
    }

    /**
     * 更新商品信息
     * @param productInfo
     * @param thumbnail
     * @param productImgList
     * @return
     */
    @Override
    @Transactional
    public ProductInfoDto updateProduct(ProductInfo productInfo, ImageHolder thumbnail, List<ImageHolder> productImgList) {
        if (productInfo != null ){
            //添加商品缩率图
            if (thumbnail != null) {
                addThumbnail(productInfo, thumbnail);
            }
            //添加商品详情图片
            if (productImgList !=null&&productImgList.size()>0){
                addProductImgList(productInfo,productImgList);
            }

            try {
                //修改商品信息
                int effectNum = productInfoDao.updateProduct(productInfo);
                if (effectNum <=0){
                    return new ProductInfoDto(ProductInfoStateEnum.INNER_ERROE);
                }
            }catch (Exception e){
                throw new ProductInfoException("添加商品失败："+e.getMessage());
            }

            return new ProductInfoDto(ProductInfoStateEnum.SUCCESS);
        }else {
            return new ProductInfoDto(ProductInfoStateEnum.EMPTY);
        }
    }

    /**
     * 添加商品图片
     * @param productImg
     * @return
     */
    @Override
    public int addProductImg(ProductImg productImg) {
        return 0;
    }

    /**
     * 展示图片
     * @param productID
     * @return
     */
    @Override
    public List<ProductImg> findProductImg(String productID) {
        return null;
    }


    //添加商品图片
    private void addProductImgList(ProductInfo productInfo, List<ImageHolder> productImgHolderList){
        String dest = PathUtil.getShopImagePath(productInfo.getProductId());
        List<ProductImg> productImgList = new ArrayList<>();
        for (ImageHolder imageHolder:productImgHolderList){
            String imgAddr = ImageUtil.generateNormalImg(imageHolder,dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(productInfo.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        if (productImgList.size() >0){
            try {
                int effectNum =productImgDao.batchInsertProductImg(productImgList);
                if (effectNum <= 0){
                    throw new ProductInfoException("创建图片error:插入数据库失败");
                }
            }catch (Exception e){
                throw new ProductInfoException("创建图片error :"+e.getMessage());
            }
        }

    }

    /**
     * 添加商品缩略图
     * @param productInfo
     * @param thumbnail
     */
    private void addThumbnail(ProductInfo productInfo,ImageHolder thumbnail){
        String dest = PathUtil.getShopImagePath(productInfo.getProductId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        productInfo.setProductIcon(thumbnailAddr);
    }



}
