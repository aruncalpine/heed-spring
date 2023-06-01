package com.zno.heed.user;

import java.security.Principal;
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
import com.zno.heed.chatdata.ChatResponse;
import com.zno.heed.chatdata.ChatService;
import com.zno.heed.chatdata.ChatUsers;
import com.zno.heed.chatdata.ChatUsersView;
import com.zno.heed.utils.ZnoQuirk;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@RestController
public class UserAppController {
	
	@Autowired
	UserService userService;
	@Autowired
	ChatRepository chatRepository;
	@Autowired 
	UsersRepository usersRepository;
	@Autowired
	ChatService chatService;


	@ResponseBody
	@PostMapping(value = "/appuser/login")
	public UserResponse  login(@RequestBody LoginRequest loginUser, BindingResult bindingResult, HttpServletRequest request) throws ZnoQuirk {
		System.out.println(loginUser.getEmail() +" "+loginUser.getPassword());
		UserResponse userResponse = userService.doLogin(loginUser, bindingResult, request);
		return userResponse;
	}

	@PostMapping(value = "/appuser/signup", consumes = "application/json", produces = "application/json")
	public UserResponse createNewUser(@RequestBody @Valid User user, BindingResult bindingResult, HttpServletRequest request) throws ZnoQuirk {
		LoginRequest loginRequest = new LoginRequest(user.getEmail(), user.getPassword());
		UserResponse userResponse = userService.saveUser(user);
		
		userResponse = userService.doLogin(loginRequest, bindingResult, request);
		return userResponse;
	}

	@PostMapping(value = "/appuser/signuptest")
	public UserResponse signuptest() throws ZnoQuirk {
		System.out.println("hiiii");
		return null;
	}
	
/*	@GetMapping(value="/chat/history/{sourceId}")
	public ResponseEntity<List<ChatUsersView>> getChathistory(@PathVariable Long sourceId){
		System.out.println("source id"+ sourceId);
		return new ResponseEntity<List<ChatUsersView>>(chatRepository.findDateAndDestUserFields(sourceId), HttpStatus.OK);
		
	}
	*/
	@GetMapping(value="/chat/history")
	public ResponseEntity<List<ChatUsersView>> getChathistory(HttpServletRequest request){
		  String authorizationHeader = request.getHeader("Authorization");
		        String bearerToken = authorizationHeader.substring(7);
		        System.out.println("token  "+bearerToken );
		    return new ResponseEntity<List<ChatUsersView>>(chatService.getChatHistory(bearerToken),HttpStatus.OK);
	}		    

	
   @GetMapping(value="/appuser/logout")
      public ResponseEntity<String> logout(HttpServletRequest request) {
	   String authorizationHeader = request.getHeader("Authorization");
       String bearerToken = authorizationHeader.substring(7);
	   userService.logout(bearerToken);
	   return ResponseEntity.ok("Logged out successfully");
   }
}