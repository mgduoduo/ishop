package com.zerol.crm.repository;

import com.zerol.crm.entry.TxnRel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Repository

public class TxnRepositoryImpl implements TxnRepository {

    private final static Logger LOGGER = LoggerFactory.getLogger(TxnRepositoryImpl.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    @Transactional
    public void save(TxnRel txn) {
        entityManager.persist(txn);
        entityManager.flush();
    }

    @Override
    @Transactional
    public void deleteTxnByID(long txnID) {
//		entityManager.createNativeQuery("DELETE FROM TB_TXN_REL P WHERE P.TXN_ID=1?")
//				.setParameter(1, txnID)
//				.executeUpdate();
    }

    @Override
    @Transactional
    public void deleteTxnByExpressNO(String expressNO) {
        String sql = "DELETE FROM TB_TXN_REL WHERE EXPRESS_NO=?1";
        entityManager.createNativeQuery(sql).setParameter(1, expressNO).executeUpdate();
    }

    @Override
    @Transactional
    public void updateTxn(TxnRel txn) {
        LOGGER.debug("no need do anything just merge, jpa will do the update.");

        entityManager.merge(txn);
//        entityManager.flush();
//        entityManager.refresh(refreshObject);
//        entityManager.setFlushMode(FlushModeType.COMMIT);
//        entityManager.flush();
    }

    @Override
    public TxnRel getTxnByID(long txnID) {
        return entityManager.find(TxnRel.class, txnID);
    }

    @Override
    public List<TxnRel> getTxnListByExpressNO(String expressNO, String delIndicator) {
        String sql = null;
        Query query = null;
        if (delIndicator == null || "".equals(delIndicator)) {
            sql = "SELECT * FROM TB_TXN_REL P WHERE P.EXPRESS_NO=?1";
            query = entityManager.createNativeQuery(sql, TxnRel.class)
                    .setParameter(1, expressNO);
        } else {
            sql = "SELECT * FROM TB_TXN_REL P WHERE P.EXPRESS_NO=?1 AND P.DEL_IND=?2";
            query = entityManager.createNativeQuery(sql, TxnRel.class)
                    .setParameter(1, expressNO)
                    .setParameter(2, delIndicator);
        }
        if (query.getResultList().isEmpty()) {
            return null;
        }
        return (List<TxnRel>) query.getResultList();
    }

    @Override
    public TxnRel getTxnByExpressNOAndProdNO(String expressNO, String prodNO) {
        entityManager.clear();// session
        Query query = entityManager.createNativeQuery("SELECT * FROM TB_TXN_REL R WHERE R.EXPRESS_NO=?1 and R.PROD_NO=?2", TxnRel.class)
                .setParameter(1, expressNO)
                .setParameter(2, prodNO);

        if (query.getResultList().isEmpty()) {
            return null;
        }
        return (TxnRel) query.getResultList().get(0);
    }

}
