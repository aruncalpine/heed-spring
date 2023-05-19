package com.zno.heed.chatdata;

import java.sql.Date;
import javax.xml.crypto.Data;
import com.zno.heed.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.metamodel.StaticMetamodel;

@Entity
public class ChatUsers {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
int id;
@OneToOne
@JoinColumn
User srcUser;

@OneToOne
@JoinColumn
User destUser;

Date date;

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

public Date getDate() {
	return date;
}

public void setDate(Date date) {
	this.date = date;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

}
