package com.zerol.crm.repository;

import com.zerol.crm.entry.common.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {
   User findUserByUsernamePassword(String username, String password);
   List<User> findUserByUsernamePassword(String username);
   List<User> findAllUserList();
   User saveUser(User user);
   boolean expireUserByUsername(String username);

   boolean updateUserLastLoginDate(User user1);
}