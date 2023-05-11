package com.zno.heed.chatdata;

import java.sql.Date;

import javax.xml.crypto.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.metamodel.StaticMetamodel;

@Entity
public class Chat_History {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
int id;
String source ;
String destination;
Date date;

public String getSource() {
	return source;
}
public void setSource(String source) {
	this.source = source;
}
public String getDestination() {
	return destination;
}
public void setDestination(String destination) {
	this.destination = destination;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}


}
