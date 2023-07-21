package com.zno.heed.nettySocket.service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.zno.heed.CassandraEntities.ChatMessages;
import com.zno.heed.CassandraRepositories.ChatMessageRepository;
import com.zno.heed.MysqlEntites.ChatUsers;
import com.zno.heed.MysqlEntites.User;
import com.zno.heed.MysqlRepositories.ChatRepository;
import com.zno.heed.MysqlRepositories.UsersRepository;
import com.zno.heed.constants.CommonConstant.ResponseCode;
import com.zno.heed.constants.CommonConstant.ResponseMessage;
import com.zno.heed.constants.CommonConstant.UserMessage;
import com.zno.heed.nettySocket.model.DeleteMessage;
import com.zno.heed.nettySocket.model.LocationData;
import com.zno.heed.nettySocket.model.Message;
import com.zno.heed.nettySocket.model.MessageType;
import com.zno.heed.nettySocket.model.Response;
import com.zno.heed.nettySocket.model.UpdateMessage;
import com.zno.heed.utils.ZnoQuirk;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SocketService {
	@Autowired
	ChatRepository chatRepository;
	@Autowired
	UsersRepository usersRepository;
	@Autowired
	ChatMessageRepository chatMessageRepository;

// Sending messages	
	public void sendMessage(SocketIONamespace toSender, UUID id, String eventName, SocketIOClient senderClient,
			String message) {
		// for (
		// SocketIOClient client :
		// senderClient.getNamespace().getRoomOperations(room).getClients()) {
		SocketIOClient client = toSender.getClient(id);

		if (!client.getSessionId().equals(senderClient.getSessionId())) {
			client.sendEvent(eventName, new Message(MessageType.SERVER, message));
		}
	}

// Sending 	location
	public void sendLocation(SocketIONamespace toSender, UUID id, String eventName, SocketIOClient senderClient,
			Message data) {
		
		SocketIOClient client = toSender.getClient(id);
		if (!client.getSessionId().equals(senderClient.getSessionId())) {
			client.sendEvent(eventName, new Message(data.getLocationData()));
		}
	}
	
// For saving chatUsers    
	public ChatUsers saveChatUser ( String token , Message data) throws ZnoQuirk {
	ChatUsers  chatUsers = null;
	      if(data.getChatUserId()!=null) {
	   chatUsers = chatRepository.findById(data.getChatUserId());
	   if(chatUsers==null) {
		   throw new ZnoQuirk(ResponseCode.FAILED, ResponseMessage.FAILED);
	          }
	   }
	   else {
	  User sendUser = usersRepository.findByUserToken(token);
	  User receiveUser = usersRepository.findByMobilePhone(data.getMobileNumber());  	   
         chatUsers = chatRepository.findBySrcUserAndDestUser(sendUser, receiveUser);	   
   	    if(chatUsers==null) {
   		   chatUsers=new ChatUsers();
    	   chatUsers.setDestUser(receiveUser);
    	   chatUsers.setSrcUser(sendUser);
    	   chatUsers.setIsDeleted(false);
    	   chatUsers.setDate(new Date());
    	   chatRepository.save(chatUsers);   
   		}
	   }
   		return chatUsers;
       }

// For saving
	public Response saveMessage(String token, Message data) throws ZnoQuirk {
		// TODO Auto-generated method stub

		ChatUsers chatUsers = saveChatUser(token, data);

		ChatMessages chatMessages = new ChatMessages();
		chatMessages.setChatUserId(chatUsers.getId());
		chatMessages.setMessages(data.getMessage());
		chatMessages.setCreatedDateTime(new Date());
		chatMessages.setIsDeleted(false);		
		chatMessageRepository.save(chatMessages);
        Response response = new Response(chatMessages.getId(),chatMessages.getChatUserId()) ;  
		return response;
	}
	
// For saving with location
	public Response saveLocation(String token, Message data) throws ZnoQuirk {
		// TODO Auto-generated method stub

		ChatUsers chatUsers = saveChatUser(token, data);

		ChatMessages chatMessages = new ChatMessages();
		chatMessages.setChatUserId(chatUsers.getId());
		chatMessages.setCreatedDateTime(new Date());
		chatMessages.setIsDeleted(false);
		chatMessages.setLatitude(data.getLocationData().getLatitude());
		chatMessages.setLongitude(data.getLocationData().getLongitude());
		chatMessageRepository.save(chatMessages);
		Response response = new Response(chatMessages.getId(),chatMessages.getChatUserId()) ;
		return response;
	}
	

//	For Updating
	public void updateMessage(UpdateMessage data) {
		chatMessageRepository.updateMessage(data.getMessage(), new Date(), data.getId());
	}

// For deleting	
	public void deleteMessage(DeleteMessage data) {
		chatMessageRepository.updateIsDeleted(data.getId());
	}

}
