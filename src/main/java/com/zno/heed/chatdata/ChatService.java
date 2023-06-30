package com.zno.heed.chatdata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zno.heed.MysqlEntites.User;
import com.zno.heed.MysqlRepositories.ChatRepository;
import com.zno.heed.MysqlRepositories.UsersRepository;

@Service
public class ChatService {
	
@Autowired
ChatRepository chatRepository;

@Autowired 
UsersRepository usersRepository;


public List<ChatUsersView> getChatHistory(String barerToken){
	 User user = usersRepository.findByUserToken(barerToken);
	 Long Id = user.getId();
	 System.out.println("The id of the user  "+Id  );
	return (chatRepository.findDateAndDestUserFields(Id));
}
}
