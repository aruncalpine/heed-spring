package com.zno.heed.MysqlEntites;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity @Table
public class LoginHistory {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY, generator="native")
	private long id;	

	@OneToOne @JoinColumn
	private User usersId;
	private String ipAddress;
	private Date loginFailed;
	private Date lastModified;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUsersId() {
		return usersId;
	}

	public void setUsersId(User usersId) {
		this.usersId = usersId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Date getLoginFailed() {
		return loginFailed;
	}

	public void setLoginFailed(Date loginFailed) {
		this.loginFailed = loginFailed;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public LoginHistory() {}

	public LoginHistory(User usersId, String ipAddress, Date loginFailed, Date lastModified) {
		super();
		this.usersId = usersId;
		this.ipAddress = ipAddress;
		this.loginFailed = loginFailed;
		this.lastModified = lastModified;
	}
}
