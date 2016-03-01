package com.zerol.crm.repository;

import com.zerol.crm.entry.common.User;
import org.apache.commons.lang.StringUtils;
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
public class UserRepositoryImpl implements UserRepository {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public User findUserByUsernamePassword(String username, String password) {
        List<User> userList = getUserListByUsernamePassword(username, password);
        if (userList != null && !userList.isEmpty()) {
            return userList.get(0);
        }
        return null;
    }

    @Override
    public List<User> findUserByUsernamePassword(String username) {
        return getUserListByUsernamePassword(username, null);
    }

    private List<User> getUserListByUsernamePassword(String username, String password) {
        String sql = null;
        Query query = null;
        if (StringUtils.isEmpty(password)) {
            sql = "select * from tb_user where user_name=?1";
            query = entityManager.createNativeQuery(sql, User.class)
                    .setParameter(1, username);
        } else {
            sql = "select * from tb_user where user_name=?1 and pass_word=?2";
            query = entityManager.createNativeQuery(sql, User.class)
                    .setParameter(1, username)
                    .setParameter(2, password);
        }
        if (query.getResultList().isEmpty()) {
            return null;
        }
        return (List<User>) query.getResultList();
    }

    @Override
    public List<User> findAllUserList() {
        String sql = "select * from tb_user";
        Query query = entityManager.createNativeQuery(sql, User.class);
        if (query.getResultList().isEmpty()) {
            return null;
        }
        return (List<User>) query.getResultList();
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        entityManager.persist(user);
        entityManager.flush();
        return user; //with id
    }

    @Override
    @Transactional
    public boolean expireUserByUsername(String username) {
        String sql = "update tb_user set EXPIRY_IND = 'N' where user_name=?1";
        int num = entityManager.createNativeQuery(sql).executeUpdate();
        return num > 0;
    }

    @Override
    @Transactional
    public boolean updateUserLastLoginDate(User user1) {
        String sql = "update tb_user set ls_login_dt=now() where username=?1 and password=?2";
        int num = entityManager.createNativeQuery(sql)
                .setParameter(1, user1.getUsername())
                .setParameter(2, user1.getPassword())
                .executeUpdate();
        return num > 0;
    }
}
