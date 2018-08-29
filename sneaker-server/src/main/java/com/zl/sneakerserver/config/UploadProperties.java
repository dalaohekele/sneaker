package com.zl.sneakerserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Auther: le
 * @Date: 2018/8/29 14:23
 * @Description:
 */

/**
 * 配置图片
 */
@Component
@ConfigurationProperties("upload")
public class UploadProperties {
    //获取存放位置
    private Map<String,String> localtion;
    //单个文件大小
    private String maxFileSize;
    //单次上传总文件大小
    private String maxRequestSize;

    public String getBasePath(){
        String location="";
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")){
            location = this.getLocaltion().get("windows");
        }else if (os.toLowerCase().startsWith("linux")){
            location = this.getLocaltion().get("linux");
        }else {
            location = this.getLocaltion().get("mac");
        }
        return location;
    }


    public Map<String, String> getLocaltion() {
        return localtion;
    }

    public void setLocaltion(Map<String, String> localtion) {
        this.localtion = localtion;
    }

    public String getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(String maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public String getMaxRequestSize() {
        return maxRequestSize;
    }

    public void setMaxRequestSize(String maxRequestSize) {
        this.maxRequestSize = maxRequestSize;
    }
}
