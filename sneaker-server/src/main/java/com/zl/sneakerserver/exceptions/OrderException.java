package com.zl.sneakerserver.exceptions;

/**
 * @Auther: le
 * @Date: 2018/7/27 17:01
 * @Description:
 */
public class OrderException extends RuntimeException{


    private static final long serialVersionUID = -7446372723524484141L;

    public OrderException(String msg){
        super(msg);
    }
}
