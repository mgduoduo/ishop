package com.zerol.crm.service;

import com.zerol.crm.common.SearchCriteria;
import com.zerol.crm.common.util.DateUtil;
import com.zerol.crm.entry.OrderInfo;
import com.zerol.crm.entry.TxnRel;
import com.zerol.crm.repository.OrderRepository;
import com.zerol.crm.service.web.OrderService;
import com.zerol.crm.service.web.TxnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    private static final int PAGE_SIZE = 50;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TxnService txnService;

    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void setTxnService(TxnService txnService) {
        this.txnService = txnService;
    }

    @Override
    @Transactional
    public OrderInfo save(OrderInfo orderInfo) {
        return orderRepository.save(orderInfo);
    }

    @Override
    @Transactional
    public void update(OrderInfo orderInfo) {

        /******* method-1 : delete all firstly, then save all  *********/
        // ******* if delete product, re-calculate the total count of backupCount. ********
        // cannot do like this,because: javax.persistence.EntityExistsException: A different object with the same identifier value was already associated with the session ,

		/*txnService.deleteTxnListByExpressNO(orderInfo.getExpressNO());

		for(TxnRel txnRel : txnList){
			if(CommonConstant.COMMON_BOOLEAN_N.equals(txnRel.getDeleteIndicator())){
				txnService.save(txnRel);
			}
		}
		orderRepository.update(orderInfo);*/


        /** ******************
         * When OneToMany CascadeType include persist level, below is mandatory:
         *
         * it's important to leave it as empty (or any other better way please share),
         * when the child-object is a collection and been updated.
         * In this case, before update the parent-object,
         * we should make sure the hashcode is the same, or leave the child-collection as empty,
         * as maybe JPA/Hibernate will compare the hashcode.
         * if equals, parent object can be updated.
         * else, cant updated, and will throw Exception.
         *
         ** ******************/
        //update orderInfo
//		orderInfo.setTxnList(null);// set the child-collection object as empty
//		orderRepository.update(orderInfo);//update the parent object


        /******* method-2 : if refProduct exists, do update; else do insert.  *********/
        List<TxnRel> txnListFromUI = orderInfo.getTxnList(); // get the data from UI, and save them by local.

        List<String> existedProdNOList = new ArrayList<String>();
        Map<String, TxnRel> tempMap = new HashMap<String, TxnRel>();
        List<TxnRel> originalTxnListFromDB = txnService.getTxnListByExpressNO(orderInfo.getExpressNO());

        if (originalTxnListFromDB != null && originalTxnListFromDB.size() > 0) {
            for (TxnRel txnRel : originalTxnListFromDB) {
                existedProdNOList.add(txnRel.getRefProdNO());
                tempMap.put(txnRel.getRefProdNO(), txnRel);
            }
        }

        if (txnListFromUI != null && !txnListFromUI.isEmpty()) {
            LOGGER.debug("It should re-calculate the total count of backupCount once update order info .");
            for (TxnRel rel1 : txnListFromUI) {
                if (originalTxnListFromDB != null && !originalTxnListFromDB.isEmpty()) {
                    if (existedProdNOList.contains(rel1.getRefProdNO())) {
                        LOGGER.debug("Do update using UI data if the record exists in DB, refProdNo=" + rel1.getRefProdNO());
                        // Step-1: if exists, update by data from UI .
                        TxnRel rel2 = tempMap.get(rel1.getRefProdNO());
                        rel2.setRefProdNO(rel1.getRefProdNO());
                        rel2.setPurchaseCount(rel1.getPurchaseCount());
                        rel2.setDeleteIndicator(rel1.getDeleteIndicator());
                        rel2.setRemark(rel1.getRemark());
                        txnService.updateTxn(rel2);
                    } else {
                        LOGGER.debug("Do insert if the record not exists in DB, which means a new record! refProdNo=" + rel1.getRefProdNO());
                        // Step-2: if not exists, insert new records.
                        txnService.save(rel1);
                    }

                } else {
                    LOGGER.debug("Do insert if no record found in DB, refProdNo=" + rel1.getRefProdNO());
                    ;
                    txnService.save(rel1);
                }
            }
        } else {

            if (!existedProdNOList.isEmpty()) {
                LOGGER.debug("Do delete all the related records if no record passed from UI!");
                txnService.deleteTxnListByExpressNO(orderInfo.getExpressNO());//TODO pending double confirm
            }
        }

        orderRepository.update(orderInfo);//S-3 update the parent object
    }

    @Override
    @Transactional
    public void deleteOrderInfoByID(long orderID) {
        orderRepository.deleteOrderInfoByID(orderID);
    }

    @Override
    public OrderInfo getOrderInfoByID(long orderID) {
        return orderRepository.getOrderInfoByID(orderID);
    }

    @Override
    public OrderInfo getOrderInfoByExpressNO(String expressNO) {
        return orderRepository.getOrderInfoByExpressNO(expressNO);
    }

    @Override
    public List<OrderInfo> getOrderListByFromDtToDt(String fromDate, String toDate) {
        return this.getAllOrderListByFromDtToDt(fromDate, toDate);
    }

    @Override
    public List<OrderInfo> getAllOrderList() {
        return this.getAllOrderListByFromDtToDt(null, null);
    }

    @Override
    public List<OrderInfo> getAllOrderList(Map<String, Object> pageMap) {

        return this.getOrderListBySearchCriteria(null, pageMap);
    }

    @Override
    public List<OrderInfo> getOrderListBySearchCriteria(SearchCriteria searchCriteria) {
        if (searchCriteria == null) {
            return null;
        }
        return this.getOrderListBySearchCriteria(searchCriteria, null);
    }

    @Override
    public Integer getCountOfOrderListBySearchCriteria(SearchCriteria searchCriteria) {
        List result = this.getOrderListBySearchCriteria(searchCriteria);
        return (result == null || result.isEmpty()) ? 0 : result.size();
    }

    @Override
    public List<OrderInfo> getOrderListBySearchCriteria(SearchCriteria searchCriteria, Map<String, Object> pageMap) {
        return orderRepository.getOrderListBySearchCriteria(searchCriteria, pageMap);
    }

    @Override
    public Page<OrderInfo> getOrderPageListBySearchCriteria(SearchCriteria searchCriteria, Pageable page) {

        int pageNumber = page.getPageNumber();
        int pageSize = page.getPageSize();
        PageRequest request =
                new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "startTime");
        return orderRepository.getOrderPageListBySearchCriteria(searchCriteria, page);
    }

    private List<OrderInfo> getAllOrderListByFromDtToDt(String fromDate, String toDate) {
        if (fromDate != null && !"".equals(fromDate) && !"".equals(fromDate.trim())) {
            if (!DateUtil.isValidFormat(DateUtil.FORMAT_DATE_YYYY_MM_DD, fromDate)) {
                return null;
            }
        }
        if (toDate != null && !"".equals(toDate) && !"".equals(toDate.trim())) {
            if (DateUtil.isValidFormat(DateUtil.FORMAT_DATE_YYYY_MM_DD, toDate)) {
                return null;
            }
        }

        return orderRepository.getOrderListByFromDtToDt(fromDate, toDate);
    }

}