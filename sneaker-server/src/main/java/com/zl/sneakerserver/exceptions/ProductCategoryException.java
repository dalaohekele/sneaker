package com.zl.sneakerserver.exceptions;

/**
 * @Auther: le
 * @Date: 2018/7/27 12:33
 * @Description:
 */
public class ProductCategoryException extends RuntimeException{

    private static final long serialVersionUID = -4503156417034560889L;

    public ProductCategoryException(String msg){
        super(msg);
    }
}
