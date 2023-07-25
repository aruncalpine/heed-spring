package com.zno.heed.chatdata;

import java.util.Date;

import com.zno.heed.MysqlEntites.User;

public interface ChatUsersView {

int getId();	
Date getDate();
String getFullName();
String getMobilePhone();

}
