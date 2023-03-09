package com.zno.heed.utils;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.zno.heed.response.ResponseBean;

/**
 * 
 * This Class may contain confidential, proprietary or legally privileged information. 
 * It is for the use of the ZNO only, and access by anyone else is unauthorized.
 * 
 * Module       : zno_online_class
 * Created Date : 28/07/2020
 * Created By   : TITTU VARGHESE
 */

@ResponseStatus(HttpStatus.CONFLICT)
public class ZnoQuirk extends Exception {

	private static final long serialVersionUID = 1L;

	private int code;
	private String message;
	
	public ZnoQuirk(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
	
	@ControllerAdvice
	public static class MyExceptionHandler {
	
		@ExceptionHandler(ZnoQuirk.class)
		public @ResponseBody ResponseBean handler(ZnoQuirk exception) {	
			return CommonUtils.getErrorResponseBean(exception.getCode(), exception.getMessage());
		}
	}

}
