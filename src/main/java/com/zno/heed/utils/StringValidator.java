package com.zno.heed.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

import com.zno.heed.constants.Delimiters;

/**
 * 
 * This Class may contain confidential, proprietary or legally privileged information. 
 * It is for the use of the ZNO only, and access by anyone else is unauthorized.
 * 
 * Module       : zno_online_class
 * Created Date : 28/07/2020
 * Created By   : TITTU VARGHESE
 */

public class StringValidator {

	public static String sqlEsczpe(String inputString){
		String[] metaCharacters = {Delimiters.QUOTE};
		for (int i = 0 ; i < metaCharacters.length ; i++){
			if(inputString.contains(metaCharacters[i]))
				inputString = inputString.replace(metaCharacters[i],"\\"+metaCharacters[i]);
		}
		inputString = inputString.replace("\r\n","+ CHAR(13) + CHAR(10) +");

		return inputString;
	}

	/**
	 * Returns false if input string contains anything other than alphabets,underscore,space and hyphen
	 */
	public static boolean isAlphaSpaceUnderScoreHyphen(String str) {
		String regex = "^[a-zA-Z_ -]*$";
		return str.matches(regex);
	}
	
	public static String esczpeChar(String queryLog) throws ParseException {		
		return queryLog.replace("\\\"", "\"").replace("\\\\", "\\");
	}
	
	public static Double getRoundOffToThreeDecimal(double value) {
		return Math.round(value * 1000.0) / 1000.0;
	}
	
	public static String encode(String url) {
		try {
			return URLEncoder.encode(url,StandardCharsets.UTF_8.name()).toString();
		} catch (UnsupportedEncodingException e) {
			return "Exception while encoding" + e.getMessage();
		}
	}

	/**
	 * check min length 6 max 30
	 * atleast combination uppercase, lowercase letters and (0-9 digit or special character) should be there.
	 */
	public Boolean validatePassword(String password) {
		String regexp = "(?=.*[a-z])(?=.*[A-Z])((?=.*[0-9])|(?=.*[@#$%^&+=]))(?=\\S+$).{6,30}";
		return password.matches(regexp);	    
	}
}
