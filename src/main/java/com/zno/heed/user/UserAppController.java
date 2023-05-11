package com.zno.heed.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zno.heed.chatdata.ChatRepository;
import com.zno.heed.chatdata.Chat_History;
import com.zno.heed.utils.ZnoQuirk;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@RestController
public class UserAppController {
	
	@Autowired
	UserService userService;
	@Autowired
	ChatRepository chatRepository;


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
	
	@GetMapping(value="/chat/history/{source}")
	public ResponseEntity<List<Chat_History>> getChathistory(@PathVariable String source){
		return new ResponseEntity<List<Chat_History>>(chatRepository.findBySource(source), HttpStatus.OK);
		
	}
}