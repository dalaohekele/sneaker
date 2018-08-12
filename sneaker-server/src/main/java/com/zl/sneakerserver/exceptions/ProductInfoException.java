package com.zl.sneakerserver.exceptions;

/**
 * @Auther: le
 * @Date: 2018/7/27 15:01
 * @Description:
 */
public class ProductInfoException extends RuntimeException{


    private static final long serialVersionUID = 5426728808807100231L;

    public ProductInfoException(String msg){
        super(msg);
    }
}
