package com.zerol.crm.repository;

import com.zerol.crm.entry.common.MyCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

@Repository

public class CodeRepositoryImpl implements CodeRepository {

    private final static Logger LOGGER = LoggerFactory.getLogger(CodeRepositoryImpl.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public MyCode findCodeByCodeTypIDAndCodeID(String codeTypeID, String codeID) {
        String sql = "SELECT * FROM TB_CODE_INT WHERE CODETYPE_ID=?1 AND CODE_ID=?2";
        Query query = entityManager.createNativeQuery(sql, MyCode.class)
                .setParameter(1, codeTypeID)
                .setParameter(2, codeID);

        if (query.getResultList().isEmpty()) {
            return null;
        }
        return (MyCode) query.getResultList().get(0);
    }

    @Override
    public List<MyCode> findCodeListByCodeTypID(String codeTypeID) {
        String sql = "SELECT * FROM TB_CODE_INT WHERE CODETYPE_ID=?1";
        Query query = entityManager.createNativeQuery(sql, MyCode.class)
                .setParameter(1, codeTypeID);

        if (query.getResultList().isEmpty()) {
            return null;
        }
        return (List<MyCode>) query.getResultList();
    }

    @Override
    @Transactional
    public void saveCode(MyCode code) {
        String sql = "INSERT INTO TB_CODE_INT(CODETYPE_ID, CODE_ID, CODE_DESC) VALUES(?1,?2,?3)";
        entityManager.createNativeQuery(sql)
                .setParameter(1, code.getCodeTypeID())
                .setParameter(2, code.getCodeID())
                .setParameter(3, code.getCodeDesc())
                .executeUpdate();
    }

    @Override
    @Transactional
    public void deleteCodeByCodeTypeID(String codeTypeID) {
        String sql = "DELETE FROM TB_CODE_INT WHERE CODETYPE_ID=?1";
        entityManager.createNativeQuery(sql)
                .setParameter(1, codeTypeID)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void deleteCodeByCodeTypeIDAndCodeID(String codeTypeID, String codeID) {
        String sql = "DELETE FROM TB_CODE_INT WHERE CODETYPE_ID=?1 AND CODE_ID=?2";
        entityManager.createNativeQuery(sql)
                .setParameter(1, codeTypeID)
                .setParameter(2, codeID)
                .executeUpdate();
    }
}
