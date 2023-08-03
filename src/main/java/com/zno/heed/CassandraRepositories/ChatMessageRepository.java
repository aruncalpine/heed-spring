package com.zno.heed.CassandraRepositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import com.zno.heed.CassandraEntities.ChatMessages;

import dto.ChatMessagesDto;
@Repository
public interface ChatMessageRepository extends CassandraRepository<ChatMessages, UUID>{
	
	

	@AllowFiltering
	ChatMessages findByMessagesAndCreatedDateTime(String messages , Date date);
	
//	@Query("update chatmessages set messages =?0 , updatedatetime =?1 where id=?2")
//	void updateMessage(String message,Date date,UUID id);
	
	@AllowFiltering
	 List<ChatMessages> findAllByChatUserId(Long chatUserId);
	
	@Query("select * from chatmessages where id=?0 ALLOW FILTERING")
	ChatMessages findChatMessagesById(UUID id);
	
	@Query( "SELECT messages FROM chatmessages WHERE chatuserid =?0 ORDER BY createddatetime DESC")
	List<ChatMessages> findMessagesByChatUserIdOrderByCreatedDateTime(Long chatUserID);
	
	
}
