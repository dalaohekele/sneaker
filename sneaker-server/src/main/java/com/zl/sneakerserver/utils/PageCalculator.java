package com.zl.sneakerserver.utils;

/**
 * @Auther: le
 * @Date: 2018/7/31 16:53
 * @Description:
 */
// 分页
public class PageCalculator {
    public static int calculateRowIndex(Integer pageIndex,Integer pageSize){
        return (pageIndex>0)?(pageIndex -1)*pageSize:0;
    }
}
