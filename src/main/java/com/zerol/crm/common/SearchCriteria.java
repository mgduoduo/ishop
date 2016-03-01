package com.zerol.crm.common;

/**
 * Created by gaoqiang on 1/2/2015.
 */
public class SearchCriteria {

    private String expressNO;
    private String customerName;
    private String customerContactType;
    private String customerContactNO;
    private String productNO;
    private String productName;
    private String payType;
    private String shopingDateFromStr;
    private String shopingDateToStr;
    private String expressStatus;
    private String orderBy;

    public String getExpressNO() {
        return expressNO;
    }

    public void setExpressNO(String expressNO) {
        this.expressNO = expressNO;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContactType() {
        return customerContactType;
    }

    public void setCustomerContactType(String customerContactType) {
        this.customerContactType = customerContactType;
    }

    public String getCustomerContactNO() {
        return customerContactNO;
    }

    public void setCustomerContactNO(String customerContactNO) {
        this.customerContactNO = customerContactNO;
    }

    public String getProductNO() {
        return productNO;
    }

    public void setProductNO(String productNO) {
        this.productNO = productNO;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getShopingDateFromStr() {
        return shopingDateFromStr;
    }

    public void setShopingDateFromStr(String shopingDateFromStr) {
        this.shopingDateFromStr = shopingDateFromStr;
    }

    public String getShopingDateToStr() {
        return shopingDateToStr;
    }

    public void setShopingDateToStr(String shopingDateToStr) {
        this.shopingDateToStr = shopingDateToStr;
    }

    public String getExpressStatus() {
        return expressStatus;
    }

    public void setExpressStatus(String expressStatus) {
        this.expressStatus = expressStatus;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
