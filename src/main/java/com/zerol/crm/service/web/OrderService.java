package com.zerol.crm.service.web;

import com.zerol.crm.common.SearchCriteria;
import com.zerol.crm.entry.OrderInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface OrderService {

	public OrderInfo save(OrderInfo orderInfo);
	public void update(OrderInfo orderInfo);
	public void deleteOrderInfoByID(long orderID);
	public OrderInfo getOrderInfoByID(long orderID);
	public OrderInfo getOrderInfoByExpressNO(String expressNO);

	/***** for page tag ********/
//	List<OrderInfo> getOrderListBySearchCriteria(SearchCriteria searchCriteria);
	public Integer getCountOfOrderListBySearchCriteria(SearchCriteria searchCriteria);

	public List<OrderInfo> getAllOrderList(Map<String, Object> pageMap);

	public List<OrderInfo> getOrderListBySearchCriteria(SearchCriteria searchCriteria, Map<String, Object> pageMap);
	/*************/



	public List<OrderInfo> getOrderListByFromDtToDt(String FromDate, String toDate);//unused.
	public List<OrderInfo> getAllOrderList();//unused.
	public List<OrderInfo> getOrderListBySearchCriteria(SearchCriteria searchCriteria);//unused.

	/**** for spring pagination*********/
	Page<OrderInfo> getOrderPageListBySearchCriteria(SearchCriteria searchCriteria, Pageable page);
	/*************/
}

