package com.zno.heed.CassandraRepositories;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import com.zno.heed.CassandraEntities.ChatMessages;

import dto.ChatMessagesDto;
@Repository
public interface ChatMessageRepository extends CassandraRepository<ChatMessages, UUID>{
	
	@Query("update chatmessages set isdeleted = true where id= ?0")
	void updateIsDeleted(UUID id);

	
	@AllowFiltering
	ChatMessages findByMessagesAndCreatedDateTime(String messages , Date date);
	
	@Query("update chatmessages set messages =?0 , updatedatetime =?1 where id=?2")
	void updateMessage(String message,Date date,UUID id);
	
	@AllowFiltering
	 List<ChatMessages> findAllByChatUserId(Long chatUserId);
	
}
