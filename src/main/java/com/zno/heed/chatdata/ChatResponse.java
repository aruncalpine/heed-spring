package com.zno.heed.chatdata;

import java.sql.Date;

public class ChatResponse {
Date date;
String moblie;
String name;

public ChatResponse(Date date, String moblie, String name) {
	super();
	this.date = date;
	this.moblie = moblie;
	this.name = name;
}

public Date getDate() {
	return date;
}

public void setDate(Date date) {
	this.date = date;
}

public String getMoblie() {
	return moblie;
}

public void setMoblie(String moblie) {
	this.moblie = moblie;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}



}