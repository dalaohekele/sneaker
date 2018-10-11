package com.zl.sneakerentity.model;

/**
 * @Auther: le
 * @Date: 2018/10/9 17:35
 * @Description:
 */

import java.util.Date;

/**
 * 秒杀订单详情
 */
public class SeckillOrder {
    private Long id;
    private String userId;
    private String seckillProductId;
    private Date createTime;

    private Date updateTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSeckillProductId() {
        return seckillProductId;
    }

    public void setSeckillProductId(String seckillProductId) {
        this.seckillProductId = seckillProductId;
    }
}
