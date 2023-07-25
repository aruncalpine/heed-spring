package com.zno.heed.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.zno.heed.MysqlEntites.User;
import com.zno.heed.user.UserService;
import com.zno.heed.utils.DateCalculator;

/**
 * 
 * This Class may contain confidential, proprietary or legally privileged information. 
 * It is for the use of the ZNO only, and access by anyone else is unauthorized.
 * 
 * Module       : zno_online_class
 * Created Date : 28/07/2020
 * Created By   : TITTU VARGHESE
 */

@Service
public class LoggerService {
	private static final String USERNAME = "Username";
	private static final Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();
	private static final Logger logger = LoggerFactory.getLogger(LoggerService.class);

	/*
	 * @Autowired private UserService userService;
	 */

	@Value("${logging.level.root}")
	private  String level;

	public String logMessage(String message, String userToken, String loggerName) {
		String log="";
		try {
			User user = null;//userService.getUserFromToken(userToken);
			log = gson.toJson(formatLogToJson(loggerName, message, user));
		} catch (Exception e) { 
			logger.error(getErrorLog("logMessage", message));
		}
		return log;
	}
	
	private JsonObject formatLogToJson(String loggerName, String message, User user) {
		JsonObject jsonObject =  new JsonObject();
		try
		{
			jsonObject.add("level", gson.toJsonTree(level));
			jsonObject.add("class", gson.toJsonTree(this.getClass().getName()));
			jsonObject.add("logger_name", gson.toJsonTree(loggerName));
			jsonObject.add("timestamp", gson.toJsonTree(DateCalculator.getFastDateFormat()));
			jsonObject.add("message", gson.toJsonTree(message));
			if(user!=null)
				jsonObject.add(USERNAME, gson.toJsonTree(user.getFullName()+ " ("+user.getUsersRole().getRoleName() +")"));
		}
		catch(Exception e)	{
			logger.error(getErrorLog("formatLogToJson",message));
		}
		return jsonObject;
	}
	
	private String getErrorLog(String methodName, String logName) {
		return "{ \"message\":\"LogService : Error in creating json logs\", \"method name\":\"" + methodName + "\", \"logName\":" + logName + "\" }";
	}
}	