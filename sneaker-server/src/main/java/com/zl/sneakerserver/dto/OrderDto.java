package com.zl.sneakerserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zl.sneakerentity.enums.ProductInfoStateEnum;
import com.zl.sneakerentity.enums.ResultEnum;
import com.zl.sneakerentity.model.OrderDetail;
import com.zl.sneakerentity.model.OrderMaster;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Auther: le
 * @Date: 2018/7/27 16:38
 * @Description:
 */
public class OrderDto{

    @JsonProperty("state")
    private Integer state;

    @JsonProperty("state_info")
    private String stateInfo;

    /**
     * 订单id.
     */
    @JsonProperty("order_id")
    private String orderId;

    /**
     * 买家名字.
     */
    @JsonProperty("buyer_name")
    private String buyerName;

    /**
     * 买家手机号.
     */
    @JsonProperty("buyer_phone")
    private String buyerPhone;

    /**
     * 买家地址.
     */
    @JsonProperty("buyer_address")
    private String buyerAddress;

    /**
     * 买家微信Openid.
     */
    @JsonProperty("buyer_openid")
    private String buyerOpenid;

    /**
     * 订单总金额.
     */
    @JsonProperty("order_amount")
    private BigDecimal orderAmount;

    /**
     * 订单状态, 默认为0新下单.
     */
    @JsonProperty("order_status")
    private Integer orderStatus;

    /**
     * 支付状态, 默认为0未支付.
     */
    @JsonProperty("pay_status")
    private Integer payStatus;

    /**
     * 创建时间.
     */
    @JsonProperty("create_time")
    private Date createTime;

    /**
     * 更新时间.
     */
    @JsonProperty("update_time")
    private Date updateTime;


    @JsonProperty("order_detailList")
    private List<OrderDetail> orderDetailList;

    @JsonProperty("order_master")
    private OrderMaster orderMaster;

    @JsonProperty("order_master_list")
    private List<OrderMaster> orderMasterList;


    public List<OrderMaster> getOrderMasterList() {
        return orderMasterList;
    }

    public void setOrderMasterList(List<OrderMaster> orderMasterList) {
        this.orderMasterList = orderMasterList;
    }



    public OrderDto(){}

    public OrderDto(ProductInfoStateEnum productInfoStateEnum){
        this.state = productInfoStateEnum.getState();
        this.stateInfo = productInfoStateEnum.getStateInfo();
    }

    public OrderDto(ResultEnum resultEnum){
        this.state = resultEnum.getState();
        this.stateInfo = resultEnum.getMessage();
    }


    public OrderDto(ProductInfoStateEnum productInfoStateEnum,List<OrderMaster> orderMasterList){
        this.state = productInfoStateEnum.getState();
        this.stateInfo = productInfoStateEnum.getStateInfo();
        this.orderMasterList = orderMasterList;
    }


    public OrderDto(ProductInfoStateEnum productInfoStateEnum,String orderId){
        this.state = productInfoStateEnum.getState();
        this.stateInfo = productInfoStateEnum.getStateInfo();
        this.orderId = orderId;
    }


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public String getBuyerOpenid() {
        return buyerOpenid;
    }

    public void setBuyerOpenid(String buyerOpenid) {
        this.buyerOpenid = buyerOpenid;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

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

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public OrderMaster getOrderMaster() {
        return orderMaster;
    }

    public void setOrderMaster(OrderMaster orderMaster) {
        this.orderMaster = orderMaster;
    }
}
