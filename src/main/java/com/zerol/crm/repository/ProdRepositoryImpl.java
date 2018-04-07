package com.zerol.crm.repository;

import com.zerol.crm.entry.Product;
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
@Transactional
public class ProdRepositoryImpl implements ProdRepository {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProdRepositoryImpl.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    @Transactional
    public Product save(Product entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity; //with id
    }

    @Override
    public Product findOne(Long prodID) {
        return entityManager.find(Product.class, prodID);
    }

    @Override
    public List<Product> findAll() {
        String sql = "select * from tb_product";
        Query query = entityManager.createNativeQuery(sql, Product.class);
        if (query.getResultList().isEmpty()) {
            return null;
        }
        return (List<Product>) query.getResultList();
    }

    @Override
    @Transactional
    public void deleteProductByID(long prodID) {
        entityManager.createNativeQuery("delete from tb_product where prod_id=" + prodID).executeUpdate();
    }

    @Override
    @Transactional
    public void updateProduct(Product product) {
        LOGGER.debug("Merge command can move the object from 'detached' to 'managed', then JPA will update it into DB.");
        entityManager.merge(product);
//        entityManager.flush();
//        entityManager.refresh(refreshObject);
//        entityManager.setFlushMode(FlushModeType.COMMIT);
//        entityManager.flush();
    }

    @Override
    public Product getProductByProdNO(String prodNO) {
        String sql = "SELECT p.* FROM TB_PRODUCT p WHERE p.PROD_NO=?1";
        Query query = entityManager.createNativeQuery(sql, Product.class)
                .setParameter(1, prodNO);

        if (query.getResultList().isEmpty()) {
            return null;
        }
        return (Product) query.getSingleResult();
    }

}
