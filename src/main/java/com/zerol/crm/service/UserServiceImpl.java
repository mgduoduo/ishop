package com.zerol.crm.service;

import com.zerol.crm.common.constant.CommonConstant;
import com.zerol.crm.common.util.DateUtil;
import com.zerol.crm.entry.common.User;
import com.zerol.crm.repository.UserRepository;
import com.zerol.crm.service.web.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserByUsernamePassword(String username, String password) {
        return userRepository.findUserByUsernamePassword(username, password);
    }

    @Override
    public List<User> findUserByUsername(String username) {
        return userRepository.findUserByUsernamePassword(username);
    }

    @Override
    public List<User> findAllUserList() {
        return userRepository.findAllUserList();
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        user.setExpiryIndicator(CommonConstant.COMMON_BOOLEAN_N);
        user.setRegisterDate(DateUtil.getDate());
        return userRepository.saveUser(user);
    }

    @Override
    @Transactional
    public User updateUserLastLoginDate(User user) {

        if (user == null || StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            return null;
        }
        User user1 = this.findUserByUsernamePassword(user.getUsername(), user.getPassword());
        if (user1 == null) {
            return null;
        }
        user1.setLastLoginDate(DateUtil.getDate());
        userRepository.updateUserLastLoginDate(user1);

        return user1;
    }

    /**
     * @param user
     * @return 实际上没有改密码，只是把以前的用户全部注销掉，然后插入一个新的active的user。
     */
    @Override
    @Transactional
    public boolean changeUserPassword(User user) {
        User u1 = this.findUserByUsernamePassword(user.getUsername(), user.getPassword());
        if (u1 != null) {
            if (CommonConstant.COMMON_BOOLEAN_N.equals(u1.getExpiryIndicator())) {
                // 存在这样账号和密码都相同的用户，且仍是active的， 故可以不做任何改动，即直接使用存在的账户，但仍视之为修改成功
                return true;
            }
        }
        //反之， 需注销掉所有username相同的账户（逻辑删除），再重新插入一条新用户的记录
        //1
        this.expireUserByUsername(user.getUsername());
        //2
        user.setExpiryIndicator(CommonConstant.COMMON_BOOLEAN_Y);
        user.setLastLoginDate(null);
        user.setRegisterDate(DateUtil.getDate());
        User u2 = this.saveUser(user);

        return u2 != null;
    }

    @Override
    @Transactional
    public boolean expireUserByUsername(String username) {
        return userRepository.expireUserByUsername(username);
    }

}