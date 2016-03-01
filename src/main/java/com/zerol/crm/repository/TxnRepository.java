package com.zerol.crm.repository;

import com.zerol.crm.entry.TxnRel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TxnRepository {

    public void save(TxnRel txn);
    public void deleteTxnByID(long txnID);
    public void deleteTxnByExpressNO(String expressNO);
    public void updateTxn(TxnRel txn);
    public TxnRel getTxnByID(long txnID);


    public List<TxnRel> getTxnListByExpressNO(String expressNO, String deleteIndicator);

    TxnRel getTxnByExpressNOAndProdNO(String expressNO, String prodNO);
}