package com.zno.heed.utils;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.web.util.HtmlUtils;

/**
 * 
 * This Class may contain confidential, proprietary or legally privileged information. 
 * It is for the use of the ZNO only, and access by anyone else is unauthorized.
 * 
 * Module       : zno_online_class
 * Created Date : 28/07/2020
 * Created By   : TITTU VARGHESE
 */

public class ValidationUtil {

	private static final Pattern pattern = Pattern.compile("^[a-zA-Z\\s'-]*$");
	
	public static boolean isNull(Object obj) {
		return obj == null;
	}

	public static boolean isNullOrEmpty(Collection<?> value) {
		return (value == null || value.isEmpty());
	}
	public static boolean isInvalidName(String str) {
		if(!isNullOrEmpty(str))	{	
		Matcher mtch = pattern.matcher(str.toLowerCase());
		if(!mtch.matches())
			return true;
		}
		return false;
	}
	
	public static boolean isNullOrEmpty(String value) {
		return (isNull(value));// || StringUtils.isEmpty(value));
	}	

	public static boolean isInvalidString(String str) {
		return !isNullOrEmpty(str) || isHtml(str) && (!StringUtils.isAlpha(str));
	}

	public static boolean isInvalidNumber(String str) {
// $R 05/03/23 pattern, matcher and matcher.find is added		    
		 Pattern pattern = Pattern.compile("\\d{10}");
		    Matcher matcher = pattern.matcher(str);
		return !isNullOrEmpty(str) && !StringUtils.isNumeric(str)&&matcher.find();
	}

	public static boolean isInvalidEmailAddress(String email) {	
		if(!isNullOrEmpty(email))
			return !EmailValidator.getInstance().isValid(email);
// $R 15/03/23 return changed to true			
		return true;
	}
	public static boolean isInvalidEmailAddress1(String email) {	
		if(!isNullOrEmpty(email))
			return !EmailValidator.getInstance().isValid(email);
// $R 15/03/23 return changed to true			
		return true;
	}
// $R 15/03/23 added isInvalidPassword	
	
	public static boolean isInvalidPassword(String password , String confirmPassword) {
		return !password.equals(confirmPassword);
	}
	
// 16/03/23

	
	public static boolean isInvalidDomain(String domain) {
		if(!isNullOrEmpty(domain))
			return !DomainValidator.getInstance().isValid(domain);
		return false;
	}
	
	private static final String INVALID_SPECIAL_CHARACTERS = "`~!#$%^&*()+={}[]:;'<>?/|";
	public static boolean containsInvalidSpecialCharacters(String str) {
		if(!isNullOrEmpty(str))
		{
			String str2[]= str.split("");
			for (int i=0;i<str2.length;i++)
			{
				if(INVALID_SPECIAL_CHARACTERS.contains(str2[i]))
					return true;
			}
		}
		return false;
	}

	public static boolean isHtml(String checkStr) {
        if (checkStr != null && !checkStr.equalsIgnoreCase(""))
            if (!checkStr.equals(HtmlUtils.htmlEscape(checkStr)))
            	return true;
        return false;       
    }

	private static final String[] SQL_KEYWORDS = {"SELECT","UPDATE","INSERT","DELETE","DROP","ALTER","CREATE"};
	public static boolean isQuery(String str) {
		if(!isNullOrEmpty(str))
		{
			str=str.toUpperCase();
			for (String keyword : SQL_KEYWORDS)
			{
				if(str.contains(keyword))
					return true;
			}
		}
		return false;
	}
	
	public static boolean validateUserName(String userName){
		  if(!isNullOrEmpty(userName)) {
			  return !userName.matches("^[\\p{L} .'-]+$");
		  }
		  return false;
	}

	public static boolean validateEmail(String value, String patterns) {
		Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(patterns, Pattern.CASE_INSENSITIVE);
		  if(!isNullOrEmpty(value))
		        return VALID_EMAIL_ADDRESS_REGEX.matcher(value).matches();
		  return false;
	}
}