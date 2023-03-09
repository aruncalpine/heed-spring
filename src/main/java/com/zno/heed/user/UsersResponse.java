package com.zno.heed.user;

public class UsersResponse {
	
	private String fullName;
	private String roles;
	private String userToken;
	private String organizationName;
	
	public UsersResponse(String fullName, String roles, String userToken, String organizationName) {
		this.fullName = fullName;
		this.roles = roles;
		this.userToken = userToken;
		this.organizationName = organizationName;
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
}
