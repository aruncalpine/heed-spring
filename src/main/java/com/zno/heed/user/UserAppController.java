package com.zno.heed.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zno.heed.utils.ZnoQuirk;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@Controller
public class UserAppController {
	
	@Autowired
	UserService userService;

	@ResponseBody
	@PostMapping(value = "/appuser/login")
	public UserResponse  login(@RequestBody LoginRequest loginUser, BindingResult bindingResult, HttpServletRequest request) throws ZnoQuirk {
		System.out.println(loginUser.getEmail() +" "+loginUser.getPassword());
		UserResponse userResponse = userService.doLogin(loginUser, bindingResult, request);
		return userResponse;
	}

	@PostMapping(value = "/appuser/signup", consumes = "application/json", produces = "application/json")
	public UserResponse createNewUser(@RequestBody @Valid User user, BindingResult bindingResult, HttpServletRequest request) throws ZnoQuirk {
		System.out.println("hiiii");
		UserResponse userResponse = userService.saveUser(user);
		LoginRequest loginRequest = new LoginRequest(user.getEmail(), user.getPassword());
		userResponse = userService.doLogin(loginRequest, bindingResult, request);
		return userResponse;
	}

	@PostMapping(value = "/appuser/signuptest")
	public UserResponse signuptest() throws ZnoQuirk {
		System.out.println("hiiii");
		return null;
	}
}