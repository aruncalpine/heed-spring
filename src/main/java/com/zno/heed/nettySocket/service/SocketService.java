package com.zno.heed.nettySocket.service;


import java.util.Date;
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
import com.zno.heed.nettySocket.model.Message;
import com.zno.heed.nettySocket.model.MessageType;

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

    public void sendMessage(SocketIONamespace toSender,UUID id, String eventName, SocketIOClient senderClient, String message) {
      //  for (
               // SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
    	SocketIOClient client = toSender.getClient(id);
        		
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent(eventName,
                        new Message(MessageType.SERVER, message));
            }
        }
    
       public void saveHistory ( User sendUser , User receiveUser ) {
    	  
    	   System.out.println("sendUser   "+ sendUser);
    	   System.out.println("receiveUser   "+receiveUser);
    	   
    	   ChatUsers chatUsers = new ChatUsers();
    	   chatUsers.setDestUser(receiveUser);
    	   chatUsers.setSrcUser(sendUser);
    	   chatUsers.setIsDeleted(false);
    	   chatUsers.setDate(new Date());
    	   chatRepository.save(chatUsers);
       }

	public void saveMessage(User sendUser,User receiveUser, Message data) {
		// TODO Auto-generated method stub
		
 	   ChatUsers chatUsers=chatRepository.findBySrcUserAndDestUser(sendUser, receiveUser);
 	   
 	   System.out.println("the id of chatUsers  "+chatUsers.getId());
 	   
 	   ChatMessages chatMessages = new ChatMessages();
 	   chatMessages.setChatUserId(chatUsers.getId());
 	   chatMessages.setMessages(data.getMessage());
 	   chatMessages.setCreatedDateTime(new Date());
 	   chatMessages.setIsDeleted(false); 
 	   chatMessageRepository.save(chatMessages);
	}
    }
