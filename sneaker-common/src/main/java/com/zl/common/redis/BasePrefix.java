package com.zl.common.redis;

/**
 * @Auther: le
 * @Date: 2018/8/9 16:16
 * @Description:
 */
public abstract class BasePrefix implements KeyPrefix {
    private int expireSeconds;

    private String prefix;

    // 0表示不过期
    public BasePrefix(String prefix){
        this(0,prefix);
    }

    public BasePrefix(int expireSeconds,String prefix){
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds(){
        return expireSeconds;
    }

    @Override
    public String getPrefix(){
        String className = getClass().getSimpleName();//拿到参数类类名
        return className+":"+prefix;
    }
}
