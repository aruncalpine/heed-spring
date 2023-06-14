package com.zno.heed.user;

import java.util.ArrayList;
import java.util.List;

import com.zno.heed.MysqlEntites.AgreementFile;
import com.zno.heed.MysqlEntites.Company;
import com.zno.heed.MysqlEntites.User;
import com.zno.heed.MysqlEntites.UsersRole;
import com.zno.heed.constants.CommonConstant.AgreementResponseCode;

public class UserResponse {
	private long id;
	private String firstname;
	private String lastname;
	private List<UsersRole> roles = new ArrayList<UsersRole>();
	private String userToken;
	private String mobilephone;
	private String workphone;
	private String title;
	private String email;
	private Company companyId;	
	private String companyName;	
	private Boolean accountlocked;	
	private Boolean accountexpired;
	private Boolean passwordexpired;	
	private Boolean enabled;
	private boolean agreement;
	private List<AgreementFile> agreementFile;
	private Long daysLeftForPasswordExpiry;
	private Boolean loadUserShow;
	private Boolean hasOldAgreement;
	private Boolean childLock;
	private boolean hasUserLicenceType;
	private boolean supportAllowed;
	private boolean hideLicenceLimit;
	private String imageFileName;
		
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public List<UsersRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UsersRole> roles) {
		this.roles = roles;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getWorkphone() {
		return workphone;
	}

	public void setWorkphone(String workphone) {
		this.workphone = workphone;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Company getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Boolean getAccountlocked() {
		return accountlocked;
	}

	public void setAccountlocked(Boolean accountlocked) {
		this.accountlocked = accountlocked;
	}

	public Boolean getAccountexpired() {
		return accountexpired;
	}

	public void setAccountexpired(Boolean accountexpired) {
		this.accountexpired = accountexpired;
	}

	public Boolean getPasswordexpired() {
		return passwordexpired;
	}

	public void setPasswordexpired(Boolean passwordexpired) {
		this.passwordexpired = passwordexpired;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAgreement() {
		return agreement;
	}

	public void setAgreement(boolean agreement) {
		this.agreement = agreement;
	}

	public List<AgreementFile> getAgreementFile() {
		return agreementFile;
	}

	public void setAgreementFile(List<AgreementFile> agreementFile) {
		this.agreementFile = agreementFile;
	}

	public Long getDaysLeftForPasswordExpiry() {
		return daysLeftForPasswordExpiry;
	}

	public void setDaysLeftForPasswordExpiry(Long daysLeftForPasswordExpiry) {
		this.daysLeftForPasswordExpiry = daysLeftForPasswordExpiry;
	}

	public Boolean getLoadUserShow() {
		return loadUserShow;
	}

	public void setLoadUserShow(Boolean loadUserShow) {
		this.loadUserShow = loadUserShow;
	}

	public Boolean getHasOldAgreement() {
		return hasOldAgreement;
	}

	public void setHasOldAgreement(Boolean hasOldAgreement) {
		this.hasOldAgreement = hasOldAgreement;
	}

	public Boolean getChildLock() {
		return childLock;
	}

	public void setChildLock(Boolean childLock) {
		this.childLock = childLock;
	}

	public boolean isHasUserLicenceType() {
		return hasUserLicenceType;
	}

	public void setHasUserLicenceType(boolean hasUserLicenceType) {
		this.hasUserLicenceType = hasUserLicenceType;
	}

	public boolean isSupportAllowed() {
		return supportAllowed;
	}

	public void setSupportAllowed(boolean supportAllowed) {
		this.supportAllowed = supportAllowed;
	}

	public boolean isHideLicenceLimit() {
		return hideLicenceLimit;
	}

	public void setHideLicenceLimit(boolean hideLicenceLimit) {
		this.hideLicenceLimit = hideLicenceLimit;
	}

	public String getImageFileName() {
		return imageFileName;
	}
	
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public UserResponse(User user, int agreement, List<AgreementFile> agreementFile,Boolean loadUserShow, Boolean isASponsor, String imageFileUrl) {
		this.firstname = user.getFirstName();
		this.lastname = user.getLastName();
		this.roles.add(user.getUsersRole());
		this.userToken = user.getUserToken();
		this.mobilephone=user.getMobilePhone();

		if(agreement == AgreementResponseCode.AGREEMENT_SUCCESS) this.agreement = true;
		else  this.agreement = false;

		if(agreement == AgreementResponseCode.AGREEMENT_OLD) hasOldAgreement = true;
		else hasOldAgreement = false;

		this.agreementFile = agreementFile;
		//this.daysLeftForPasswordExpiry=user.getDaysLeftForPasswordExpiry();
		this.loadUserShow = loadUserShow;
		this.imageFileName=imageFileUrl;
	}
}