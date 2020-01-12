package com.zl.common.utils;

/**
 * @Auther: le
 * @Date: 2018/8/30 17:51
 * @Description:
 */

/**
 * 根据环境不同提供不同的根路径
 */
public class PathUtil {
    //获取系统文件分隔符
    private static String separator = System.getProperty("file.separator");

    public static String getImgBasePath(){
        String os = System.getProperty("os.name");
        String basePath;
        if (os.toLowerCase().startsWith("win")){
            basePath = "D:/image/";
        }else {
            basePath = "/Users/le/Documents/image/";
        }
        basePath = basePath.replace("/",separator);
        return basePath;
    }

    public static String getShopImagePath(String productId){
        String imagePath = "/"+productId+"/";
        return imagePath.replace("/",separator);
    }

}
