package com.zno.heed.user;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zno.heed.CassandraEntities.ChatMessages;
import com.zno.heed.CassandraRepositories.ChatMessageRepository;
import com.zno.heed.MysqlEntites.User;
import com.zno.heed.MysqlRepositories.ChatRepository;
import com.zno.heed.MysqlRepositories.UsersRepository;
import com.zno.heed.chatdata.ChatService;
import com.zno.heed.chatdata.ChatUsersView;
import com.zno.heed.files.FileDownloadUtil;
import com.zno.heed.files.FileUploadResponse;
import com.zno.heed.files.FileUploadUtil;
import com.zno.heed.files.ImageDownloadUtil;
import com.zno.heed.files.ImageUploadResponse;
import com.zno.heed.files.ImageUploadUtil;
import com.zno.heed.files.ProfileImageDownloadUtil;
import com.zno.heed.files.ProfileImageUploadResponse;
import com.zno.heed.files.ProfileImageUploadUtil;
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
	@Autowired
	ChatMessageRepository chatMessageRepository;
	@Autowired
   ProfileImageUploadUtil profileImageUploadUtil;
	@ResponseBody
	@PostMapping(value = "/appuser/login")
	public UserResponse login(@RequestBody LoginRequest loginUser, BindingResult bindingResult,
			HttpServletRequest request) throws ZnoQuirk {
		System.out.println(loginUser.getEmail() + " " + loginUser.getPassword());
		UserResponse userResponse = userService.doLogin(loginUser, bindingResult, request);
		return userResponse;
	}

	@PostMapping(value = "/appuser/signup", consumes = "application/json", produces = "application/json")
	public UserResponse createNewUser(@RequestBody @Valid User user, BindingResult bindingResult,
			HttpServletRequest request) throws ZnoQuirk {
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
	/*
	 * @GetMapping(value="/chat/history/{sourceId}") public
	 * ResponseEntity<List<ChatUsersView>> getChathistory(@PathVariable Long
	 * sourceId){ System.out.println("source id"+ sourceId); return new
	 * ResponseEntity<List<ChatUsersView>>(chatRepository.findDateAndDestUserFields(
	 * sourceId), HttpStatus.OK);
	 * 
	 * }
	 */
	@GetMapping(value = "/chatuser")
	public ResponseEntity<List<ChatUsersView>> getChathistory(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		String bearerToken = authorizationHeader.substring(7);
		System.out.println("token  " + bearerToken);
		return new ResponseEntity<List<ChatUsersView>>(chatService.getChatHistory(bearerToken), HttpStatus.OK);
	}

	@GetMapping(value = "/appuser/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		String bearerToken = authorizationHeader.substring(7);
		userService.logout(bearerToken);
		return ResponseEntity.ok("Logged out successfully");
	}

	@GetMapping(value = "/testsave")
	public ResponseEntity<String> test() {
		ChatMessages chatMessages = new ChatMessages();
		chatMessages.setChatUserId(2L);
		chatMessages.setIsDeleted(false);
		chatMessages.setMessages("haiiiiiii");
		chatMessages.setType("image");
		chatMessages.setCreatedDateTime(new Date());
		chatMessageRepository.save(chatMessages);
		System.out.println(chatMessages);
		return ResponseEntity.ok("successfully");
	}

	@GetMapping(value = "/testdelete/{id}")
	public ResponseEntity<String> testDelete(@PathVariable UUID id) {
		chatMessageRepository.deleteById(id);
		return ResponseEntity.ok("deleted");
	}

	@GetMapping(value = "/testupdate/{id}")
	public ResponseEntity<String> testUpdate(@PathVariable UUID id) {
		/*
		 * Optional<ChatMessages> chatMessages = chatMessageRepository.findById(id);
		 * 
		 * ChatMessages chatMessages2 = chatMessages.get(); ChatMessages chatMessages3 =
		 * new ChatMessages();
		 * 
		 * chatMessages3.setId(chatMessages2.getId());
		 * chatMessages3.setChatUserId(chatMessages2.getChatUserId());
		 * chatMessages3.setMessages("The messages is changed");
		 * chatMessages3.setIsDeleted(chatMessages2.getIsDeleted());
		 * chatMessages3.setUpdatedDateTime(new Date());
		 * chatMessageRepository.save(chatMessages3);
		 */

		chatMessageRepository.updateIsDeleted(id);
		return ResponseEntity.ok("updated");

	}
	
	@GetMapping(value = "/listmessages/{id}")
	ResponseEntity<List<ChatMessages>> listMessages(@PathVariable Long id) {
		return new ResponseEntity<List<ChatMessages>>(chatMessageRepository.findAllByChatUserId(id), HttpStatus.OK);

		
	}
	
	@PostMapping("/uploadFile")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile multipartFile)
                    throws IOException {
         
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();
         
        String filecode = FileUploadUtil.saveFile(fileName, multipartFile);
         
        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/downloadFile/" + filecode);
         
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	  @GetMapping("/downloadFile/{fileCode}")
	    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) {
	        FileDownloadUtil downloadUtil = new FileDownloadUtil();
	         
	        Resource resource = null;
	        try {
	            resource = downloadUtil.getFileAsResource(fileCode);
	        } catch (IOException e) {
	            return ResponseEntity.internalServerError().build();
	        }
	         
	        if (resource == null) {
	            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
	        }
	         
	        String contentType = "application/octet-stream";
	        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
	         
	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
	                .body(resource);       
	    }
	  
	  @PostMapping("/uploadImage")
	    public ResponseEntity<ImageUploadResponse> uploadImage(
	            @RequestParam("image") MultipartFile multipartFile)
	                    throws IOException {
	         
	        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	        long size = multipartFile.getSize();
	         
	        String filecode = ImageUploadUtil.saveImage(fileName, multipartFile);
	        ImageUploadResponse response = new ImageUploadResponse();  
	        response.setFileName(fileName);
	        response.setSize(size);
	        response.setDownloadUri("/downloadImage/" + filecode);
	         
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	  
	  @GetMapping("/downloadImage/{fileCode}")
	    public ResponseEntity<?> downloadImage(@PathVariable("fileCode") String fileCode) {
		  ImageDownloadUtil downloadUtil= new ImageDownloadUtil();
	        Resource resource = null;
	        try {
	            resource = downloadUtil.getFileAsResource(fileCode);
	        } catch (IOException e) {
	            return ResponseEntity.internalServerError().build();
	        }
	         
	        if (resource == null) {
	            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
	        }
	         
	        String contentType = "application/octet-stream";
	        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
	         
	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
	                .body(resource);       
	    }
	  @PostMapping("/uploadProfileImage")
	    public ResponseEntity<ProfileImageUploadResponse> uploadaProfileImage(
	            @RequestParam("image") MultipartFile multipartFile , HttpServletRequest request)
	                    throws IOException {
		  
		    String authorizationHeader = request.getHeader("Authorization");
			String bearerToken = authorizationHeader.substring(7);   
		  
	        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	        long size = multipartFile.getSize();
	        String filecode = profileImageUploadUtil.saveProfileImage(fileName, multipartFile, bearerToken);
	        ProfileImageUploadResponse response = new ProfileImageUploadResponse();  
	        response.setFileName(fileName);
	        response.setSize(size);
	        response.setDownloadUri("/downloadProfileImage/" + filecode);
	         
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	  @GetMapping("/downloadProfileImage/{fileCode}")
	    public ResponseEntity<?> downloadProfileImage(@PathVariable("fileCode") String fileCode) {
		  ProfileImageDownloadUtil downloadUtil= new ProfileImageDownloadUtil();
	        Resource resource = null;
	        try {
	            resource = downloadUtil.getFileAsResource(fileCode);
	        } catch (IOException e) {
	            return ResponseEntity.internalServerError().build();
	        }
	         
	        if (resource == null) {
	            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
	        }
	         
	        String contentType = "application/octet-stream";
	        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
	         
	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
	                .body(resource);       
	    }
}