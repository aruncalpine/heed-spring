package com.zno.heed.user;

import static com.zno.heed.constants.CommonConstant.UserMessage.INVALID_EMAIL;
import static com.zno.heed.constants.CommonConstant.UserMessage.INVALID_FIRSTNAME;
import static com.zno.heed.constants.CommonConstant.UserMessage.INVALID_LASTNAME;
import static com.zno.heed.constants.CommonConstant.UserMessage.INVALID_MOBILE_PHONE;
import static com.zno.heed.constants.CommonConstant.UserMessage.PASSWORD_MISSMATCH;
import static com.zno.heed.utils.ValidationUtil.isInvalidEmailAddress;
import static com.zno.heed.utils.ValidationUtil.isInvalidNumber;
import static com.zno.heed.utils.ValidationUtil.isInvalidPassword;
import static com.zno.heed.utils.ValidationUtil.validateUserName;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.CustomValidatorBean;

import com.zno.heed.MysqlEntites.User;

@Component
public class UserValidator extends CustomValidatorBean {

	@Override
	public boolean supports(Class<?> classObj) {
		if (User.class.equals(classObj))
			return true;
		return false;
	}

	@Override
	public void validate(Object obj, Errors errors) {
		System.out.println("validation");
		super.validate(obj, errors);
		if (obj instanceof User) {
			User users = (User) obj;
			if (validateUserName(users.getFirstName()))
				errors.reject(INVALID_FIRSTNAME);

			if (validateUserName(users.getLastName()))
				errors.reject(INVALID_LASTNAME);
	
			if (isInvalidNumber(users.getMobilePhone()))
				errors.reject(INVALID_MOBILE_PHONE);

			if (isInvalidEmailAddress(users.getEmail()))
				errors.reject(INVALID_EMAIL);

		//	if (isInvalidPassword(users.getPassword(), users.getConfirmPassword()))
		//		errors.reject(PASSWORD_MISSMATCH);

		}
	}

}