package com.zerol.crm.entry.common;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TB_USER")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "USER_NAME")
	private String username;

	@Column(name = "PASS_WORD")
	private String password;

	@Column(name = "EXPIRY_IND")
	private String expiryIndicator;

	@Column(name = "REGISTER_DATE")
	private Date registerDate;

	@Column(name = "LS_LOGIN_DT")
	private Date lastLoginDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getExpiryIndicator() {
		return expiryIndicator;
	}

	public void setExpiryIndicator(String expiryIndicator) {
		this.expiryIndicator = expiryIndicator;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
}
