package com.zno.heed.constants;

/**
 * 
 * This Class may contain confidential, proprietary or legally privileged information. 
 * It is for the use of the ZNO only, and access by anyone else is unauthorized.
 * 
 * Module       : zno_online_class
 * Created Date : 28/07/2020
 * Created By   : TITTU VARGHESE
 */

public interface CommonConstant {

	public interface ResponseCode {		
		public static final int SUCCESS = 200;
		public static final int FAILED = 400;
		public static final int INVALID_INPUT = 401;
		public static final int ADMIN_INVALID = 503;
	}

	public interface ResponseMessage {		
		public static final String FAILED = "failed";
		public static final String SUCCESS = "Success";
		public static final String VERIFIED = "Verified";
		public static final String ZERO_RESULTS = "zero_results";
	}
	
	public interface CompanyMessage {		
		public static final String ERROR_COMPANY_DOESNT_EXISTS = "Company does not exist";
	}
	
	public interface Lockout {
		public static final String LOCK_MAX_ONE = "LOCK_MAX_ONE";
		public static final String LOCK_MAX_TWO = "LOCK_MAX_TWO";
		public static final String INVALID_ONE = "INVALID_ONE";
		public static final String INVALID_TWO = "INVALID_TWO";
		public static final String INVALID_THREE = "INVALID_THREE";
		public static final String USER_LOCKED_ONE = "Invalid credentials. Please try again.Two more attempt";
		public static final String USER_LOCKED_TWO = "USER IS LOCKED TWO";
		public static final String USER_LOCKED_THREE = "USER IS LOCKED THREE";
		public static final String USER_LOCKED_THREE_OUT = "USER_LOCKED_THREE_OUT";
		public static final String USER_LOCKED_THREE_OUT_MSG = "Your account permanently locked, Please contact Admin";
		public static final String LOCK_REMOVED = "Successfully removed the lock of the user . ";
		public static final String INVALID_REQUEST = "Invalid request please review it ";
		public static final String USER_LOCKED = "User Locked";
	}
	
	public interface UserMessage {

		public static final String ADMIN_TOKEN_EMPTY = "The admin token is empty ";
		public static final String ADMIN_TOKEN_INVALID = "The admin token is invalid ";
		public static final String ERROR_NOT_ADMIN = "You are not previleged ";
		public static final String RESET_PASSWORD_SUCCESS = "Please login and update password";
		public static final String ERROR_RESET_PASSWORD = "Error in reset password";
		public static final String USER_NOTFOUND = "User not found";

		public static final String INVALID_FIRSTNAME = "First Name is invalid";
		public static final String INVALID_LASTNAME = "Last Name is invalid";
		public static final String INVALID_MOBILE_PHONE = "Mobile phone number is invalid";
		public static final String INVALID_WORK_PHONE = "Work phone number is invalid";
		public static final String INVALID_EMAIL = "Email is invalid";
		public static final String PASSWORD_MISSMATCH = "Password and confirm passwored doesnot match";
		
		public static final String LOAD_CREATE_USER = "Create user page loadded successfully";
		public static final String USER_LOGIN = "User LogIn successfully";
		public static final String ERROR_LOGIN_INVALID_USER = "Invalid User";
		
		public static final String ACCOUNT_DISABLED = "Account Disabled";
		public static final String PASSWORD_EXPIRED = "Your password has been expired please login with a new password";
		public static final String PASSWORD_VALIDITY_EXPIRATION = "Your password has expired. Please check your e-mail to create a new password";

		public static final String USER_LOGOUT = "User LogOut successfully";
		public static final String ERROR_USER_LOGOUT = "An error occur in User LogOut";
		
		public static final String INVALID_INPUT_DATA = "Invalid Data";
		public static final String USER_EXISIT = "User Already exist, Try to login";
		public static final String ERROR_USER_CREATE = "Please try again after some time";
		public static final String USER_CREATED = "User created successfully";
	}

	public interface AgreementResponseCode {
		public static final int AGREEMENT_SUCCESS = 1;
		public static final int AGREEMENT_OLD = 3;
	}
	
	public interface PurchaseMessage {
		public static final String PURCHASE = "purchase";
		public static final String PURCHASE_CART_NO = "purchase_cart_no";

		public static final String PURCHASE_SAVED = "Purchase saved successfully";
		public static final String REMOVE_ERROR_PRODUCT_CART = "Product not exisit in the cart";
		public static final String REMOVED_PRODUCT_CART = "Remove product from purchase cart successfully";
		
		public static final String PURCHASE_COMPLETED = "Thanks, Your puchase completed successfully";
		public static final String PURCHASE_CART_EMPTY = "Your cart is empty";
	}
	
	public interface SalesMessage {
		public static final String SALES = "sales";
		public static final String SALES_CART_NO = "sales_cart_no";

		public static final String SALES_SAVED = "Sales saved successfully";
		public static final String REMOVE_ERROR_PRODUCT_CART = "Product not exisit in the cart";
		public static final String REMOVED_PRODUCT_CART = "Remove product from sales cart successfully";
		
		public static final String SALES_COMPLETED = "Thanks, Your sales completed successfully";
		public static final String SALES_CART_EMPTY = "Your cart is empty";
	}
}