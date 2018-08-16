package com.zl.sneakerserver.exceptions;

/**
 * @Auther: le
 * @Date: 2018/8/15 14:10
 * @Description:
 */
public class UserException extends RuntimeException {


    private static final long serialVersionUID = 449559596140485109L;

    public UserException(String msg){
        super(msg);
    }
}
