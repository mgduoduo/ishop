package com.zerol.crm.service;

import com.zerol.crm.common.constant.CommonConstant;
import com.zerol.crm.entry.TxnRel;
import com.zerol.crm.repository.TxnRepository;
import com.zerol.crm.service.web.ProdService;
import com.zerol.crm.service.web.TxnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class TxnServiceImpl implements TxnService {

    private final static Logger LOGGER = LoggerFactory.getLogger(TxnServiceImpl.class);

    @Autowired
    private TxnRepository txnRepository;

    @Autowired
    private ProdService prodService;

    public void setTxnRepository(TxnRepository txnRepository) {
        this.txnRepository = txnRepository;
    }

    public void setProdService(ProdService prodService) {
        this.prodService = prodService;
    }

    @Override
    @Transactional
    public void save(TxnRel txnRel) {
        if (txnRel != null) {
            //1, re-calculate the new total backup count of the product.
            prodService.updateTotalBackupCountOfProduct(txnRel.getRefProdNO(), txnRel.getPurchaseCount(), 0);
        }
        txnRepository.save(txnRel);
    }

    @Override
    @Transactional
    public void deleteTxnListByExpressNO(String expressNO) {
        List<TxnRel> txnRelList = this.getTxnListByExpressNO(expressNO);
        if (txnRelList != null && !txnRelList.isEmpty()) {
            for (TxnRel txnRel : txnRelList) {
                //1, re-calculate the new total backup count of the product.
                prodService.updateTotalBackupCountOfProduct(txnRel.getRefProdNO(), 0, txnRel.getPurchaseCount());
            }
        }
        txnRepository.deleteTxnByExpressNO(expressNO);
    }

    @Override
    @Transactional
    public void saveTxnList(List<TxnRel> txnRelList) {
        if (txnRelList != null && !txnRelList.isEmpty()) {
            for (TxnRel txnRel : txnRelList) {
                //1, re-calculate the new total backup count of the product.
                prodService.updateTotalBackupCountOfProduct(txnRel.getRefProdNO(), txnRel.getPurchaseCount(), 0);

                //2. save the txn
                this.save(txnRel);
            }
        }
    }

    @Override
    @Transactional
    public void updateTxn(TxnRel txnRel) {
        if (txnRel != null) {
            //1, re-calculate the new total backup count of the product.
            TxnRel txnRelFromDB = this.getTxnByExpressNOAndProdNO(txnRel.getRefExpressNO(), txnRel.getRefProdNO());
            if(txnRelFromDB!=null){
                prodService.updateTotalBackupCountOfProduct(txnRel.getRefProdNO(), txnRel.getPurchaseCount(), txnRelFromDB.getPurchaseCount());
            }

            //update
            txnRepository.updateTxn(txnRel);
        }
    }

    @Override
    public TxnRel getTxnByID(long txnID) {
        return txnRepository.getTxnByID(txnID);
    }

    @Override
    public TxnRel getTxnByExpressNOAndProdNO(String expressNO, String prodNO) {
        return txnRepository.getTxnByExpressNOAndProdNO(expressNO, prodNO);
    }

    @Override
    public boolean isExistTxnByExpressNOAndProdNO(String expressNO, String prodNO) {
        return this.getTxnByExpressNOAndProdNO(expressNO, prodNO) != null;
    }


    /**
     * only include active records: del_ind='N'
     */
    @Override
    public List<TxnRel> getTxnListByExpressNO(String expressNO) {
        return getTxnListByExpressNO(expressNO, CommonConstant.COMMON_BOOLEAN_N);
    }

    /**
     * include all: del_ind='Y' or 'N'
     */
    @Override
    public List<TxnRel> getAllTxnListByExpressNO(String expressNO) {
        return getTxnListByExpressNO(expressNO, null);
    }


    private List<TxnRel> getTxnListByExpressNO(String expressNO, String deleteIndicator) {
        List<TxnRel> txnRelList = txnRepository.getTxnListByExpressNO(expressNO, deleteIndicator);
        return txnRelList;
    }
}