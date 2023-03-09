package com.zno.heed.utils;

import org.springframework.validation.DataBinder;

/**
 * 
 * This Class may contain confidential, proprietary or legally privileged information. 
 * It is for the use of the ZNO only, and access by anyone else is unauthorized.
 * 
 * Module       : zno_online_class
 * Created Date : 28/07/2020
 * Created By   : TITTU VARGHESE
 */

public class BindingResultSet extends DataBinder {

	public BindingResultSet(Object target) {
		super(target);
	}

	public BindingResultSet(Object target, String objectName) {
		super(target, objectName);
	}
}
