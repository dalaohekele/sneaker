package com.zl.sneakerserver.dto;

import java.io.InputStream;

/**
 * @Auther: le
 * @Date: 2018/8/30 15:26
 * @Description:
 */
public class ImageHolder {
    private String imageName;
    private InputStream image;

    public ImageHolder(String imageName,InputStream image){
        this.imageName = imageName;
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }
}
