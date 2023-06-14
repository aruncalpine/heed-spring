package com.zno.heed.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zno.heed.MysqlEntites.User;
import com.zno.heed.MysqlRepositories.UsersRepository;
import com.zno.heed.user.UsersResponse;

@RestController
public class HomeController {

	@GetMapping(value = "/")
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@Autowired
	private UsersRepository userRepository;
	
	@GetMapping(value = "/home")
	public ModelAndView homeLoad(@RequestHeader(value="X-Requested-Token", required=true) String userToken) {
		User user = userRepository.findByUserToken(userToken);
		UsersResponse userResponse = new UsersResponse(user.getFullName(), user.getUsersRole().getRoleName(), user.getUserToken(), user.getCompany().getName());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("user", userResponse);
		modelAndView.setViewName("home");
		return modelAndView;
	}
}
