package com.zl.sneakerserver.exceptions;

/**
 * @Auther: le
 * @Date: 2018/8/28 17:23
 * @Description:
 */
public class CartException extends RuntimeException{


    private static final long serialVersionUID = 3714885189371020147L;

    public CartException (String msg){
        super(msg);
    }
}
