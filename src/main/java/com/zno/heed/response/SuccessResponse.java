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

public class SuccessResponse extends ResponseBean {

	private final String message;

	public SuccessResponse(int status, String message) {
		super(status);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
