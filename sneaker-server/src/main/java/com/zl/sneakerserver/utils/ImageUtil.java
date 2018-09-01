package com.zl.sneakerserver.utils;

import com.zl.sneakerserver.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Auther: le
 * @Date: 2018/8/30 15:19
 * @Description:
 */
public class ImageUtil {


    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 随机图片名
     * @return
     */
    private static String getRandomImageName(){
        return UUID.randomUUID().toString();
    }

    /**
     * 根据日期创建文件路径
     * @return
     */
    private static String getDatePath(){
        return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    }

    /**
     * 获取文件拓展名
     * @param fileName
     * @return
     */
    private static String getFileExtension(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 创建文件夹
     * @param targetAddr
     */
    private static void mkDirPath(String targetAddr){
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()){
            dirPath.mkdirs();
        }
    }

    /**
     * 上传图片
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateNormalImg(ImageHolder thumbnail,String targetAddr){
//        String datePath = getDatePath(); //文件夹名
        String imageName = getRandomImageName(); //图片名
        String extension = getFileExtension(thumbnail.getImageName()); //
        mkDirPath(targetAddr);
        //相对路径
        String relativeAddr = targetAddr + imageName  + extension;
        File dest = new File(PathUtil.getImgBasePath()+relativeAddr);

        try {
            Thumbnails.of(thumbnail.getImage()).size(337,640)
                    .outputQuality(0.9f).toFile(dest);
        }catch (Exception e){
            e.printStackTrace();
        }
        return relativeAddr;
    }
}
