package com.zno.heed.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zno.heed.response.ErrorResponse;
import com.zno.heed.response.SuccessDataResponse;
import com.zno.heed.response.SuccessResponse;

/**
 * 
 * This Class may contain confidential, proprietary or legally privileged information. 
 * It is for the use of the ZNO only, and access by anyone else is unauthorized.
 * 
 * Module       : zno_online_class
 * Created Date : 28/07/2020
 * Created By   : TITTU VARGHESE
 */

@Component
public class CommonUtils {

	@Value("${isIntegrated}")
	private String isIntegrated;

	public boolean isIntegrated() {
		return Boolean.valueOf(isIntegrated);
	}

	public static SuccessResponse getSuccessResponse(int status, String message) {
		SuccessResponse successDataResponse = new SuccessResponse(status, message);
		return successDataResponse;
	}
	
	public static SuccessDataResponse getSuccessDataResponse(int status, Object result, String message) {
		SuccessDataResponse responseBean = new SuccessDataResponse(status, message, result);
		return responseBean;
	}

	public static ErrorResponse getErrorResponseBean(int status, String errMsg) {
		ErrorResponse errorResponseBean = new ErrorResponse(status, errMsg);
		return errorResponseBean;
	}
}