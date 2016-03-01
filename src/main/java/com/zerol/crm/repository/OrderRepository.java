package com.zerol.crm.repository;

import com.zerol.crm.common.SearchCriteria;
import com.zerol.crm.entry.OrderInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderRepository {

    public OrderInfo save(OrderInfo orderInfo);
    public void deleteOrderInfoByID(long orderID);
    public OrderInfo getOrderInfoByID(long orderID);
    public OrderInfo getOrderInfoByExpressNO(String expressNO);

    public List<OrderInfo> getOrderListByFromDtToDt(String fromDate, String toDate);

//    List<OrderInfo> getOrderListBySearchCriteria(SearchCriteria searchCriteria);//tmp
    void update(OrderInfo orderInfo);

    /***** for spring ***********/
    Page<OrderInfo> getOrderPageListBySearchCriteria(SearchCriteria searchCriteria, Pageable page);

    /****** for page tag **********/
    List<OrderInfo> getOrderListBySearchCriteria(SearchCriteria searchCriteria, Map<String, Object> pageMap);
}