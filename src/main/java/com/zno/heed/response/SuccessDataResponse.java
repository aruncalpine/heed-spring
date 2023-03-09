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

public class SuccessDataResponse extends SuccessResponse {

	private final Object objResponse;

	public SuccessDataResponse(int status, String message, Object objResponse) {
		super(status, message);
		this.objResponse = objResponse;
	}
	
	public Object getObjResponse() {
		return objResponse;
	}	
}
