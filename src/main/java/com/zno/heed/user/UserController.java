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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zno.heed.constants.CommonConstant.ResponseCode;
import com.zno.heed.constants.CommonConstant.ResponseMessage;
import com.zno.heed.constants.CommonConstant.UserMessage;
import com.zno.heed.response.ResponseBean;
import com.zno.heed.response.SuccessDataResponse;
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
@RequestMapping(value = "/user")
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
	
	@GetMapping(value = "/signup")
	public ModelAndView signup() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("signup");
		return modelAndView;
	}
	
	@PostMapping(value = "/signup")
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findByEmail(user.getEmail()); 
		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email id provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("signup");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("login");
		}
		return modelAndView;
	}
	
	@PostMapping(value = "/login")
	public SuccessDataResponse login(@RequestBody LoginRequest loginUser, BindingResult bindingResult, HttpServletRequest request) throws ZnoQuirk {
		System.out.println("ccccccccccccccccccccc");
		String ipAddress = request.getRemoteAddr();
		if(bindingResult.hasErrors()) throw new ZnoQuirk(ResponseCode.FAILED, UserMessage.INVALID_INPUT_DATA);

		User user = userRepository.findByEmail(loginUser.getEmail()); 
		if(user == null)  throw new ZnoQuirk(ResponseCode.FAILED, UserMessage.ERROR_LOGIN_INVALID_USER); 
		
		Boolean flag = userService.getlock(user);
		if(flag == true) user = userService.doLogin(user, ipAddress);		
		userService.getlock(user);
		UserResponse userResponse = new UserResponse(user, 1, null, false, false, null);
		logger.info(_logService.logMessage(user.getEmail() +" : "+ UserMessage.USER_LOGIN, user.getUserToken(), this.getClass().getName()));	
		return new SuccessDataResponse(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, userResponse);
	}
	
	@ResponseBody
	@PostMapping(value = "/logout")
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