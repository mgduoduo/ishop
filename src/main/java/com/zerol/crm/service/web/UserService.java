package com.zerol.crm.service.web;

import com.zerol.crm.entry.common.User;

import java.util.List;

public interface UserService {

	public User findUserByUsernamePassword(String username, String password);
	public List<User> findUserByUsername(String username);
	public List<User> findAllUserList();
	public User saveUser(User user);
	public User updateUserLastLoginDate(User user);
	public boolean changeUserPassword(User user);
	public boolean expireUserByUsername(String username);

}

