package com.zno.heed.MysqlEntites;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;

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
@Data
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
	private String profileImagePath;
	
	public User() {}	
	
	public User(String firstName, String lastName, String email, Company company, UsersRole usersRole ) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.fullName = firstName+" "+firstName;
  	    this.company = company;
		this.usersRole = usersRole;
	}
	

}
