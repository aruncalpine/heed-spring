package com.zno.heed.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zno.heed.constants.CommonConstant.ResponseCode;
import com.zno.heed.constants.CommonConstant.UserMessage;
import com.zno.heed.utils.ZnoQuirk;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@Controller
public class UserAppController {
	
@Autowired
UserService userService;	

	@PostMapping(value = "/appuser/login")
	@ResponseBody
	public UserResponse  login(@RequestBody LoginRequest loginUser, BindingResult bindingResult, HttpServletRequest request) throws ZnoQuirk {
		System.out.println("ccccccccccccccccccccc");
		System.out.println(loginUser.getEmail() +" "+loginUser.getPassword());
		UserResponse userResponse = userService.doLogin(loginUser, bindingResult, request);
		return userResponse;
}

	@PostMapping(value = "/appuser/signup")
	@ResponseBody
	public UserResponse createNewUser(@RequestBody @Valid User user, BindingResult bindingResult) throws ZnoQuirk {
		System.out.println("Is mapping working"    + user);
		
		UserResponse userResponse= userService.saveUser(user);
		System.out.println("the userResponse"+userResponse);
		
		return userResponse;

	}

	
	
}