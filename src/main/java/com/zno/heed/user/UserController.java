package com.zno.heed.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zno.heed.MysqlEntites.User;
import com.zno.heed.MysqlRepositories.UsersRepository;
import com.zno.heed.constants.CommonConstant.ResponseCode;
import com.zno.heed.constants.CommonConstant.UserMessage;
import com.zno.heed.response.ResponseBean;
import com.zno.heed.services.LoggerService;
import com.zno.heed.utils.CommonUtils;
import com.zno.heed.utils.ZnoQuirk;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * 
 * This Class may contain confidential, proprietary or legally privileged information. 
 * It is for the use of the ZNO only, and access by anyone else is unauthorized.
 * 
 * Module       : zno_online_class
 * Created Date : 28/07/2020
 * Created By   : TITTU VARGHESE
 */
 
@Controller
public class UserController {

	@Autowired
	@Qualifier("userValidator")
	private Validator userValidator;
	
	@Autowired
	private UserService userService;

	@Autowired
	private UsersRepository userRepository;

	@Autowired
	private LoggerService _logService;
	

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@GetMapping(value = "/user/signup")
	public ModelAndView signup() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("signup");
		return modelAndView;
	}
	
	@PostMapping(value = "/user/signup")
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		try{
		
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("login");
			return modelAndView;
		}catch(ZnoQuirk exception ) {
			modelAndView.addObject("errorMessage", exception.getMessage());
			modelAndView.setViewName("signup");
		return modelAndView;
		}	
	}
	
	@PostMapping(value = "/user/login")
	public ModelAndView login(@Valid LoginRequest loginUser, BindingResult bindingResult, HttpServletRequest request) throws ZnoQuirk {
		System.out.println("ccccccccccccccccccccc");
		ModelAndView modelAndView = new ModelAndView();	
		try {
		 UserResponse userResponse =userService.doLogin(loginUser, bindingResult, request); 
		 modelAndView.addObject("user", userResponse);
			modelAndView.setViewName("home");
			return modelAndView;
		}catch(ZnoQuirk exception ) {
			modelAndView.addObject("errorMessage", exception.getMessage());
		     modelAndView.setViewName("login");
		return modelAndView;
		
		}
		
	}
	
	@ResponseBody
	@PostMapping(value = "/user/logout")
	public ResponseBean logout(@RequestHeader(value="userToken", required=true) String userToken) throws ZnoQuirk {
		User user = userRepository.findByUserToken(userToken);
		Boolean flag = userService.logout(userToken);
		if(flag == true) {
			logger.info(_logService.logMessage(user.getEmail() +""+ UserMessage.USER_LOGOUT, userToken,this.getClass().getName()));
			return CommonUtils.getErrorResponseBean(ResponseCode.SUCCESS, UserMessage.USER_LOGOUT);
		} else
			throw new ZnoQuirk(ResponseCode.FAILED, UserMessage.ERROR_USER_LOGOUT);
	}
}