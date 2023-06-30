package com.zno.heed.MysqlEntites;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class ChatUsers {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
Long id;

@OneToOne
@JoinColumn
User srcUser;

@OneToOne
@JoinColumn
User destUser;

Date date;

Boolean isDeleted;



public Date getDate() {
	return date;
}

public void setDate(Date date) {
	this.date = date;
}

public Boolean getIsDeleted() {
	return isDeleted;
}

public void setIsDeleted(Boolean isDeleted) {
	this.isDeleted = isDeleted;
}

public User getSrcUser() {
	return srcUser;
}

public void setSrcUser(User srcUser) {
	this.srcUser = srcUser;
}

public User getDestUser() {
	return destUser;
}

public void setDestUser(User destUser) {
	this.destUser = destUser;
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

}
