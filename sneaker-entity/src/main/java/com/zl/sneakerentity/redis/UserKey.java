package com.zl.sneakerentity.redis;

/**
 * @Auther: le
 * @Date: 2018/8/9 17:01
 * @Description:
 */
public class UserKey extends BasePrefix{
    public static final int TOKEN_EXPIRE = 3600*24*2; //默认俩天

    private UserKey(int expireSeconds,String prefix){
        super(expireSeconds,prefix);
    }

    /**
     * 需要缓存的字段
     */
    public static UserKey token = new UserKey(TOKEN_EXPIRE,"token");
    public static UserKey getById = new UserKey(0,"id");



}
