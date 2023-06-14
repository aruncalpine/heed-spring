package com.zno.heed.user;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

import com.zno.heed.CassandraEntities.ChatMessages;
import com.zno.heed.CassandraRepositories.ChatMessageRepository;
import com.zno.heed.MysqlEntites.ChatUsers;
import com.zno.heed.MysqlEntites.User;
import com.zno.heed.MysqlRepositories.ChatRepository;
import com.zno.heed.MysqlRepositories.UsersRepository;
import com.zno.heed.chatdata.ChatResponse;
import com.zno.heed.chatdata.ChatService;
import com.zno.heed.chatdata.ChatUsersView;
import com.zno.heed.utils.ZnoQuirk;

import dto.ChatMessagesDto;
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
	@Autowired
	ChatMessageRepository chatMessageRepository;
	


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
   @GetMapping(value="/test")
     public ResponseEntity<String> test(){
	   ChatMessages chatMessages = new ChatMessages();
	   chatMessages.setChatUserId(2);
	   chatMessages.setIsDeleted(false);
	   chatMessages.setMessages("haiiiiiii");
	   chatMessages.setType("image");
	   chatMessages.setCreatedDateTime(new Date());
	   chatMessageRepository.save(chatMessages);
	   System.out.println(chatMessages);
	   return ResponseEntity.ok("successfully");
   }
   @GetMapping(value ="/testdelete/{id}")
   public ResponseEntity<String> testDelete(@PathVariable UUID id){
	   chatMessageRepository.deleteById(id);
	  return ResponseEntity.ok("deleted");
   }
   
   @GetMapping(value="/testupdate/{id}")
   public ResponseEntity<String> testUpdate(@PathVariable UUID id){
   Optional<ChatMessages> chatMessages = chatMessageRepository.findById(id);
   
   ChatMessages chatMessages2 = chatMessages.get();
   ChatMessages chatMessages3 = new ChatMessages();
   
   chatMessages3.setId(chatMessages2.getId());
   chatMessages3.setChatUserId(chatMessages2.getChatUserId());
   chatMessages3.setMessages("The messages is changed");
   chatMessages3.setIsDeleted(chatMessages2.getIsDeleted());
   chatMessages3.setUpdatedDateTime(new Date());
   chatMessageRepository.save(chatMessages3);
	return ResponseEntity.ok("updated");
	   
   }
}