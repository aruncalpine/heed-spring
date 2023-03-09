package com.zno.heed.response;

/**
 * 
 * This Class may contain confidential, proprietary or legally privileged information. 
 * It is for the use of the ZNO only, and access by anyone else is unauthorized.
 * 
 * Module       : zno_online_class
 * Created Date : 28/07/2020
 * Created By   : TITTU VARGHESE
 */

public class ErrorResponse extends ResponseBean {

	private final String response;

	public ErrorResponse(int status, String response) {
		super(status);
		this.response = response;
	}
	
	public String getResponse() {
		return response;
	}
	
}
