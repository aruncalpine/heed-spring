package com.zno.heed.chatdata;

import java.sql.Date;

import com.zno.heed.MysqlEntites.User;

public interface ChatUsersView {

Date getDate();
String getFullName();
String getMobilePhone();

}
