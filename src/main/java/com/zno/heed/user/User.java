package com.zno.heed.user;

import java.util.Date;

import com.zno.heed.Company.Company;
import com.zno.heed.chatdata.ChatUsers;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 * 
 * This Class may contain confidential, proprietary or legally privileged information. 
 * It is for the use of the ZNO only, and access by anyone else is unauthorized.
 * 
 * Module       : zno_online_class
 * Created Date : 28/07/2020
 * Created By   : TITTU VARGHESE
 */

@Entity @Table
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="native")
	private long id;
	
	@Version
	private long version;
	
	@OneToOne @JoinColumn
	private UsersRole usersRole;
	
	@OneToOne @JoinColumn
	private Company company;

	private String firstName;
	private String lastName;
	private String mobilePhone;
	private String fullName;
	private String userName;	
	private String email;	
	private String password;
// $R `15/03/23 added confirmPassword
	private String confirmPassword;
	private String workPhone;	
	private Boolean accountExpired = false;
	private Boolean accountLocked = false;
	private Boolean enabled = true;
	private Boolean passwordExpired = false;	
	private String userToken;
	private Date tokenTime;	
	private int attempts;
	private Date lastModified;
	private Long loginUcId;
	private Date dateCreated;
	private Date passwordUpdateDate;
	private Date lastLogin;		
	
	public User() {}	
	
	public User(String firstName, String lastName, String email, Company company, UsersRole usersRole ) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.fullName = firstName+" "+firstName;
  	    this.company = company;
		this.usersRole = usersRole;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	public UsersRole getUsersRole() {
		return usersRole;
	}
	public void setUsersRole(UsersRole usersRole) {
		this.usersRole = usersRole;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getWorkPhone() {
		return workPhone;
	}
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}
	public Boolean getAccountExpired() {
		return accountExpired;
	}
	public void setAccountExpired(Boolean accountExpired) {
		this.accountExpired = accountExpired;
	}
	public Boolean getAccountLocked() {
		return accountLocked;
	}
	public void setAccountLocked(Boolean accountLocked) {
		this.accountLocked = accountLocked;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Boolean getPasswordExpired() {
		return passwordExpired;
	}
	public void setPasswordExpired(Boolean passwordExpired) {
		this.passwordExpired = passwordExpired;
	}
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	public Date getTokenTime() {
		return tokenTime;
	}
	public void setTokenTime(Date tokenTime) {
		this.tokenTime = tokenTime;
	}
	public int getAttempts() {
		return attempts;
	}
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public Long getLoginUcId() {
		return loginUcId;
	}
	public void setLoginUcId(Long loginUcId) {
		this.loginUcId = loginUcId;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getPasswordUpdateDate() {
		return passwordUpdateDate;
	}
	public void setPasswordUpdateDate(Date passwordUpdateDate) {
		this.passwordUpdateDate = passwordUpdateDate;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", version=" + version + ", usersRole=" + usersRole + ", company=" + company
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", mobilePhone=" + mobilePhone + ", fullName="
				+ fullName + ", userName=" + userName + ", email=" + email + ", password=" + password + ", workPhone="
				+ workPhone + ", accountExpired=" + accountExpired + ", accountLocked=" + accountLocked + ", enabled="
				+ enabled + ", passwordExpired=" + passwordExpired + ", userToken=" + userToken + ", tokenTime="
				+ tokenTime + ", attempts=" + attempts + ", lastModified=" + lastModified + ", loginUcId=" + loginUcId
				+ ", dateCreated=" + dateCreated + ", passwordUpdateDate=" + passwordUpdateDate + ", lastLogin="
				+ lastLogin + "]";
	}
}
